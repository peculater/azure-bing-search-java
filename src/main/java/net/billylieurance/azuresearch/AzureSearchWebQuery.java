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
public class AzureSearchWebQuery extends
        AbstractAzureSearchQuery<AzureSearchWebResult> {

	// private static final Logger log = Logger
    // .getLogger(AzureSearchNewsQuery.class.getName());
    private String _webSearchOptions = "";

    /**
     * @return the webSearchOptions
     */
    public String getWebSearchOptions() {
        return _webSearchOptions;
    }

    /**
     * @param webSearchOptions the webSearchOptions to set
     */
    public void setWebSearchOptions(String webSearchOptions) {
        _webSearchOptions = webSearchOptions;
    }

    private String _webFileType = "";

    /**
     * @return the webFileType
     */
    public String getWebFileType() {
        return _webFileType;
    }

    /**
     * @param webFileType the webFileType to set
     */
    public void setWebFileType(String webFileType) {
        _webFileType = webFileType;
    }

    /**
     * @param bingApi the bingApi to set
     */
    public void setBingApi(AZURESEARCH_API bingApi) {
        _bingApi = bingApi;
    }

    /**
     * @return the current instance of the Bing API we'll be calling
     */
    public AZURESEARCH_API getBingApi() {
        return _bingApi;
    }

    /**
     *
     * @return
     */
    @Override
    public String getQueryPath() {
        return this.getPath() + querytypeToUrl(AZURESEARCH_QUERYTYPE.WEB);
    }

    /**
     *
     * @param entry
     * @return
     */
    @Override
    public AzureSearchWebResult parseEntry(Node entry) {
        AzureSearchWebResult returnable = new AzureSearchWebResult();

        try {
            NodeList l1kids = entry.getChildNodes();

            for (int i = 0; i < l1kids.getLength(); i++) {
                Node l1kid = l1kids.item(i);
                if (l1kid.getNodeName().equals("content")) {
					// parse <content>
					/*
                     * <d:ID
                     * m:type="Edm.Guid">749aa620-464b-462f-974c-adf11985abb8
                     * </d:ID> <d:Title m:type="Edm.String">SoonerSports.com -
                     * Official Athletics Site of the Oklahoma Sooners</d:Title>
                     * <d:Description m:type="Edm.String">SoonerSports.com is
                     * the official athletics site of the Oklahoma Sooners.
                     * SoonerSports.com provides official coverage of the
                     * Oklahoma Sooners direct from ...</d:Description>
                     * <d:DisplayUrl
                     * m:type="Edm.String">www.soonersports.com</d:DisplayUrl>
                     * <d:Url
                     * m:type="Edm.String">http://www.soonersports.com/</d:Url>
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
                            } else if (contentKid.getNodeName().equals("d:Url")) {
                                returnable.setUrl(contentKid.getTextContent());
                            } else if (contentKid.getNodeName().equals(
                                    "d:DisplayUrl")) {
                                returnable.setDisplayUrl(contentKid
                                        .getTextContent());
                            } else if (contentKid.getNodeName().equals(
                                    "d:Description")) {
                                returnable.setDescription(contentKid
                                        .getTextContent());
                            }
                        } catch (Exception ex) {
                            // no one cares
                            ex.printStackTrace();
                        }
                    }
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

        if (!this.getWebSearchOptions().equals("")) {
            sb.append("&WebSearchOptions='");
            sb.append(this.getWebSearchOptions());
            sb.append("'");
        }

        if (!this.getWebFileType().equals("")) {
            sb.append("&WebFileType='");
            sb.append(this.getWebFileType());
            sb.append("'");
        }

        return sb.toString();
    }

}
