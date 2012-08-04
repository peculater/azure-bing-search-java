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

import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchVideoQuery;
import net.billylieurance.azuresearch.AzureSearchVideoResult;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;

public class AzureSearchVideoTest {

	AzureSearchVideoResult asr;
	
	@Test
	public void TestAppid(){
		final String reason = "You need a valid Azure Appid as the static final String AZURE_APPID in net.billylieurance.azuresearch.test.AzureAppid to run these tests.";
		assert (AzureAppid.AZURE_APPID != null) : reason;
		assert (!AzureAppid.AZURE_APPID.equals("")) : reason;		
	}
	
	@Test 
	public void TestConstructor(){
		AzureSearchVideoQuery aq = new AzureSearchVideoQuery();
		assert (aq != null) : "Did not generate an actual query object.";
	}
	
	@Test
	(dependsOnMethods = "TestConstructor")
	public void buildQuery(){
		AzureSearchVideoQuery aq = new AzureSearchVideoQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
		aq.setQuery("Oklahoma Sooners");
		
		Assert.assertEquals(aq.getQueryPath(), "/Data.ashx/Bing/Search/v1/Video");
		Assert.assertEquals(aq.getUrlQuery(),"Query='Oklahoma Sooners'&Adult='Off'&$top=15&$format=Atom");
	}
	
	@Test
	(dependsOnMethods = {"TestConstructor", "TestAppid"})
	public void buildQueryResult(){
		AzureSearchVideoQuery aq = new AzureSearchVideoQuery();
		aq.setAppid(AzureAppid.AZURE_APPID);
		aq.setQuery("Oklahoma Sooners");
		
		aq.doQuery();
		Document ad = aq.getRawResult();
		assert (ad != null) : "getRawResult returned null";
		
		AzureSearchResultSet<AzureSearchVideoResult> ars = aq.getQueryResult();
		assert (ars != null) : "getQueryResult returned null";
		assert (ars.getASRs() != null) : "getQueryResult.getASRs returned null";
		assert (!ars.getASRs().isEmpty()) : "getQueryResult returned no results";
	
		asr = ars.getASRs().get(0);
		assert (asr != null) : "Unparseable result from result.";
		
	}
	
	

	  @Test
	  (dependsOnMethods = "buildQueryResult")
	  public void getDisplayUrl() {
		  assert (asr.getDisplayUrl() != null) : "Unparseable DisplayUrl from result";
	  }

	  @Test
	  (dependsOnMethods = "buildQueryResult")
	  public void getRunTime() {
		  assert (asr.getRunTime() != null) : "Unparseable RunTime from result";
	  }

	  @Test
	  (dependsOnMethods = "buildQueryResult")
	  public void getMediaUrl() {
		  assert (asr.getMediaUrl() != null) : "Unparseable MediaUrl from result";
	  }
	  
	  @Test
	  (dependsOnMethods = "buildQueryResult")
	  public void getThumbnail() {
		  assert (asr.getThumbnail() != null) : "Thumbnail never showed up in result";
	  }
	  
	  @Test
	  (dependsOnMethods = "getThumbnail")
	  public void getThumbnailMediaUrl() {
		  assert (asr.getThumbnail().getMediaUrl() != null) : "Unparseable Thumbnail MediaUrl in result";
	  }
	  
	  @Test
	  (dependsOnMethods = "getThumbnail")
	  public void getThumbnailContentType() {
		  assert (asr.getThumbnail().getContentType() != null) : "Unparseable Thumbnail ContentType in result";
	  }
	  
	  @Test
	  (dependsOnMethods = "getThumbnail")
	  public void getThumbnailFileSize() {
		  assert (asr.getThumbnail().getFileSize() != null) : "Unparseable Thumbnail FileSize in result";
	  }
	  
	  @Test
	  (dependsOnMethods = "getThumbnail")
	  public void getThumbnailHeight() {
		  assert (asr.getThumbnail().getHeight() != null) : "Unparseable Thumbnail Height in result";
	  }
	  
	  @Test
	  (dependsOnMethods = "getThumbnail")
	  public void getThumbnailWidth() {
		  assert (asr.getThumbnail().getWidth() != null) : "Unparseable Thumbnail Width in result";
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

}
