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

public class AzureSearchVideoResult extends AbstractAzureSearchResult {

    /*
     *  <d:ID m:type="Edm.Guid">d5703b09-0b21-4991-acc9-68913946c673</d:ID>
     <d:Title m:type="Edm.String">The Panini America "Countdown to Elite" Interview: New York Jets ...</d:Title>
     <d:MediaUrl m:type="Edm.String">http://www.youtube.com/watch?v=qhe9IhH7Ny8</d:MediaUrl>
     <d:DisplayUrl m:type="Edm.String">http://www.bing.com/videos/search?mkt=en-US&amp;q=&amp;FORM=MONITR&amp;id=1086D91E115D87DD9C451086D91E115D87DD9C45&amp;view=detail</d:DisplayUrl>
     <d:RunTime m:type="Edm.Int32">22964</d:RunTime>
     <d:Thumbnail m:type="Bing.Thumbnail">
     <d:MediaUrl m:type="Edm.String">http://ts4.mm.bing.net/videos/thumbnail.aspx?q=4519989896871951&amp;id=debd7aedef740238c0ebb7d7de218364&amp;bid=RZzdh10RHtmGEA&amp;bn=Thumb&amp;url=http%3a%2f%2fwww.youtube.com%2fwatch%3fv%3dqhe9IhH7Ny8</d:MediaUrl>
     <d:ContentType m:type="Edm.String">image/jpg</d:ContentType>
     <d:Width m:type="Edm.Int32">160</d:Width>
     <d:Height m:type="Edm.Int32">90</d:Height>
     <d:FileSize m:type="Edm.Int64">4823</d:FileSize>
     </d:Thumbnail>
     */
    private String _mediaUrl;
    private String _displayUrl;
    private Integer _runTime;
    private BingThumbnail _thumbnail = new BingThumbnail();

    /**
     * @return the mediaUrl
     */
    public String getMediaUrl() {
        return _mediaUrl;
    }

    /**
     * @param mediaUrl the mediaUrl to set
     */
    public void setMediaUrl(String mediaUrl) {
        _mediaUrl = mediaUrl;
    }

    /**
     * @return the displayUrl
     */
    public String getDisplayUrl() {
        return _displayUrl;
    }

    /**
     * @param displayUrl the displayUrl to set
     */
    public void setDisplayUrl(String displayUrl) {
        _displayUrl = displayUrl;
    }

    /**
     *
     * @return
     */
    public Integer getRunTime() {
        return _runTime;
    }

    /**
     *
     * @param runTime
     */
    public void setRunTime(Integer runTime) {
        _runTime = runTime;
    }

    /**
     *
     * @return
     */
    public BingThumbnail getThumbnail() {
        return _thumbnail;
    }

    /**
     *
     * @param thumbnail
     */
    public void setThumbnail(BingThumbnail thumbnail) {
        _thumbnail = thumbnail;
    }

}
