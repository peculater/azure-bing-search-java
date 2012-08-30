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

public abstract class AbstractAzureSearchQuery<ResultT> {

	private String _query;

	private String _queryOption = "";
	private String _market = "en-US";
	private AZURESEARCH_QUERYADULT _adult = null;
	protected AZURESEARCH_API _bingApi = AZURESEARCH_API.BINGSEARCH;
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

	public static enum AZURESEARCH_QUERYTYPE {
		COMPOSITE, WEB, IMAGE, VIDEO, NEWS, RELATEDSEARCH, SPELLINGSUGGESTION
	};

	public static enum AZURESEARCH_QUERYADULT {
		OFF, MODERATE, STRICT
	};

	public static enum AZURESEARCH_API {
		BINGSEARCH, BINGSEARCHWEBONLY
	}
	
	public static enum AZURESEARCH_FORMAT {
		JSON, XML
	}

	public static final String AZURESEARCH_SCHEME = "https";
	public static final Integer AZURESEARCH_PORT = 443;
	public static final String AZURESEARCH_AUTHORITY = "api.datamarket.azure.com";
	public static final String AZURESEARCH_PATH = "/Data.ashx/Bing/Search/v1/";
	public static final String AZURESEARCHWEB_PATH = "/Data.ashx/Bing/SearchWeb/v1/";

	// HTTP objects
	protected HttpHost _targetHost = new HttpHost(AZURESEARCH_AUTHORITY,
			AZURESEARCH_PORT, AZURESEARCH_SCHEME);
	protected AuthCache _authCache = new BasicAuthCache();
	protected BasicScheme _basicAuth = new BasicScheme();
	protected BasicHttpContext _localcontext = new BasicHttpContext();
	protected HttpResponse _responsePost;
	protected HttpEntity _resEntity;

	/**
	 * @return the responsePost
	 */
	public HttpResponse getResponsePost() {
		return _responsePost;
	}

	/**
	 * @param responsePost
	 *            the responsePost to set
	 */
	protected void setResponsePost(HttpResponse responsePost) {
		this._responsePost = responsePost;
	}

	/**
	 * @return the resEntity
	 */
	public HttpEntity getResEntity() {
		return _resEntity;
	}

	/**
	 * @param resEntity
	 *            the resEntity to set
	 */
	protected void setResEntity(HttpEntity resEntity) {
		this._resEntity = resEntity;
	}

	/**
	 * @return the adult
	 */
	protected AZURESEARCH_QUERYADULT getAdult() {
		return _adult;
	}

	/**
	 * @param adult
	 *            the adult to set
	 */
	protected void setAdult(AZURESEARCH_QUERYADULT adult) {
		_adult = adult;
	}

	/**
	 * @return the queryResult
	 */
	public AzureSearchResultSet<ResultT> getQueryResult() {
		return _queryResult;
	}

	/**
	 * @param queryResult
	 *            the queryResult to set
	 */
	protected void setQueryResult(AzureSearchResultSet<ResultT> queryResult) {
		_queryResult = queryResult;
	}

	public Document getRawResult() {
		return _rawResult;
	}

	protected void setRawResult(Document _rawResult) {
		this._rawResult = _rawResult;
	}

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

	public abstract String getQueryPath();

	public String getQueryOption() {
		return _queryOption;
	}

	public void setQueryOption(String _queryOption) {
		this._queryOption = _queryOption;
	}

	public String getMarket() {
		return _market;
	}

	public void setMarket(String _market) {
		this._market = _market;
	}

	public abstract ResultT parseEntry(Node entry);

	// cast _queryresult to the right thing
	/**
	 * @return the query
	 */
	public String getQuery() {
		return _query;
	}

	/**
	 * @param query
	 *            the query to set
	 */
	public void setQuery(String query) {
		_query = query;
	}

	/**
	 * @return the perPage
	 */
	public Integer getPerPage() {
		return _perPage;
	}

	/**
	 * @param perPage
	 *            the perPage to set
	 */
	public void setPerPage(Integer perPage) {
		_perPage = perPage;
	}

	public AbstractAzureSearchQuery() {
		super();

		// Generate BASIC scheme object and add it to the local
		// auth cache
		_authCache.put(_targetHost, _basicAuth);

		// Add AuthCache to the execution context
		_localcontext.setAttribute(ClientContext.AUTH_CACHE, _authCache);
	}

	public abstract String getAdditionalUrlQuery();

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
		
		if (_market != null) {
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
					+ uri.getRawQuery().replace("+", "%2b"));

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
				if (_rawResult != null) {
					NodeList parseables = _rawResult
							.getElementsByTagName("entry");
					_queryResult = new AzureSearchResultSet<ResultT>();
					if (parseables != null) {
						for (int i = 0; i < parseables.getLength(); i++) {
							Node parseable = parseables.item(i);
							ResultT ar = this.parseEntry(parseable);
							if (ar != null)
								_queryResult.addResult(ar);
						}
					}
				}
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

	private static Document loadXMLFromStream(InputStream is) {
		try {

			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			if (e instanceof SAXParseException) {
				SAXParseException ex = (SAXParseException) e;
				System.out.println("File: " + ex.getSystemId());
				System.out.println("Public: " + ex.getPublicId());
				System.out.println("Line: " + ex.getLineNumber());
				System.out.println("Line: " + ex.getColumnNumber());
			}
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getAppid() {
		return _appid;
	}

	public void setAppid(String appid) {
		_appid = appid;
	}

	public String getQueryExtra() {
		return _queryExtra;
	}

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
	 * @param latitude
	 *            the latitude to set
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
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		_longitude = longitude;
	}

	public Boolean getProcessHTTPResults() {
		if (this.getFormat() == AZURESEARCH_FORMAT.JSON)
			return false;
		return _processHTTPResults;
	}

	public void setProcessHTTPResults(Boolean processHTTPResults) {
		_processHTTPResults = processHTTPResults;
	}

	/**
	 * @return the format
	 */
	public AZURESEARCH_FORMAT getFormat() {
		return _format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(AZURESEARCH_FORMAT format) {
		_format = format;
	}

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

	public static String querytypeToUrl(AZURESEARCH_QUERYTYPE type) {
		if (type == null)
			return "Web";

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

	public static String adultToParam(AZURESEARCH_QUERYADULT adult) {
		if (adult == null)
			return "Off";

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
	
	public static String formatToParam(AZURESEARCH_FORMAT format) {
		if (format == null)
			return "Atom";

		switch (format) {
		case JSON:
			return "JSON";
		case XML:
			return "Atom";
		default:
			return "Atom";
		}

	}

	public Integer getSkip() {
		return _skip;
	}

	public void setSkip(Integer skip) {
		_skip = skip;
		if (_skip < 0)
			_skip = 0;
	}

	public void nextPage() {
		this.setSkip(this.getSkip() + this.getPerPage());
	}

	public void setPage(int page) {
		this.setSkip(this.getPerPage() * (page - 1));
	}

}
