package net.billylieurance.azuresearch;

public class AzureSearchWebResult extends AbstractAzureSearchResult {

	/*
	 * <entry>
    <id>https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Web?Query='lieurance'&amp;Market='en-US'&amp;Adult='Moderate'&amp;$skip=0&amp;$top=1</id>
    <title type="text">WebResult</title>
    <updated>2012-08-01T07:03:41Z</updated>
    <content type="application/xml">
      <m:properties>
        <d:ID m:type="Edm.Guid">9b53addd-9d1b-4b67-a1c6-eb814ba3f8e7</d:ID>
        <d:Title m:type="Edm.String">Barbara Lieurance | pianist</d:Title>
        <d:Description m:type="Edm.String">BOWED PIANO 2012-2013: What do you think? Please share your reactions, your experience, and your ideas here by clicking on the title above. This post is for those ...</d:Description>
        <d:DisplayUrl m:type="Edm.String">lieurance.com</d:DisplayUrl>
        <d:Url m:type="Edm.String">http://lieurance.com/</d:Url>
      </m:properties>
    </content>
  </entry>
	 */
	
	private String _displayUrl;

	public String getDisplayUrl() {
		return _displayUrl;
	}

	public void setDisplayURL(String _displayUrl) {
		this._displayUrl = _displayUrl;
	}
	
}
