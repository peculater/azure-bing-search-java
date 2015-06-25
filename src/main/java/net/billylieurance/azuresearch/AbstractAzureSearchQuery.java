package net.billylieurance.azuresearch;

/*
 Copyright 2012 William Lieurance

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.io.input.BOMInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * The abstract class representing a search query.  The class is intended to have a subclass 
 * instantiated, have a number of fields set on that object, run the {@link doQuery()} method,
 * then get results one page at a time through the {@link getQueryResult()} method.
 * @author William Lieurance
 * @param <ResultT> Type of AzureSearch(something)Result that the class will return
 */
public abstract class AbstractAzureSearchQuery<ResultT> {

    private String _query;

    private String _queryOption = "";
    private String _market = "en-US";
    private AZURESEARCH_QUERYADULT _adult = null;

    /**
     * API comes in regular and "Web Only".  This chooses the more expansive option
     */
    protected AZURESEARCH_API _bingApi = AZURESEARCH_API.BINGSEARCH;

    /**
     * All of the parsing later is based on XML format.
     */
    protected AZURESEARCH_FORMAT _format = AZURESEARCH_FORMAT.XML;
    // private static final Logger log = Logger
    // .getLogger(AbstractAzureSearchQuery.class.getName());
    private AzureSearchResultSet<ResultT> _queryResult;
    private Document _rawResult;
    private String _appid;
    private Integer _perPage = 15;
    private Integer _skip = 0;
    private String _queryExtra = "";
    private String _latitude = "";
    private String _longitude = "";
    private Boolean _processHTTPResults = true;
    private Boolean _debug = false;  //Trade an extra copy of the results in memory for better exceptions

    /**
     * Type of query that Bing can understand.  Matches to the AzureSearch(Thing)Query class.
     */
    public static enum AZURESEARCH_QUERYTYPE {

        /**
         * Mixture of all the other types of searches
         */
        COMPOSITE,

        /**
         * Web search
         */
        WEB,

        /**
         * Image search
         */
        IMAGE,

        /**
         * Video search
         */
        VIDEO,

        /**
         * News search
         */
        NEWS,

        /**
         * "Related" search
         */
        RELATEDSEARCH,

        /**
         * Spelling suggestions
         */
        SPELLINGSUGGESTION
    };

    /**
     * Choose whether or not to limit out objectionable content
     */
    public static enum AZURESEARCH_QUERYADULT {

        /**
         * Show everything
         */
        OFF,

        /**
         * Show some content that might be considered objectionable
         */
        MODERATE,

        /**
         * Show almost nothing that might be objectionable
         */
        STRICT
    };

    /**
     * Bing's API comes in regular and "Web Only".
     */
    public static enum AZURESEARCH_API {

        /**
         * Arbitrary searches
         */
        BINGSEARCH,

        /**
         * Web only searches
         */
        BINGSEARCHWEBONLY
    }

    /**
     * What format the results should be returned in
     */
    public static enum AZURESEARCH_FORMAT {

        /**
         * JSON
         */
        JSON,

        /**
         * XML
         */
        XML
    }

    /**
     * HTTP or HTTPS
     */
    public static final String AZURESEARCH_SCHEME = "https";

    /**
     * What port to connect to
     */
    public static final Integer AZURESEARCH_PORT = 443;

    /**
     * Azure's search hostname
     */
    public static final String AZURESEARCH_AUTHORITY = "api.datamarket.azure.com";

    /**
     * Azure's search path
     */
    public static final String AZURESEARCH_PATH = "/Data.ashx/Bing/Search/v1/";

    /**
     * Azure's web-only search path
     */
    public static final String AZURESEARCHWEB_PATH = "/Data.ashx/Bing/SearchWeb/v1/";

    // HTTP objects

    /**
     * HttpHost that represents where to post queries
     */
        protected HttpHost _targetHost = new HttpHost(AZURESEARCH_AUTHORITY,
            AZURESEARCH_PORT, AZURESEARCH_SCHEME);

    /**
     * Cache the auth
     */
    protected AuthCache _authCache = new BasicAuthCache();

    /**
     *
     */
    protected BasicScheme _basicAuth = new BasicScheme();

    /**
     *
     */
    protected BasicHttpContext _localcontext = new BasicHttpContext();

    /**
     *
     */
    protected HttpResponse _responsePost;

    /**
     *
     */
    protected HttpEntity _resEntity;

    /**
     * @return the responsePost to get a response entity out of
     */
    public HttpResponse getResponsePost() {
        return _responsePost;
    }

    /**
     * @param responsePost the responsePost to get a response entity out of
     */
    protected void setResponsePost(HttpResponse responsePost) {
        this._responsePost = responsePost;
    }

    /**
     * @return the resEntity to read a response out of
     */
    public HttpEntity getResEntity() {
        return _resEntity;
    }

    /**
     * @param resEntity the response entity to read a response out of
     */
    protected void setResEntity(HttpEntity resEntity) {
        this._resEntity = resEntity;
    }

    /**
     * @return the level of objectionable content
     */
    protected AZURESEARCH_QUERYADULT getAdult() {
        return _adult;
    }

    /**
     * @param adult the level of objectionable content to set
     */
    protected void setAdult(AZURESEARCH_QUERYADULT adult) {
        _adult = adult;
    }

    /**
     * @return the set of results to the query
     */
    public AzureSearchResultSet<ResultT> getQueryResult() {
        return _queryResult;
    }

    /**
     * @param queryResult the queryResult to set
     */
    protected void setQueryResult(AzureSearchResultSet<ResultT> queryResult) {
        _queryResult = queryResult;
    }

    /**
     *
     * @return The XML that Bing gave back as a result
     */
    public Document getRawResult() {
        return _rawResult;
    }

    /**
     *
     * @param _rawResult The XML that Bing gave back as a result
     */
    public void setRawResult(Document _rawResult) {
        this._rawResult = _rawResult;
    }

    /**
     *
     * @return the appropriate path based on whether the class is using general search or web-only
     */
    public String getPath() {
        switch (_bingApi) {
            case BINGSEARCH:
                return AZURESEARCH_PATH;
            case BINGSEARCHWEBONLY:
                return AZURESEARCHWEB_PATH;
            default:
                return AZURESEARCH_PATH;
        }
    }

    /**
     *
     * @return the query path from {@link getPath()} plus any class-specific query parameters
     */
    public abstract String getQueryPath();

    /**
     *
     * @return The Bing options string for this query
     */
    public String getQueryOption() {
        return _queryOption;
    }

    /**
     *
     * @param _queryOption The Bing options string for this query
     */
    public void setQueryOption(String _queryOption) {
        this._queryOption = _queryOption;
    }

    /**
     *
     * @return The Bing code for what language market to focus results on
     */
    public String getMarket() {
        return _market;
    }

    /**
     *
     * @param _market The Bing code for what language market to focus results on
     */
    public void setMarket(String _market) {
        this._market = _market;
    }

    /**
     *
     * @param entry an XML node representing one search result of a given type
     * @return a new AzureSearch(Something)Result object that represents the search result
     */
    public abstract ResultT parseEntry(Node entry);

    // cast _queryresult to the right thing
    /**
     * @return the The querystring to search for
     */
    public String getQuery() {
        return _query.replace("&", "%26");
    }

    /**
     * @param query The querystring to search for
     */
    public void setQuery(String query) {
        _query = query;
    }

    /**
     * @return the number of results per page to ask Bing to return.  Default is 15.
     */
    public Integer getPerPage() {
        return _perPage;
    }

    /**
     * @param perPage Number of results per page to ask Bing to return.  Default is 15.
     */
    public void setPerPage(Integer perPage) {
        _perPage = perPage;
    }

    /**
     *
     * @return True to use an extra memory segment to hold a copy of the response from Bing in case there's a later exception.  False to keep that memory empty but exceptions do not show the data they were working on.
     */
    public Boolean getDebug() {
        return _debug;
    }

    /**
     *
     * @param _debug True to use an extra memory segment to hold a copy of the response from Bing in case there's a later exception.  False to keep that memory empty but exceptions do not show the data they were working on.
     */
    public void setDebug(Boolean _debug) {
        this._debug = _debug;
    }

    /**
     * Basic constructor, creates a basic context to do a query to be defined later
     */
    public AbstractAzureSearchQuery() {
        super();

        // Generate BASIC scheme object and add it to the local
        // auth cache
        _authCache.put(_targetHost, _basicAuth);

        // Add AuthCache to the execution context
        _localcontext.setAttribute(ClientContext.AUTH_CACHE, _authCache);
    }

    /**
     *
     * @return Class specific additional query parameters to add to the querystring
     */
    public abstract String getAdditionalUrlQuery();

    /**
     *
     * @return The URL querystring that represents this query and its various options
     */
    public String getUrlQuery() {

        StringBuilder sb = new StringBuilder();
        sb.append("Query='");
        sb.append(this.getQuery());
        sb.append("'");

        if (!this.getQueryOption().equals("")) {
            sb.append("&Options='");
            sb.append(this.getQueryOption());
            sb.append("'");
        }

        if (!this.getLatitude().equals("")) {
            sb.append("&Latitude=");
            sb.append(this.getLatitude());
        }

        if (!this.getLongitude().equals("")) {
            sb.append("&Longitude=");
            sb.append(this.getLongitude());
        }

        if (_adult != null) {
            sb.append("&Adult='");
            sb.append(adultToParam(this.getAdult()));
            sb.append("'");
        }

        if (!this.getMarket().equals("")) {
            sb.append("&Market='");
            sb.append(this.getMarket());
            sb.append("'");
        }
        sb.append("&$top=");
        sb.append(this.getPerPage());

        if (this.getSkip() > 0) {
            sb.append("&$skip=");
            sb.append(this.getSkip());
        }

        sb.append("&$format=");
        sb.append(formatToParam(this.getFormat()));

        sb.append(this.getAdditionalUrlQuery());

        sb.append(this.getQueryExtra());

        return sb.toString();
    }

    /**
     * Run the query that has been set up in this instance.
     * Next step would be to get the results with {@link getQueryResult()}
     */
    public void doQuery() {
        DefaultHttpClient client = new DefaultHttpClient();

        client.getCredentialsProvider()
                .setCredentials(
                        new AuthScope(_targetHost.getHostName(),
                                _targetHost.getPort()),
                        new UsernamePasswordCredentials(this.getAppid(), this
                                .getAppid()));

        URI uri;
        try {
            String full_path = getQueryPath();
            String full_query = getUrlQuery();
            uri = new URI(AZURESEARCH_SCHEME, AZURESEARCH_AUTHORITY, full_path,
                    full_query, null);
            // Bing and java URI disagree about how to represent + in query
            // parameters. This is what we have to do instead...
            uri = new URI(uri.getScheme() + "://" + uri.getAuthority()
                    + uri.getPath() + "?"
                    + uri.getRawQuery().replace("+", "%2b")
            );

            // log.log(Level.WARNING, uri.toString());
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
            return;
        }

        HttpGet get = new HttpGet(uri);

        get.addHeader("Accept", "application/xml");
        get.addHeader("Content-Type", "application/xml");

        try {
            _responsePost = client.execute(get);
            _resEntity = _responsePost.getEntity();

            if (this.getProcessHTTPResults()) {
                _rawResult = loadXMLFromStream(_resEntity.getContent());
                this.loadResultsFromRawResults();
            }

            // Adding an automatic HTTP Result to String really requires
            // Apache Commons IO. That would break
            // Android compatibility. I'm not going to do that unless I
            // re-implement IOUtils.
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    /**
     * Run only the parsing and object creation parts of the query process, 
     * assuming that there are already raw results that have been set through
     * the {@link setRawResult()} method
     */
    public void loadResultsFromRawResults() {
        if (_rawResult != null) {
            NodeList parseables = _rawResult
                    .getElementsByTagName("entry");
            _queryResult = new AzureSearchResultSet<ResultT>();
            if (parseables != null) {
                for (int i = 0; i < parseables.getLength(); i++) {
                    Node parseable = parseables.item(i);
                    ResultT ar = this.parseEntry(parseable);
                    if (ar != null) {
                        _queryResult.addResult(ar);
                    }
                }
            }
        }
    }

    /**
     *
     * @param is An InputStream holding some XML that needs parsing
     * @return a parsed Document from the XML in the stream
     */
    public Document loadXMLFromStream(InputStream is) {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        BOMInputStream bis;
        String dumpable = "";
        try {
            factory = DocumentBuilderFactory
                    .newInstance();
            builder = factory.newDocumentBuilder();
            bis = new BOMInputStream(is);

            if (_debug) {
                java.util.Scanner s = new java.util.Scanner(bis).useDelimiter("\\A");
                dumpable = s.hasNext() ? s.next() : "";
                //System.out.print(dumpable);
                // convert String into InputStream
                InputStream istwo = new java.io.ByteArrayInputStream(dumpable.getBytes());
                return builder.parse(istwo);

            } else {
                return builder.parse(bis);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            if (e instanceof SAXParseException) {
                SAXParseException ex = (SAXParseException) e;
                System.out.println("Line: " + ex.getLineNumber());
                System.out.println("Col: " + ex.getColumnNumber());
                System.out.println("Data: " + dumpable);
            }
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @return the Azure Appid
     */
    public String getAppid() {
        return _appid;
    }

    /**
     *
     * @param appid the Azure Appid
     */
    public void setAppid(String appid) {
        _appid = appid;
    }

    /**
     *
     * @return Additional things appended to the querystring
     */
    public String getQueryExtra() {
        return _queryExtra;
    }

    /**
     *
     * @param queryExtra Additional things appended to the querystring.
     */
    public void setQueryExtra(String queryExtra) {
        _queryExtra = queryExtra;
    }

    /**
     * @return the latitude
     */
    public String getLatitude() {
        return _latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        _latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public String getLongitude() {
        return _longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        _longitude = longitude;
    }

    /**
     *
     * @return whether or not to process the HTTP results
     */
    public Boolean getProcessHTTPResults() {
        if (this.getFormat() == AZURESEARCH_FORMAT.JSON) {
            return false;
        }
        return _processHTTPResults;
    }

    /**
     *
     * @param processHTTPResults whether or not to process the HTTP results
     */
    public void setProcessHTTPResults(Boolean processHTTPResults) {
        _processHTTPResults = processHTTPResults;
    }

    /**
     * @return XML or JSON format
     */
    public AZURESEARCH_FORMAT getFormat() {
        return _format;
    }

    /**
     * @param format the format to request of Bing.  XML or JSON.
     */
    public void setFormat(AZURESEARCH_FORMAT format) {
        _format = format;
    }

    /**
     *
     * @param node XML data node
     * @return A string dumping the XML
     */
    public static String xmlToString(Node node) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param type from the AZURESEARCH_QUERYTYPE enum
     * @return the string representation of the enum selection that the URL is expecting
     */
    public static String querytypeToUrl(AZURESEARCH_QUERYTYPE type) {
        if (type == null) {
            return "Web";
        }

        switch (type) {
            case COMPOSITE:
                return "Composite";
            case WEB:
                return "Web";
            case IMAGE:
                return "Image";
            case VIDEO:
                return "Video";
            case NEWS:
                return "News";
            case RELATEDSEARCH:
                return "RelatedSearch";
            case SPELLINGSUGGESTION:
                return "SpellingSuggestions";
            default:
                return "Composite";
        }

    }

    /**
     *
     * @param adult from the AZURESEARCH_QUERYADULT enum
     * @return the string representation of the enum selection that the URL is expecting
     */
    public static String adultToParam(AZURESEARCH_QUERYADULT adult) {
        if (adult == null) {
            return "Off";
        }

        switch (adult) {
            case OFF:
                return "Off";
            case MODERATE:
                return "Moderate";
            case STRICT:
                return "Strict";
            default:
                return "Off";
        }

    }

    /**
     *
     * @param format from the AZURESEARCH_FORMAT enum
     * @return the string representation of the enum selection that the URL is expecting
     */
    public static String formatToParam(AZURESEARCH_FORMAT format) {
        if (format == null) {
            return "Atom";
        }

        switch (format) {
            case JSON:
                return "JSON";
            case XML:
                return "Atom";
            default:
                return "Atom";
        }

    }

    /**
     *
     * @return the number of results to skip for pagination
     */
    public Integer getSkip() {
        return _skip;
    }

    /**
     *
     * @param skip the number of results to skip for pagination
     */
    public void setSkip(Integer skip) {
        _skip = skip;
        if (_skip < 0) {
            _skip = 0;
        }
    }

    /**
     * Set this query to ask for the next page of results
     */
    public void nextPage() {
        this.setSkip(this.getSkip() + this.getPerPage());
    }

    /**
     *
     * @param page Ask for a particular page of results for this query 
     */
    public void setPage(int page) {
        this.setSkip(this.getPerPage() * (page - 1));
    }

}
