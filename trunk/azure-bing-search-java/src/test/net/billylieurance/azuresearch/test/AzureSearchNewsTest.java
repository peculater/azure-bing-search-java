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

import net.billylieurance.azuresearch.AzureSearchNewsQuery;
import net.billylieurance.azuresearch.AzureSearchNewsResult;
import net.billylieurance.azuresearch.AzureSearchResultSet;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

public class AzureSearchNewsTest extends AbstractAzureSearchTest {

	AzureSearchNewsResult asr;
	
	@Test
	public void TestAppid(){
		final String reason = "You need a valid Azure Appid as the static final String AZURE_APPID in net.billylieurance.azuresearch.test.AzureAppid to run these tests.";
		Assert.assertNotNull(AzureAppid.AZURE_APPID, reason);
		Assert.assertNotEquals(AzureAppid.AZURE_APPID, "", reason);		
	}
	
	@Test 
	public void TestConstructor(){
		AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
		Assert.assertNotNull(aq, "Did not generate an actual query object.");
	}
	
	@Test
	(dependsOnMethods = "TestConstructor")
	public void buildQuery(){
		AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
		aq.setQuery("Oklahoma Sooners");
		aq.setCategory("rt_Sports");
		aq.setLocationOverride("US.OK");
		aq.setSortBy("Date");
		
		Assert.assertEquals(aq.getQueryPath(), "/Data.ashx/Bing/Search/v1/News");
		Assert.assertEquals(aq.getUrlQuery(),"Query='Oklahoma Sooners'&Market='en-US'&$top=15&$format=Atom&NewsLocationOverride='US.OK'&NewsCategory='rt_Sports'&NewsSortBy='Date'");
	}
	
	@Test
	(dependsOnMethods = {"TestConstructor", "TestAppid"})
	public void buildQueryResult(){
		AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
		aq.setQuery("Oklahoma Sooners");
		aq.setCategory("rt_Sports");
		aq.setLocationOverride("US.OK");
		aq.setSortBy("Date");
		
		logURL(aq);
		
		aq.doQuery();
		Document ad = aq.getRawResult();
		Assert.assertNotNull(ad);
		
		AzureSearchResultSet<AzureSearchNewsResult> ars = aq.getQueryResult();
		Assert.assertNotNull(ars, "getQueryResult returned null");
		Assert.assertNotNull(ars.getASRs(), "getQueryResult.getASRs returned null");
		Assert.assertFalse(ars.getASRs().isEmpty(), "getQueryResult returned no results");
	
		asr = ars.getASRs().get(0);
		Assert.assertNotNull(asr, "Unparseable result from result.");
		
	}
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void getDate(){		
		Assert.assertNotNull(asr.getDate(), "Unparseable date from result");
	}
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void getSource(){		
		Assert.assertNotNull(asr.getSource(), "Unparseable Source from result");
	}
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void getUrl(){		
		Assert.assertNotNull(asr.getUrl(), "Unparseable URL from result");
	}
	
	@Test
	(dependsOnMethods = "buildQueryResult")
	public void getDescription(){		
		Assert.assertNotNull(asr.getDescription(), "Unparseable Description from result");
	}
	
	
	//Below this are the abstract tests
	
			@Test
			(dependsOnMethods = "buildQueryResult")
			public void getId(){		
				Assert.assertNotNull(asr.getId(), "Unparseable ID from result");
			}
			
			@Test
			(dependsOnMethods = "buildQueryResult")
			public void getTitle(){		
				Assert.assertNotNull(asr.getTitle(), "Unparseable Title from result");
			}
	
	
	
	
}
