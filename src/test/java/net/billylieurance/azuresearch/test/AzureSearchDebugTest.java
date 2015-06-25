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
import java.io.IOException;
import java.io.InputStream;

import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AzureSearchDebugTest extends AbstractAzureSearchTest {

    @Test
    public void TestAppid() {
        final String reason = "You need a valid Azure Appid as the static final String AZURE_APPID in net.billylieurance.azuresearch.test.AzureAppid to run these tests.";
        Assert.assertNotNull(AzureAppid.AZURE_APPID, reason);
        Assert.assertNotEquals(AzureAppid.AZURE_APPID, "", reason);
    }

    @Test
    public void TestConstructor() {
        AzureSearchWebQuery aq = new AzureSearchWebQuery();
        Assert.assertNotNull(aq, "Did not generate an actual query object.");
    }

    @Test(dependsOnMethods = {"TestConstructor", "TestAppid"})
    public void TestDebug() {

        AzureSearchWebQuery aq = new AzureSearchWebQuery();
        aq.setAppid(AzureAppid.AZURE_APPID);
        aq.setQuery("Oklahoma Sooners");

        aq.setProcessHTTPResults(false);
        aq.doQuery();

		//Really all we're doing here is dumping the InputStream to a file.
        //There are other ways that might be cleaner depending on what tools you have available.
        java.util.Scanner s;
        String debugResult;
        try {
            s = new java.util.Scanner(aq.getResEntity().getContent()).useDelimiter("\\A");
            debugResult = s.hasNext() ? s.next() : "";
        } catch (IllegalStateException e) {
            Assert.fail("IllegalStateException thrown from scanner ");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            Assert.fail("IOException from Scanner");
            return;
        } catch (NullPointerException e) {
            Assert.fail("aq.getResEntity was probably null");
            return;
        }

		//   System.out.print(debugResult);
        if (debugResult.startsWith("<feed")) {
            aq.setProcessHTTPResults(true);
            aq.doQuery();
        } else {
            System.out.print(debugResult);
            Assert.fail("Did not get a valid XML result back.");
        }

        AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();
        Assert.assertNotNull(ars, "getQueryResult returned null");
        Assert.assertNotNull(ars.getASRs(), "getQueryResult.getASRs returned null");
        Assert.assertFalse(ars.getASRs().isEmpty(), "getQueryResult returned no results");

        AzureSearchWebResult asr = ars.getASRs().get(0);
        Assert.assertNotNull(asr, "Unparseable result from result.");

    }

    @Test(dependsOnMethods = {"TestConstructor", "TestAppid"})
    public void TestDebugDirect() {
        AzureSearchWebQuery aq = new AzureSearchWebQuery();
        aq.setAppid(AzureAppid.AZURE_APPID);
        aq.setQuery("Oklahoma Sooners");

        aq.setProcessHTTPResults(false);
        aq.setDebug(true);
        aq.doQuery();

		//Really all we're doing here is dumping the InputStream to a file.
        //There are other ways that might be cleaner depending on what tools you have available.
        java.util.Scanner s;
        String debugResult;
        try {
            s = new java.util.Scanner(aq.getResEntity().getContent()).useDelimiter("\\A");
            debugResult = s.hasNext() ? s.next() : "";
        } catch (IllegalStateException e) {
            Assert.fail("IllegalStateException thrown from scanner ");
            e.printStackTrace();
            return;
        } catch (IOException e) {
            Assert.fail("IOException from Scanner");
            return;
        } catch (NullPointerException e) {
            Assert.fail("aq.getResEntity was probably null");
            return;
        }

		//   System.out.print(debugResult);
        if (debugResult.startsWith("<feed")) {
            InputStream istwo = new java.io.ByteArrayInputStream(debugResult.getBytes());
            aq.setRawResult(aq.loadXMLFromStream(istwo));
            aq.loadResultsFromRawResults();
        } else {
            System.out.print(debugResult);
            Assert.fail("Did not get a valid XML result back.");
        }

        AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();
        Assert.assertNotNull(ars, "getQueryResult returned null");
        Assert.assertNotNull(ars.getASRs(), "getQueryResult.getASRs returned null");
        Assert.assertFalse(ars.getASRs().isEmpty(), "getQueryResult returned no results");

        AzureSearchWebResult asr = ars.getASRs().get(0);
        Assert.assertNotNull(asr, "Unparseable result from result.");
    }

}
