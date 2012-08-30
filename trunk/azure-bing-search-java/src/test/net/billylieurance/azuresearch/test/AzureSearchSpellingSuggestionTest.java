package net.billylieurance.azuresearch.test;

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

import net.billylieurance.azuresearch.AzureSearchSpellingSuggestionQuery;
import net.billylieurance.azuresearch.AzureSearchSpellingSuggestionResult;
import net.billylieurance.azuresearch.AzureSearchResultSet;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

public class AzureSearchSpellingSuggestionTest extends AbstractAzureSearchTest {

	AzureSearchSpellingSuggestionResult asr;
	AzureSearchSpellingSuggestionResult asr_null;
	
	@Test
	public void TestAppid(){
		final String reason = "You need a valid Azure Appid as the static final String AZURE_APPID in net.billylieurance.azuresearch.test.AzureAppid to run these tests.";
		Assert.assertNotNull(AzureAppid.AZURE_APPID, reason);
		Assert.assertNotEquals(AzureAppid.AZURE_APPID, "", reason);		
	}
	
	@Test 
	public void TestConstructor(){
		AzureSearchSpellingSuggestionQuery aq = new AzureSearchSpellingSuggestionQuery();
		Assert.assertNotNull(aq, "Did not generate an actual query object.");
	}
	
	@Test
	(dependsOnMethods = "TestConstructor")
	public void buildQuery(){
		AzureSearchSpellingSuggestionQuery aq = new AzureSearchSpellingSuggestionQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
		aq.setQuery("Oklahoma Sooners");
		
		Assert.assertEquals(aq.getQueryPath(), "/Data.ashx/Bing/Search/v1/SpellingSuggestions");
		Assert.assertEquals(aq.getUrlQuery(),"Query='Oklahoma Sooners'&Market='en-US'&$top=15&$format=Atom");
	}
	
	@Test
	(dependsOnMethods = {"TestConstructor", "TestAppid"})
	public void buildQueryResult(){
		AzureSearchSpellingSuggestionQuery aq = new AzureSearchSpellingSuggestionQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
		aq.setQuery("Okahoma Sooners");
		
		logURL(aq);
		
		aq.doQuery();
		Document ad = aq.getRawResult();
		Assert.assertNotNull(ad);
		
		AzureSearchResultSet<AzureSearchSpellingSuggestionResult> ars = aq.getQueryResult();
		Assert.assertNotNull(ars, "getQueryResult returned null");
		Assert.assertNotNull(ars.getASRs(), "getQueryResult.getASRs returned null");
		Assert.assertFalse(ars.getASRs().isEmpty(), "getQueryResult returned no results");
	
		asr = ars.getASRs().get(0);
		Assert.assertNotNull(asr, "Unparseable result from result.");
		
	}
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void getValue(){		
		Assert.assertNotNull(asr.getValue(), "Unparseable Value from result");
	}
	
	@Test
	(dependsOnMethods = {"TestConstructor", "TestAppid"})
	public void buildNullQueryResult(){
		AzureSearchSpellingSuggestionQuery aq = new AzureSearchSpellingSuggestionQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
		aq.setQuery("Okahoma Sooners");
		
		aq.doQuery();
		Document ad = aq.getRawResult();
		Assert.assertNotNull(ad);
		
		AzureSearchResultSet<AzureSearchSpellingSuggestionResult> ars = aq.getQueryResult();
		Assert.assertNotNull(ars, "getQueryResult returned null");
		Assert.assertNotNull(ars.getASRs(), "getQueryResult.getASRs returned null");
		
	}
	
	
	
	
	
	//Below this are the abstract tests
	
			@Test
			(dependsOnMethods = "buildQueryResult")
			public void getId(){		
				Assert.assertNotNull(asr.getId(), "Unparseable ID from result");
			}
			
			
	
	
}
