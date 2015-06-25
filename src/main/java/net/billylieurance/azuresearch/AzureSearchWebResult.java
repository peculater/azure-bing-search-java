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

public class AzureSearchWebResult extends AbstractAzureSearchResult {

    /*
     * <entry>
     * <id>https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Web?Query=
     * 'lieurance'&amp;Market='en-US'&amp;Adult='Moderate'&amp;$skip=0&amp;$top=1</id>
     * <title type="text">WebResult</title>
     * <updated>2012-08-01T07:03:41Z</updated> <content type="application/xml">
     * <m:properties> <d:ID
     * m:type="Edm.Guid">9b53addd-9d1b-4b67-a1c6-eb814ba3f8e7</d:ID> <d:Title
     * m:type="Edm.String">Barbara Lieurance | pianist</d:Title> <d:Description
     * m:type="Edm.String">BOWED PIANO 2012-2013: What do you think? Please
     * share your reactions, your experience, and your ideas here by clicking on
     * the title above. This post is for those ...</d:Description> <d:DisplayUrl
     * m:type="Edm.String">lieurance.com</d:DisplayUrl> <d:Url
     * m:type="Edm.String">http://lieurance.com/</d:Url> </m:properties>
     * </content> </entry>
     */
    private String _displayUrl;

    /**
     *
     * @return
     */
    public String getDisplayUrl() {
        return _displayUrl;
    }

    /**
     *
     * @param _displayUrl
     */
    public void setDisplayUrl(String _displayUrl) {
        this._displayUrl = _displayUrl;
    }

    /**
     *
     */
    protected String _description;

    /**
     *
     * @return
     */
    public String getDescription() {
        return _description;
    }

    /**
     *
     * @param _description
     */
    public void setDescription(String _description) {
        this._description = _description;
    }

    /**
     *
     */
    protected String _url;

    /**
     *
     * @return
     */
    public String getUrl() {
        return _url;
    }

    /**
     *
     * @param _url
     */
    public void setUrl(String _url) {
        this._url = _url;
    }

}
