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
public class AzureSearchImageQuery extends
        AbstractAzureSearchQuery<AzureSearchImageResult> {

	// private static final Logger log = Logger
    // .getLogger(AzureSearchNewsQuery.class.getName());
    private String _imageFilters = "";

    /**
     *
     * @return
     */
    @Override
    public String getQueryPath() {
        return this.getPath() + querytypeToUrl(AZURESEARCH_QUERYTYPE.IMAGE);
    }

    /**
     *
     * @param entry
     * @return
     */
    @Override
    public AzureSearchImageResult parseEntry(Node entry) {
        AzureSearchImageResult returnable = new AzureSearchImageResult();

        try {
            NodeList l1kids = entry.getChildNodes();

            for (int i = 0; i < l1kids.getLength(); i++) {
                Node l1kid = l1kids.item(i);
                if (l1kid.getNodeName().equals("content")) {
					// parse <content>
					/*
                     * <d:ID
                     * m:type="Edm.Guid">0b466975-9091-4c9d-9191-7bc829ffe6ea</d:ID>
                     * <d:Title m:type="Edm.String">OKLAHOMA SOONERS</d:Title>
                     * <d:MediaUrl
                     * m:type="Edm.String">http://i.cnn.net/si/images
                     * /basketball/college/logos/oklahoma_100.gif</d:MediaUrl>
                     * <d:SourceUrl
                     * m:type="Edm.String">http://sportsillustrated.
                     * cnn.com/basketball
                     * /college/men/rosters/2002/oklahoma/</d:SourceUrl>
                     * <d:DisplayUrl
                     * m:type="Edm.String">sportsillustrated.cnn.com
                     * /basketball/college
                     * /men/rosters/2002/oklahoma</d:DisplayUrl> <d:Width
                     * m:type="Edm.Int32">100</d:Width> <d:Height
                     * m:type="Edm.Int32">100</d:Height> <d:FileSize
                     * m:type="Edm.Int64">3199</d:FileSize> <d:ContentType
                     * m:type="Edm.String">image/gif</d:ContentType>
                     * <d:Thumbnail m:type="Bing.Thumbnail"> <d:MediaUrl
                     * m:type="Edm.String"
                     * >http://ts1.mm.bing.net/images/thumbnail
                     * .aspx?q=5046449779900500
                     * &amp;id=35e0371de51b63390dc0cc9a94304e62</d:MediaUrl>
                     * <d:ContentType
                     * m:type="Edm.String">image/jpg</d:ContentType> <d:Width
                     * m:type="Edm.Int32">100</d:Width> <d:Height
                     * m:type="Edm.Int32">100</d:Height> <d:FileSize
                     * m:type="Edm.Int64">2432</d:FileSize> </d:Thumbnail>
                     * </m:properties>
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
                                    "d:SourceUrl")) {
                                returnable.setSourceUrl(contentKid
                                        .getTextContent());
                            } else if (contentKid.getNodeName().equals(
                                    "d:DisplayUrl")) {
                                returnable.setDisplayUrl(contentKid
                                        .getTextContent());
                            } else if (contentKid.getNodeName().equals(
                                    "d:Width")) {
                                returnable.setWidth(Integer.parseInt(contentKid
                                        .getTextContent()));
                            } else if (contentKid.getNodeName().equals(
                                    "d:Height")) {
                                returnable.setHeight(Integer
                                        .parseInt(contentKid.getTextContent()));
                            } else if (contentKid.getNodeName().equals(
                                    "d:FileSize")) {
                                returnable
                                        .setFileSize(Long.parseLong(contentKid
                                                        .getTextContent()));
                            } else if (contentKid.getNodeName().equals(
                                    "d:ContentType")) {
                                returnable.setContentType(contentKid
                                        .getTextContent());
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

    /**
     *
     * @return
     */
    @Override
    public String getAdditionalUrlQuery() {
        StringBuilder sb = new StringBuilder(6);

        if (!this.getImageFilters().equals("")) {
            sb.append("&ImageFilters='");
            sb.append(this.getImageFilters());
            sb.append("'");
        }

        return sb.toString();
    }

    /**
     *
     * @return
     */
    public String getImageFilters() {
        return _imageFilters;
    }

    /**
     *
     * @param imageFilters
     */
    public void setImageFilters(String imageFilters) {
        _imageFilters = imageFilters;
    }

}
