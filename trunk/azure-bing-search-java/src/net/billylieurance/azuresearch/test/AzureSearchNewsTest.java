package net.billylieurance.azuresearch.test;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import net.billylieurance.azuresearch.AzureSearchNewsQuery;
import net.billylieurance.azuresearch.AzureSearchNewsResult;
import net.billylieurance.azuresearch.AzureSearchResultSet;

public class AzureSearchNewsTest {

	@Test
	public void TestAppid(){
		final String reason = "You need a valid Azure Appid as the static final String AZURE_APPID in net.billylieurance.azuresearch.test.AzureAppid to run these tests.";
		assert (AzureAppid.AZURE_APPID != null) : reason;
		assert (!AzureAppid.AZURE_APPID.equals("")) : reason;		
	}
	
	@Test 
	public void TestConstructor(){
		AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
		assert (aq != null) : "Did not generate an actual query object.";
	}
	
	@Test
	public void doQuery(){
		AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
		aq.setQuery("Oklahoma Sooners");
		
		aq.doQuery();
		Document ad = aq.getRawResult();
		assert (ad != null) : "getRawResult returned null";
		
		AzureSearchResultSet<AzureSearchNewsResult> ars = aq.getQueryResult();
		assert (ars != null) : "getQueryResult returned null";
		assert (ars.getASRs() != null) : "getQueryResult.getASRs returned null";
		assert (!ars.getASRs().isEmpty()) : "getQueryResult returned no results";
	}
}
