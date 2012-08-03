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

import java.util.ArrayList;
import java.util.Iterator;

public class AzureSearchResultSet<T> implements Iterable<T> {

	private ArrayList<T> _asrs = new ArrayList<T>();

	public AzureSearchResultSet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void addResult(T result) {
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
	 * @param _asrs
	 *            the _asrs to set
	 */
	public void setASRs(ArrayList<T> _asrs) {
		this._asrs = _asrs;
	}

	public Iterator<T> iterator() {
		// lets us use for/in
		return _asrs.iterator();
	}

}
