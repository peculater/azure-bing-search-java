package net.billylieurance.azuresearch;

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

/**
 *
 * @author wlieurance
 */

public class AzureSearchRelatedSearchResult extends AbstractAzureSearchResult {

    /*
     <content type="application/xml">
     <m:properties>
     <d:ID m:type="Edm.Guid">06de675d-02f8-42ad-b9e4-cfe1062caaa9</d:ID>
     <d:Title m:type="Edm.String">Oklahoma Sooners Wallpaper</d:Title>
     <d:BingUrl m:type="Edm.String">http://www.bing.com/search?q=Oklahoma+Sooners+Wallpaper</d:BingUrl>
     </m:properties>
     </content>
     */
    private String _bingUrl;

    /**
     *
     * @return
     */
    public String getBingUrl() {
        return _bingUrl;
    }

    /**
     *
     * @param bingUrl
     */
    public void setBingUrl(String bingUrl) {
        _bingUrl = bingUrl;
    }

}
