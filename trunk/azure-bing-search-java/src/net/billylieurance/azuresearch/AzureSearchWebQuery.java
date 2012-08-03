package net.billylieurance.azuresearch;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class AzureSearchWebQuery extends
		AbstractAzureSearchQuery<AzureSearchWebResult> {

	//private static final Logger log = Logger
	//		.getLogger(AzureSearchNewsQuery.class.getName());

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


	

	@Override
	public String getQueryPath() {
		return AZURESEARCH_PATH
				+ querytypeToUrl(AZURESEARCH_QUERYTYPE.WEB);
	}

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
        <d:ID m:type="Edm.Guid">749aa620-464b-462f-974c-adf11985abb8</d:ID>
        <d:Title m:type="Edm.String">SoonerSports.com - Official Athletics Site of the Oklahoma Sooners</d:Title>
        <d:Description m:type="Edm.String">SoonerSports.com is the official athletics site of the Oklahoma Sooners. SoonerSports.com provides official coverage of the Oklahoma Sooners direct from ...</d:Description>
        <d:DisplayUrl m:type="Edm.String">www.soonersports.com</d:DisplayUrl>
        <d:Url m:type="Edm.String">http://www.soonersports.com/</d:Url>
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
								returnable.setDisplayURL(contentKid
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
