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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.billylieurance.azuresearch.AbstractAzureSearchQuery.AZURESEARCH_API;
import net.billylieurance.azuresearch.AbstractAzureSearchQuery.AZURESEARCH_FORMAT;
import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;

import org.apache.http.HttpEntity;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

public class AzureSearchWebJSONTest extends AbstractAzureSearchTest {

	AzureSearchWebResult asr;
	
	@Test
	public void TestAppid(){
		final String reason = "You need a valid Azure Appid as the static final String AZURE_APPID in net.billylieurance.azuresearch.test.AzureAppid to run these tests.";
		Assert.assertNotNull(AzureAppid.AZURE_APPID, reason);
		Assert.assertNotEquals(AzureAppid.AZURE_APPID, "", reason);		
	}
	
	@Test 
	public void TestConstructor(){
		AzureSearchWebQuery aq = new AzureSearchWebQuery();
		Assert.assertNotNull(aq, "Did not generate an actual query object.");
	}
	
	@Test
	(dependsOnMethods = "TestConstructor")
	public void buildQuery(){
		AzureSearchWebQuery aq = new AzureSearchWebQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
		aq.setFormat(AZURESEARCH_FORMAT.JSON);
		aq.setQuery("Oklahoma Sooners");
		
		Assert.assertEquals(aq.getBingApi(), AZURESEARCH_API.BINGSEARCH);
		Assert.assertEquals(aq.getQueryPath(), "/Data.ashx/Bing/Search/v1/Web");
		Assert.assertEquals(aq.getUrlQuery(),"Query='Oklahoma Sooners'&Market='en-US'&$top=15&$format=JSON");
	}
	
	@Test
	(dependsOnMethods = {"TestConstructor", "TestAppid"})
	public void buildQueryResult(){
		AzureSearchWebQuery aq = new AzureSearchWebQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
                aq.setDebug(true);
		aq.setFormat(AZURESEARCH_FORMAT.JSON);
		aq.setQuery("Oklahoma Sooners");
		
		logURL(aq);
		
		aq.doQuery();
		Document ad = aq.getRawResult();
		Assert.assertNull(ad);
		
		HttpEntity he = aq.getResEntity();
		Assert.assertNotNull(he, "Didn't get an HTTP Entity back");
		
		try {
			InputStream is = he.getContent();
			InputStreamReader isr = new InputStreamReader(is);
			StringBuilder sb=new StringBuilder();
			BufferedReader br = new BufferedReader(isr);
			String read = br.readLine();

			while(read != null) {
			    //System.out.println(read);
			    sb.append(read);
			    read = br.readLine();

			}

			String JSONString = sb.toString();
			Assert.assertNotNull(JSONString, "Got a null JSON string in return");
			Assert.assertNotEquals(JSONString, "", "Got an empty JSON string in return");

		} catch (IllegalStateException e) {
			Assert.fail("IllegalStateException thrown from the HTTPEntity on getting content");
		} catch (IOException e) {
			Assert.fail("IOException thrown from the HTTPEntity on getting content");
		}
		
		
		AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();
		Assert.assertNull(ars, "getQueryResult returned something somehow");

	}
	
	
	
}
