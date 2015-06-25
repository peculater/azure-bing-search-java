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

public class AzureSearchImageResult extends AbstractAzureSearchResult {

    /*
     * <d:ID m:type="Edm.Guid">0b466975-9091-4c9d-9191-7bc829ffe6ea</d:ID>
     <d:Title m:type="Edm.String">OKLAHOMA SOONERS</d:Title>
     <d:MediaUrl m:type="Edm.String">http://i.cnn.net/si/images/basketball/college/logos/oklahoma_100.gif</d:MediaUrl>
     <d:SourceUrl m:type="Edm.String">http://sportsillustrated.cnn.com/basketball/college/men/rosters/2002/oklahoma/</d:SourceUrl>
     <d:DisplayUrl m:type="Edm.String">sportsillustrated.cnn.com/basketball/college/men/rosters/2002/oklahoma</d:DisplayUrl>
     <d:Width m:type="Edm.Int32">100</d:Width>
     <d:Height m:type="Edm.Int32">100</d:Height>
     <d:FileSize m:type="Edm.Int64">3199</d:FileSize>
     <d:ContentType m:type="Edm.String">image/gif</d:ContentType>
     <d:Thumbnail m:type="Bing.Thumbnail">
     <d:MediaUrl m:type="Edm.String">http://ts1.mm.bing.net/images/thumbnail.aspx?q=5046449779900500&amp;id=35e0371de51b63390dc0cc9a94304e62</d:MediaUrl>
     <d:ContentType m:type="Edm.String">image/jpg</d:ContentType>
     <d:Width m:type="Edm.Int32">100</d:Width>
     <d:Height m:type="Edm.Int32">100</d:Height>
     <d:FileSize m:type="Edm.Int64">2432</d:FileSize>
     </d:Thumbnail>
     </m:properties>
     */
    private String _mediaUrl;
    private String _sourceUrl;
    private String _displayUrl;
    private Integer _width;
    private Integer _height;
    private Long _fileSize;
    private String _contentType;
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
     * @return the sourceUrl
     */
    public String getSourceUrl() {
        return _sourceUrl;
    }

    /**
     * @param sourceUrl the sourceUrl to set
     */
    public void setSourceUrl(String sourceUrl) {
        _sourceUrl = sourceUrl;
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
     * @return the width
     */
    public Integer getWidth() {
        return _width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(Integer width) {
        _width = width;
    }

    /**
     * @return the height
     */
    public Integer getHeight() {
        return _height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(Integer height) {
        _height = height;
    }

    /**
     * @return the fileSize
     */
    public Long getFileSize() {
        return _fileSize;
    }

    /**
     * @param fileSize the fileSize to set
     */
    public void setFileSize(Long fileSize) {
        _fileSize = fileSize;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return _contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        _contentType = contentType;
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
