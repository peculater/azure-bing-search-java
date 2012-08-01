package net.billylieurance.azuresearch;

import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;



public final class AzureSearchUtils {

	public static final String AZURESEARCH_SCHEME = "https";
	public static final Integer AZURESEARCH_PORT = 443;
	public static final String AZURESEARCH_AUTHORITY = "api.datamarket.azure.com";
	public static final String AZURESEARCH_PATH = "/Data.ashx/Bing/Search/v1/";
//public static final String AZURESEARCH_URL = "https://api.datamarket.azure.com/Bing/SearchWeb/";
	public static final String AZURESEARCH_URLQUERY = "Query='phase 3'&Adult='Off'&$top=15&$format=Atom";
	
	
	/** Transform Calendar to ISO 8601 string. */
    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /** Get current date and time formatted as ISO 8601 string. */
    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }

    /** Transform ISO 8601 string to Calendar. */
    public static Calendar toCalendar(final String iso8601string)
            throws ParseException {
        Calendar calendar = GregorianCalendar.getInstance();
        String s = iso8601string.replace("Z", "+00:00");
        try {
            s = s.substring(0, 22) + s.substring(23);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("Invalid length", 0);
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(s);
        calendar.setTime(date);
        return calendar;
    }
    
    public static enum AZURESEARCH_QUERYTYPE {
    		COMPOSITE,
    		WEB,
    		IMAGE,
    		VIDEO,
    		NEWS,
    		RELATEDSEARCH,
    		SPELLINGSUGGESTION
    };
    
    public static enum AZURESEARCH_QUERYADULT {
		OFF,
		MODERATE,
		STRICT
    };


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
    
    public static String querytypeToUrl(AZURESEARCH_QUERYTYPE type){
		if (type == null)
			return "Composite";
		
    	switch (type){
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
    
	public static String adultToParam(AZURESEARCH_QUERYADULT adult){
		if (adult == null)
			return "Off";
		
		switch (adult){
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
