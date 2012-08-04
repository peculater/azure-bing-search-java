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

public abstract class AbstractAzureSearchQuery<ResultT> {

	private String _query;

	private String _queryOption = "";
	private String _market = "en-US";
	private AZURESEARCH_QUERYADULT _adult = AZURESEARCH_QUERYADULT.OFF;
	//private static final Logger log = Logger
	//		.getLogger(AbstractAzureSearchQuery.class.getName());
	private AzureSearchResultSet<ResultT> _queryResult;
	private Document _rawResult;
	private String _appid;
	private Integer _perPage = 15;
	private String _queryExtra = "";
	private String _latitude = "";
	private String _longitude = "";

	public static enum AZURESEARCH_QUERYTYPE {
		COMPOSITE, WEB, IMAGE, VIDEO, NEWS, RELATEDSEARCH, SPELLINGSUGGESTION
	};

	public static enum AZURESEARCH_QUERYADULT {
		OFF, MODERATE, STRICT
	};

	protected static final String AZURESEARCH_SCHEME = "https";
	protected static final Integer AZURESEARCH_PORT = 443;
	protected static final String AZURESEARCH_AUTHORITY = "api.datamarket.azure.com";
	protected static final String AZURESEARCH_PATH = "/Data.ashx/Bing/Search/v1/";
	// public static final String AZURESEARCH_URL =
	// "https://api.datamarket.azure.com/Bing/SearchWeb/";
	protected static final String AZURESEARCH_URLQUERY = "Query='phase 3'&Adult='Off'&$top=15&$format=Atom";
	HttpHost targetHost = new HttpHost(AZURESEARCH_AUTHORITY, AZURESEARCH_PORT,
			AZURESEARCH_SCHEME);

	// Create AuthCache instance
	AuthCache authCache = new BasicAuthCache();
	// Generate BASIC scheme object and add it to the local
	// auth cache
	BasicScheme basicAuth = new BasicScheme();
	BasicHttpContext localcontext = new BasicHttpContext();

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

		authCache.put(targetHost, basicAuth);

		// Add AuthCache to the execution context
		localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
	}

	public abstract String getAdditionalUrlQuery();

	public String getUrlQuery() {

		// https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Web?Query=%27lieurance%27&Options=%27EnableHilighting%27&WebSearchOptions=%27DisableQueryAlterations%27&Market=%27en-US%27&Adult=%27Moderate%27&Latitude=47.603450&Longitude=-122.329696&$top=50&$format=Atom

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

		sb.append("&Adult='");
		sb.append(adultToParam(this.getAdult()));
		sb.append("'");

		sb.append("&$top=");
		sb.append(this.getPerPage());
		sb.append("&$format=Atom");

		sb.append(this.getAdditionalUrlQuery());

		sb.append(this.getQueryExtra());

		return sb.toString();
		// public static final String AZURESEARCH_URLQUERY =
		// "Query='phase 3'&Adult='Off'&$top=15&$format=Atom";
	}

	public void doQuery() {
		DefaultHttpClient client = new DefaultHttpClient();

		client.getCredentialsProvider().setCredentials(
				new AuthScope(targetHost.getHostName(), targetHost.getPort()),
				new UsernamePasswordCredentials(this.getAppid(), this
						.getAppid()));

		URI uri;
		try {
			String full_path = getQueryPath();
			String full_query = getUrlQuery();
			// AzureSearchUtils.AZURESEARCH_PATH +
			// AzureSearchUtils.querytypeToUrl(AzureSearchUtils.AZURESEARCH_QUERYTYPE.NEWS);
			uri = new URI(AZURESEARCH_SCHEME, AZURESEARCH_AUTHORITY, full_path,
					full_query, null);
			//log.log(Level.WARNING, uri.toString());
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return;
		}
		// String URL = AzureUtils.AZURE_SEARCH_URL +
		// querytypeToUrl(_querytype);

		HttpGet get = new HttpGet(uri);

		get.addHeader("Accept", "application/xml");
		get.addHeader("Content-Type", "application/xml");
		HttpResponse responsePost;
		HttpEntity resEntity;
		try {
			responsePost = client.execute(get);
			resEntity = responsePost.getEntity();
			_rawResult = loadXMLFromStream(resEntity.getContent());

			NodeList parseables = _rawResult.getElementsByTagName("entry");
			_queryResult = new AzureSearchResultSet<ResultT>();
			if (parseables != null) {
				for (int i = 0; i < parseables.getLength(); i++) {
					Node parseable = parseables.item(i);
					_queryResult.addResult(this.parseEntry(parseable));
				}
			}

			// String responseString = EntityUtils.toString(resEntity);
			// log.info(responseString);
			// _queryResult = responseString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

	private static Document loadXMLFromStream(InputStream is)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(is);
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
			return "Composite";

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
			return "SpellingSuggestion";
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

}
