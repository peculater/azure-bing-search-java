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

public class AzureSearchCompositeQuery extends
		AbstractAzureSearchQuery<AbstractAzureSearchResult> {

	// private static final Logger log = Logger
	// .getLogger(AzureSearchNewsQuery.class.getName());

	private AZURESEARCH_QUERYTYPE[] _sources;
	
	private String _webSearchOptions = "";
	private String _webFileType = "";
	private String _imageFilters = "";
	private String _videoFilters = "";
	private String _videoSortBy = "";
	private String _locationOverride = "";
	private String _category = "";
	private String _sortBy = "";

	/**
	 * @return the webSearchOptions
	 */
	public String getWebSearchOptions() {
		return _webSearchOptions;
	}

	/**
	 * @param webSearchOptions
	 *            the webSearchOptions to set
	 */
	public void setWebSearchOptions(String webSearchOptions) {
		_webSearchOptions = webSearchOptions;
	}


	/**
	 * @return the webFileType
	 */
	public String getWebFileType() {
		return _webFileType;
	}

	/**
	 * @param webFileType
	 *            the webFileType to set
	 */
	public void setWebFileType(String webFileType) {
		_webFileType = webFileType;
	}
	
	public String getImageFilters() {
		return _imageFilters;
	}

	public void setImageFilters(String imageFilters) {
		_imageFilters = imageFilters;
	}
	
	/**
	 * @return the locationOverride
	 */
	public String getLocationOverride() {
		return _locationOverride;
	}

	/**
	 * @param locationOverride
	 *            the locationOverride to set
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
	 * @param category
	 *            the category to set
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
	 * @param sortBy
	 *            the sortBy to set
	 */
	public void setSortBy(String sortBy) {
		_sortBy = sortBy;
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

	/**
	 * @param bingApi
	 *            the bingApi to set
	 */
	public void setBingApi(AZURESEARCH_API bingApi) {
		_bingApi = bingApi;
	}

	@Override
	public String getQueryPath() {
		return this.getPath() + querytypeToUrl(AZURESEARCH_QUERYTYPE.COMPOSITE);
	}

	@Override
	public AbstractAzureSearchResult parseEntry(Node entry) {
		//AzureSearchWebResult returnable = new AzureSearchWebResult();

		try {
			NodeList l1kids = entry.getChildNodes();

			for (int i = 0; i < l1kids.getLength(); i++) {
				Node l1kid = l1kids.item(i);				
				if (l1kid.getNodeName().equals("title")) {
					String type = l1kid.getTextContent();
					if (type.equals("WebResult")){
						AzureSearchWebQuery aq = new AzureSearchWebQuery();
						return aq.parseEntry(entry);						
					} else if (type.equals("NewsResult")){
						AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
						return aq.parseEntry(entry);						
					} else if (type.equals("ImageResult")){
						AzureSearchImageQuery aq = new AzureSearchImageQuery();
						return aq.parseEntry(entry);						
					} else if (type.equals("VideoResult")){
						AzureSearchVideoQuery aq = new AzureSearchVideoQuery();
						return aq.parseEntry(entry);						
					} else if (type.equals("SpellResult")){
						AzureSearchWebQuery aq = new AzureSearchWebQuery();
						return aq.parseEntry(entry);						
					}					
				}

			}
		} catch (NullPointerException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String getAdditionalUrlQuery() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("&Sources='");
		sb.append(this.getSourcesForUrl());
		sb.append("'");
		

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
			sb.append("&getSortBy='");
			sb.append(this.getLatitude());
			sb.append("'");
		}
		
		if (!this.getImageFilters().equals("")) {
			sb.append("&ImageFilters='");
			sb.append(this.getImageFilters());
			sb.append("'");
		}
		
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

	public AZURESEARCH_QUERYTYPE[] getSources() {
		return _sources;
	}

	public void setSources(AZURESEARCH_QUERYTYPE[] sources) {
		_sources = sources;
	}

	
	protected String getSourcesForUrl(){
		StringBuilder sb = new StringBuilder();
		Boolean started = false;
		for (AZURESEARCH_QUERYTYPE source : this.getSources()){
			switch (source){
			case COMPOSITE:
				break;
			case WEB:
				if (started) sb.append("+");
				sb.append("web");
				started = true;
				break;
			case IMAGE:
				if (started) sb.append("+");
				sb.append("image");
				started = true;
				break;
			case VIDEO:
				if (started) sb.append("+");
				sb.append("video");
				started = true;
				break;
			case NEWS:
				if (started) sb.append("+");
				sb.append("news");
				started = true;
				break;
			case RELATEDSEARCH:
				break;
			case SPELLINGSUGGESTION:
				if (started) sb.append("+");
				sb.append("spell");
				started = true;
				break;
			default:
				break;		
			}			
		}
		
		return sb.toString();
	
	}
}
