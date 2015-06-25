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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author wlieurance
 */
public class AzureSearchVideoQuery extends
        AbstractAzureSearchQuery<AzureSearchVideoResult> {

	// private static final Logger log = Logger
    // .getLogger(AzureSearchNewsQuery.class.getName());
    private String _videoFilters = "";
    private String _videoSortBy = "";

    /**
     *
     * @return
     */
    @Override
    public String getQueryPath() {
        return this.getPath() + querytypeToUrl(AZURESEARCH_QUERYTYPE.VIDEO);
    }

    /**
     *
     * @param entry
     * @return
     */
    @Override
    public AzureSearchVideoResult parseEntry(Node entry) {
        AzureSearchVideoResult returnable = new AzureSearchVideoResult();

        try {
            NodeList l1kids = entry.getChildNodes();

            for (int i = 0; i < l1kids.getLength(); i++) {
                Node l1kid = l1kids.item(i);
                if (l1kid.getNodeName().equals("content")) {
					// parse <content>
					/*
                     <d:ID m:type="Edm.Guid">d5703b09-0b21-4991-acc9-68913946c673</d:ID>
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
                    NodeList contentKids = l1kid.getFirstChild()
                            .getChildNodes();

                    for (int j = 0; j < contentKids.getLength(); j++) {
                        Node contentKid = contentKids.item(j);

                        try {
                            if (contentKid.getNodeName().equals("d:ID")) {
                                returnable.setId(contentKid.getTextContent());
                            } else if (contentKid.getNodeName().equals(
                                    "d:Title")) {
                                returnable
                                        .setTitle(contentKid.getTextContent());
                            } else if (contentKid.getNodeName().equals(
                                    "d:MediaUrl")) {
                                returnable.setMediaUrl(contentKid
                                        .getTextContent());
                            } else if (contentKid.getNodeName().equals(
                                    "d:DisplayUrl")) {
                                returnable.setDisplayUrl(contentKid
                                        .getTextContent());
                            } else if (contentKid.getNodeName().equals(
                                    "d:RunTime")) {
                                returnable.setRunTime(Integer.parseInt(contentKid
                                        .getTextContent()));
                            } else if (contentKid.getNodeName().equals(
                                    "d:Thumbnail")) {
                                //Do the thumbnail thing
                                NodeList contentGrandkids = contentKid.getChildNodes();
                                for (int k = 0; k < contentGrandkids.getLength(); k++) {
                                    Node contentGrandKid = contentGrandkids.item(k);

                                    try {
                                        if (contentGrandKid.getNodeName().equals(
                                                "d:MediaUrl")) {
                                            returnable.getThumbnail().setMediaUrl(contentGrandKid
                                                    .getTextContent());
                                        } else if (contentGrandKid.getNodeName().equals(
                                                "d:Width")) {
                                            returnable.getThumbnail().setWidth(Integer.parseInt(contentGrandKid
                                                    .getTextContent()));
                                        } else if (contentGrandKid.getNodeName().equals(
                                                "d:Height")) {
                                            returnable.getThumbnail().setHeight(Integer
                                                    .parseInt(contentGrandKid.getTextContent()));
                                        } else if (contentGrandKid.getNodeName().equals(
                                                "d:FileSize")) {
                                            returnable.getThumbnail()
                                                    .setFileSize(Long.parseLong(contentGrandKid
                                                                    .getTextContent()));
                                        } else if (contentGrandKid.getNodeName().equals(
                                                "d:ContentType")) {
                                            returnable.getThumbnail().setContentType(contentGrandKid
                                                    .getTextContent());
                                        }

                                    } catch (Exception ex) {
                                        // no one cares
                                        ex.printStackTrace();
                                    }
                                } //k

                            }
                        } catch (Exception ex) {
                            // no one cares
                            ex.printStackTrace();
                        }
                    } //j
                }

            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

        return returnable;
    }

    //https://api.datamarket.azure.com/Data.ashx/Bing/Search/v1/Video?Query=%27Oklahoma%20Sooners%27&VideoFilters=%27Duration%3aShort%2bResolution%3aHigh%27&VideoSortBy=%27Date%27&$top=50&$format=Atom

    /**
     *
     * @return
     */
        @Override
    public String getAdditionalUrlQuery() {
        StringBuilder sb = new StringBuilder(6);

        if (!this.getVideoFilters().equals("")) {
            sb.append("&VideoFilters='");
            sb.append(this.getVideoFilters());
            sb.append("'");
        }

        if (!this.getVideoSortBy().equals("")) {
            sb.append("&VideoSortBy='");
            sb.append(this.getVideoSortBy());
            sb.append("'");
        }

        return sb.toString();
    }

    /**
     * @return the videoFilters
     */
    public String getVideoFilters() {
        return _videoFilters;
    }

    /**
     * @param videoFilters the videoFilters to set
     */
    public void setVideoFilters(String videoFilters) {
        _videoFilters = videoFilters;
    }

    /**
     * @return the videoSortBy
     */
    public String getVideoSortBy() {
        return _videoSortBy;
    }

    /**
     * @param videoSortBy the videoSortBy to set
     */
    public void setVideoSortBy(String videoSortBy) {
        _videoSortBy = videoSortBy;
    }

}
