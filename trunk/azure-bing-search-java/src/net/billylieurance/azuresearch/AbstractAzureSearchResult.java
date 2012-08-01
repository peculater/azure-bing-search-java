/**
 * 
 */
package net.billylieurance.azuresearch;

/**
 * @author billy
 *
 */
public abstract class AbstractAzureSearchResult {

	


	public String getId() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	public String getTitle() {
		return _title;
	}
	public void setTitle(String _title) {
		this._title = _title;
	}
	public String getUrl() {
		return _url;
	}
	public void setUrl(String _url) {
		this._url = _url;
	}

	public String getDescription() {
		return _description;
	}
	public void setDescription(String _description) {
		this._description = _description;
	}

	
	protected String _id;
	protected String _title;
	protected String _url;
	protected String _description;

	
	
}
