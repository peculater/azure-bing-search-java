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

public class BingThumbnail {

    private String _mediaUrl;
    private String _contentType;
    private Integer _width;
    private Integer _height;
    private Long _fileSize;

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

}
