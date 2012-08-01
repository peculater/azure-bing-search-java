package net.billylieurance.azuresearch;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
	private AzureSearchUtils.AZURESEARCH_QUERYADULT _adult;
	private static final Logger log = Logger.getLogger(AbstractAzureSearchQuery.class.getName());
	private AzureSearchResultSet<ResultT> _queryResult;
	private Document _rawResult;
	private String _appid;
	private Integer _perPage = 15;
	private String _queryExtra = "";
	private String _latitude = "";
	private String _longitude = "";
	

	/**
	 * @return the adult
	 */
	protected AzureSearchUtils.AZURESEARCH_QUERYADULT getAdult() {
		return _adult;
	}

	/**
	 * @param adult the adult to set
	 */
	protected void setAdult(AzureSearchUtils.AZURESEARCH_QUERYADULT adult) {
		_adult = adult;
	}

	
	
	/**
	 * @return the queryResult
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

	//cast _queryresult to the right thing
	/**
	 * @return the query
	 */
	public String getQuery() {
		return _query;
	}

	/**
	 * @param query the query to set
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
	 * @param perPage the perPage to set
	 */
	public void setPerPage(Integer perPage) {
		_perPage = perPage;
	}

	public AbstractAzureSearchQuery() {
		super();
	}

	
	public abstract String getAdditionalUrlQuery();
	public String getUrlQuery(){
		
		//https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Web?Query=%27lieurance%27&Options=%27EnableHilighting%27&WebSearchOptions=%27DisableQueryAlterations%27&Market=%27en-US%27&Adult=%27Moderate%27&Latitude=47.603450&Longitude=-122.329696&$top=50&$format=Atom
			
		StringBuilder sb = new StringBuilder(4);
		sb.append("Query='");
		sb.append(this.getQuery());
		sb.append("'");
		
		if (! this.getQueryOption().equals("")){
			sb.append("&Options='");
			sb.append(this.getQueryOption());
			sb.append("'");
		}
		
		if (! this.getLatitude().equals("")){
			sb.append("&Latitude=");
			sb.append(this.getLatitude());
		}
		
		if (! this.getLongitude().equals("")){
			sb.append("&Longitude=");
			sb.append(this.getLongitude());
		}
		
		sb.append("&Adult='");
		sb.append(AzureSearchUtils.adultToParam(this.getAdult()));
		sb.append("'");
		
		sb.append("&$top=");
		sb.append(this.getPerPage());
		sb.append("&$format=Atom");
		
		sb.append(this.getAdditionalUrlQuery());
		
		sb.append(this.getQueryExtra());
		
		return sb.toString();
		//public static final String AZURESEARCH_URLQUERY = "Query='phase 3'&Adult='Off'&$top=15&$format=Atom";
	}
	
	public void doQuery(){
		HttpHost targetHost = new HttpHost(AzureSearchUtils.AZURESEARCH_AUTHORITY, AzureSearchUtils.AZURESEARCH_PORT, AzureSearchUtils.AZURESEARCH_SCHEME);
		DefaultHttpClient client = new DefaultHttpClient(); 
		
		client.getCredentialsProvider().setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials(this.getAppid(), this.getAppid()));

        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local
        // auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        // Add AuthCache to the execution context
        BasicHttpContext localcontext = new BasicHttpContext();
        localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);
		
		
		URI uri;
        try {
        	String full_path = getQueryPath();
        	String full_query = getUrlQuery();
        	//AzureSearchUtils.AZURESEARCH_PATH + AzureSearchUtils.querytypeToUrl(AzureSearchUtils.AZURESEARCH_QUERYTYPE.NEWS);
			uri = new URI(AzureSearchUtils.AZURESEARCH_SCHEME, AzureSearchUtils.AZURESEARCH_AUTHORITY, full_path , full_query, null );
			log.log(Level.WARNING, uri.toString());
        } catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		//String URL = AzureUtils.AZURE_SEARCH_URL + querytypeToUrl(_querytype);
        
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
			if (parseables != null){
				for (int i = 0; i < parseables.getLength(); i++){
					Node parseable = parseables.item(i);
						_queryResult.addResult(this.parseEntry(parseable));	
					}
			}
			
			//String responseString = EntityUtils.toString(resEntity);
			//log.info(responseString);
			//_queryResult = responseString;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
         
	}
	
	private static Document loadXMLFromStream(InputStream is) throws ParserConfigurationException, SAXException, IOException
    {
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

	
}
