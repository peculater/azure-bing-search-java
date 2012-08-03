package net.billylieurance.azuresearch;

import java.util.ArrayList;
import java.util.Iterator;

public class AzureSearchResultSet<T> implements Iterable<T> {

	private ArrayList<T> _asrs = new ArrayList<T>();
	
	
	public AzureSearchResultSet() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void addResult(T result){
		_asrs.add(result);
	}


	public AzureSearchResultSet(ArrayList<T> _asrs) {
		super();
		this._asrs = _asrs;
	}




	/**
	 * @return the _asrs
	 */
	public ArrayList<T> getASRs() {
		return _asrs;
	}




	/**
	 * @param _asrs the _asrs to set
	 */
	public void setASRs(ArrayList<T> _asrs) {
		this._asrs = _asrs;
	}




	public Iterator<T> iterator() {
		//lets us use for/in
		return _asrs.iterator();
	}

}
