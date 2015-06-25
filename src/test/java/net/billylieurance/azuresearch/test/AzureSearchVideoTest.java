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

/**
 *
 * @author wlieurance
 */
public class AzureSearchVideoTest extends AbstractAzureSearchTest {

    AzureSearchVideoResult asr;

    /**
     *
     */
    @Test
    public void TestAppid() {
        final String reason = "You need a valid Azure Appid as the static final String AZURE_APPID in net.billylieurance.azuresearch.test.AzureAppid to run these tests.";
        Assert.assertNotNull(AzureAppid.AZURE_APPID, reason);
        Assert.assertNotEquals(AzureAppid.AZURE_APPID, "", reason);
    }

    /**
     *
     */
    @Test
    public void TestConstructor() {
        AzureSearchVideoQuery aq = new AzureSearchVideoQuery();
        Assert.assertNotNull(aq, "Did not generate an actual query object.");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "TestConstructor")
    public void buildQuery() {
        AzureSearchVideoQuery aq = new AzureSearchVideoQuery();
        aq.setQuery("Oklahoma Sooners");

        Assert.assertEquals(aq.getQueryPath(), "/Data.ashx/Bing/Search/v1/Video");
        Assert.assertEquals(aq.getUrlQuery(), "Query='Oklahoma Sooners'&Market='en-US'&$top=15&$format=Atom");
    }

    /**
     *
     */
    @Test(dependsOnMethods = {"TestConstructor", "TestAppid"})
    public void buildQueryResult() {
        AzureSearchVideoQuery aq = new AzureSearchVideoQuery();
        aq.setAppid(AzureAppid.AZURE_APPID);
        aq.setDebug(true);
        aq.setQuery("Oklahoma Sooners");

        logURL(aq);

        aq.doQuery();
        Document ad = aq.getRawResult();
        Assert.assertNotNull(ad);

        AzureSearchResultSet<AzureSearchVideoResult> ars = aq.getQueryResult();
        Assert.assertNotNull(ars, "getQueryResult returned null");
        Assert.assertNotNull(ars.getASRs(), "getQueryResult.getASRs returned null");
        Assert.assertFalse(ars.getASRs().isEmpty(), "getQueryResult returned no results");

        asr = ars.getASRs().get(0);
        Assert.assertNotNull(asr, "Unparseable result from result.");

    }

    /**
     *
     */
    @Test(dependsOnMethods = "buildQueryResult")
    public void getDisplayUrl() {
        Assert.assertNotNull(asr.getDisplayUrl(), "Unparseable DisplayUrl from result");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "buildQueryResult")
    public void getRunTime() {
        Assert.assertNotNull(asr.getRunTime(), "Unparseable RunTime from result");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "buildQueryResult")
    public void getMediaUrl() {
        Assert.assertNotNull(asr.getMediaUrl(), "Unparseable MediaUrl from result");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "buildQueryResult")
    public void getThumbnail() {
        Assert.assertNotNull(asr.getThumbnail(), "Thumbnail never showed up in result");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "getThumbnail")
    public void getThumbnailMediaUrl() {
        Assert.assertNotNull(asr.getThumbnail().getMediaUrl(), "Unparseable Thumbnail MediaUrl in result");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "getThumbnail")
    public void getThumbnailContentType() {
        Assert.assertNotNull(asr.getThumbnail().getContentType(), "Unparseable Thumbnail ContentType in result");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "getThumbnail")
    public void getThumbnailFileSize() {
        Assert.assertNotNull(asr.getThumbnail().getFileSize(), "Unparseable Thumbnail FileSize in result");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "getThumbnail")
    public void getThumbnailHeight() {
        Assert.assertNotNull(asr.getThumbnail().getHeight(), "Unparseable Thumbnail Height in result");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "getThumbnail")
    public void getThumbnailWidth() {
        Assert.assertNotNull(asr.getThumbnail().getWidth(), "Unparseable Thumbnail Width in result");
    }

	  		//Below this are the abstract tests

    /**
     *
     */
        @Test(dependsOnMethods = "buildQueryResult")
    public void getId() {
        Assert.assertNotNull(asr.getId(), "Unparseable ID from result");
    }

    /**
     *
     */
    @Test(dependsOnMethods = "buildQueryResult")
    public void getTitle() {
        Assert.assertNotNull(asr.getTitle(), "Unparseable Title from result");
    }

}
