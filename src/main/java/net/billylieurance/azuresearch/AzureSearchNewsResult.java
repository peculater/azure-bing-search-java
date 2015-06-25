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

public class AzureSearchNewsResult extends AbstractAzureSearchResult {

    /*
     * <entry>
     * <id>https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/News
     * ?Query='phase 3'&amp;Adult='Off'&amp;$skip=0&amp;$top=1</id> <title
     * type="text">NewsResult</title> <updated>2012-07-28T07:27:39Z</updated>
     * <content type="application/xml"> <m:properties> <d:ID
     * m:type="Edm.Guid">51da90fe-79d9-450b-b04d-471b766e6fc1</d:ID> <d:Title
     * m:type="Edm.String">Amicus Therapeutics: Rare Disease Drug Developer
     * Expected To Reveal Phase 3 Results In 3Q12</d:Title> <d:Url
     * m:type="Edm.String"
     * >http://seekingalpha.com/article/753941-amicus-therapeutics
     * -rare-disease-drug-developer-expected-to-reveal-phase-3-results-in-3q12
     * </d:Url> <d:Source m:type="Edm.String">Seekingalpha.com</d:Source>
     * <d:Description m:type="Edm.String">Amicus Therapeutics (FOLD) is focused
     * on the development of new treatments for rare metabolic disorders,
     * including lead product candidate AMIGAL (migalastat), which is summarized
     * below. Pivotal Phase 3 results are expected this quarter as part of a
     * ...</d:Description> <d:Date
     * m:type="Edm.DateTime">2012-07-26T20:26:55Z</d:Date> </m:properties>
     * </content> </entry>
     */

    /**
     *
     */
    
    protected String _source;

    /**
     *
     * @return
     */
    public String getSource() {
        return _source;
    }

    /**
     *
     * @param _source
     */
    protected void setSource(String _source) {
        this._source = _source;
    }

    /**
     *
     */
    protected String _date;

    /**
     *
     * @return
     */
    public String getDate() {
        return _date;
    }

    /**
     *
     * @param _date
     */
    public void setDate(String _date) {
        this._date = _date;
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
