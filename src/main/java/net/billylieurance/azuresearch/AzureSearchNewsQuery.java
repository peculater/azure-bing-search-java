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
public class AzureSearchNewsQuery extends
        AbstractAzureSearchQuery<AzureSearchNewsResult> {

	// private static final Logger log = Logger
    // .getLogger(AzureSearchNewsQuery.class.getName());
    private String _locationOverride = "";
    private String _category = "";
    private String _sortBy = "";

    /**
     * @return the locationOverride
     */
    public String getLocationOverride() {
        return _locationOverride;
    }

    /**
     * @param locationOverride the locationOverride to set
     */
    public void setLocationOverride(String locationOverride) {
        _locationOverride = locationOverride;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return _category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        _category = category;
    }

    /**
     * @return the sortBy
     */
    public String getSortBy() {
        return _sortBy;
    }

    /**
     * @param sortBy the sortBy to set
     */
    public void setSortBy(String sortBy) {
        _sortBy = sortBy;
    }

    /**
     *
     * @return
     */
    @Override
    public String getQueryPath() {
        return this.getPath() + querytypeToUrl(AZURESEARCH_QUERYTYPE.NEWS);
    }

    /**
     *
     * @param entry
     * @return
     */
    @Override
    public AzureSearchNewsResult parseEntry(Node entry) {
        AzureSearchNewsResult returnable = new AzureSearchNewsResult();

        try {
            NodeList l1kids = entry.getChildNodes();

            for (int i = 0; i < l1kids.getLength(); i++) {
                Node l1kid = l1kids.item(i);
                if (l1kid.getNodeName().equals("content")) {
					// parse <content>
					/*
                     * <d:ID
                     * m:type="Edm.Guid">51da90fe-79d9-450b-b04d-471b766e6fc1
                     * </d:ID> <d:Title m:type="Edm.String">Amicus Therapeutics:
                     * Rare Disease Drug Developer Expected To Reveal Phase 3
                     * Results In 3Q12</d:Title> <d:Url
                     * m:type="Edm.String">http:
                     * //seekingalpha.com/article/753941
                     * -amicus-therapeutics-rare
                     * -disease-drug-developer-expected-
                     * to-reveal-phase-3-results-in-3q12 </d:Url> <d:Source
                     * m:type="Edm.String">Seekingalpha.com</d:Source>
                     * <d:Description m:type="Edm.String">Amicus Therapeutics
                     * (FOLD) is focused on the development of new treatments
                     * for rare metabolic disorders, including lead product
                     * candidate AMIGAL (migalastat), which is summarized below.
                     * Pivotal Phase 3 results are expected this quarter as part
                     * of a ...</d:Description> <d:Date
                     * m:type="Edm.DateTime">2012-07-26T20:26:55Z</d:Date>
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
                                    "d:Source")) {
                                returnable.setSource(contentKid
                                        .getTextContent());
                            } else if (contentKid.getNodeName().equals(
                                    "d:Description")) {
                                returnable.setDescription(contentKid
                                        .getTextContent());
                            } else if (contentKid.getNodeName()
                                    .equals("d:Date")) {
                                returnable.setDate(contentKid.getTextContent());
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
        StringBuilder sb = new StringBuilder(9);

        if (!this.getLocationOverride().equals("")) {
            sb.append("&NewsLocationOverride='");
            sb.append(this.getLocationOverride());
            sb.append("'");
        }

        if (!this.getCategory().equals("")) {
            sb.append("&NewsCategory='");
            sb.append(this.getCategory());
            sb.append("'");
        }

        if (!this.getSortBy().equals("")) {
            sb.append("&NewsSortBy='");
            sb.append(this.getSortBy());
            sb.append("'");
        }

        return sb.toString();
    }

}
