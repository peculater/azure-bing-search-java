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
import java.net.URI;
import java.net.URISyntaxException;

import net.billylieurance.azuresearch.AbstractAzureSearchQuery;

public class AbstractAzureSearchTest {

    public void logURL(@SuppressWarnings("rawtypes") AbstractAzureSearchQuery aq) {
        String full_path = aq.getQueryPath();
        String full_query = aq.getUrlQuery();

        try {
            URI uri = new URI(AbstractAzureSearchQuery.AZURESEARCH_SCHEME, AbstractAzureSearchQuery.AZURESEARCH_AUTHORITY, full_path,
                    full_query, null);
			// Bing and java URI disagree about how to represent + in query
            // parameters. This is what we have to do instead...

            uri = new URI(uri.getScheme() + "://" + uri.getAuthority()
                    + uri.getPath() + "?"
                    + uri.getRawQuery().replace("+", "%2b"));
            System.out.println(uri.toString());
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
