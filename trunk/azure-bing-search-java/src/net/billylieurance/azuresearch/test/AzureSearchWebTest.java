package net.billylieurance.azuresearch.test;

import org.testng.annotations.Test;
import org.w3c.dom.Document;

import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;
import net.billylieurance.azuresearch.AzureSearchResultSet;

public class AzureSearchWebTest {

	AzureSearchWebResult asr;
	
	@Test
	public void TestAppid(){
		final String reason = "You need a valid Azure Appid as the static final String AZURE_APPID in net.billylieurance.azuresearch.test.AzureAppid to run these tests.";
		assert (AzureAppid.AZURE_APPID != null) : reason;
		assert (!AzureAppid.AZURE_APPID.equals("")) : reason;		
	}
	
	@Test 
	(dependsOnMethods = "TestAppid")
	public void TestConstructor(){
		AzureSearchWebQuery aq = new AzureSearchWebQuery();
		assert (aq != null) : "Did not generate an actual query object.";
	}
	
	@Test
	(dependsOnMethods = "TestConstructor")
	public void buildQueryResult(){
		AzureSearchWebQuery aq = new AzureSearchWebQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
		aq.setQuery("Oklahoma Sooners");
		
		aq.doQuery();
		Document ad = aq.getRawResult();
		assert (ad != null) : "getRawResult returned null";
		
		AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();
		assert (ars != null) : "getQueryResult returned null";
		assert (ars.getASRs() != null) : "getQueryResult.getASRs returned null";
		assert (!ars.getASRs().isEmpty()) : "getQueryResult returned no results";
	
		asr = ars.getASRs().get(0);
		assert (asr != null) : "Unparseable result from result.";
		
	}
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void checkDisplayUrl(){		
		assert (asr.getDisplayUrl() != null) : "Unparseable DisplayURL from result";
	}
	
	
	
	//Below this are the abstract tests
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void checkId(){		
		assert (asr.getId() != null) : "Unparseable ID from result";
	}
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void checkTitle(){		
		assert (asr.getTitle() != null) : "Unparseable Title from result";
	}
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void checkUrl(){		
		assert (asr.getUrl() != null) : "Unparseable URL from result";
	}
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void checkDescription(){		
		assert (asr.getDescription() != null) : "Unparseable Description from result";
	}
}
