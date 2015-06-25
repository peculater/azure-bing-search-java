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

/**
 *
 * @author wlieurance
 * @param <T>
 */
public class AzureSearchResultSet<T> implements Iterable<T> {

    private ArrayList<T> _asrs = new ArrayList<T>();

    /*
     *  <m:properties>
     <d:ID m:type="Edm.Guid">2c486003-50f4-4494-add1-1453a34a36a7</d:ID>
     <d:WebTotal m:type="Edm.Int64">900000</d:WebTotal>
     <d:WebOffset m:type="Edm.Int64">0</d:WebOffset>
     <d:ImageTotal m:type="Edm.Int64">113000</d:ImageTotal>
     <d:ImageOffset m:type="Edm.Int64">0</d:ImageOffset>
     <d:VideoTotal m:type="Edm.Int64">10600</d:VideoTotal>
     <d:VideoOffset m:type="Edm.Int64">0</d:VideoOffset>
     <d:NewsTotal m:type="Edm.Int64">4370</d:NewsTotal>
     <d:NewsOffset m:type="Edm.Int64">0</d:NewsOffset>
     <d:SpellingSuggestionsTotal m:type="Edm.Int64" m:null="true"></d:SpellingSuggestionsTotal>
     <d:AlteredQuery m:type="Edm.String"></d:AlteredQuery>
     <d:AlterationOverrideQuery m:type="Edm.String"></d:AlterationOverrideQuery>
        
     */
    private Long _webTotal;
    private Long _webOffset;
    private Long _imageTotal;
    private Long _imageOffset;
    private Long _videoTotal;
    private Long _videoOffset;
    private Long _newsTotal;
    private Long _newsOffset;
    private Long _spellingSuggestionsTotal;
    private String _alteredQuery;
    private String _alterationOverrideQuery;

    /**
     * @return the asrs
     */
    public ArrayList<T> getAsrs() {
        return _asrs;
    }

    /**
     * @param asrs the asrs to set
     */
    public void setAsrs(ArrayList<T> asrs) {
        _asrs = asrs;
    }

    /**
     * @return the webTotal
     */
    public Long getWebTotal() {
        return _webTotal;
    }

    /**
     * @param webTotal the webTotal to set
     */
    public void setWebTotal(Long webTotal) {
        _webTotal = webTotal;
    }

    /**
     * @return the webOffset
     */
    public Long getWebOffset() {
        return _webOffset;
    }

    /**
     * @param webOffset the webOffset to set
     */
    public void setWebOffset(Long webOffset) {
        _webOffset = webOffset;
    }

    /**
     * @return the imageTotal
     */
    public Long getImageTotal() {
        return _imageTotal;
    }

    /**
     * @param imageTotal the imageTotal to set
     */
    public void setImageTotal(Long imageTotal) {
        _imageTotal = imageTotal;
    }

    /**
     * @return the imageOffset
     */
    public Long getImageOffset() {
        return _imageOffset;
    }

    /**
     * @param imageOffset the imageOffset to set
     */
    public void setImageOffset(Long imageOffset) {
        _imageOffset = imageOffset;
    }

    /**
     * @return the videoTotal
     */
    public Long getVideoTotal() {
        return _videoTotal;
    }

    /**
     * @param videoTotal the videoTotal to set
     */
    public void setVideoTotal(Long videoTotal) {
        _videoTotal = videoTotal;
    }

    /**
     * @return the videoOffset
     */
    public Long getVideoOffset() {
        return _videoOffset;
    }

    /**
     * @param videoOffset the videoOffset to set
     */
    public void setVideoOffset(Long videoOffset) {
        _videoOffset = videoOffset;
    }

    /**
     * @return the newsTotal
     */
    public Long getNewsTotal() {
        return _newsTotal;
    }

    /**
     * @param newsTotal the newsTotal to set
     */
    public void setNewsTotal(Long newsTotal) {
        _newsTotal = newsTotal;
    }

    /**
     * @return the newsOffset
     */
    public Long getNewsOffset() {
        return _newsOffset;
    }

    /**
     * @param newsOffset the newsOffset to set
     */
    public void setNewsOffset(Long newsOffset) {
        _newsOffset = newsOffset;
    }

    /**
     * @return the spellingSuggestionsTotal
     */
    public Long getSpellingSuggestionsTotal() {
        return _spellingSuggestionsTotal;
    }

    /**
     * @param spellingSuggestionsTotal the spellingSuggestionsTotal to set
     */
    public void setSpellingSuggestionsTotal(Long spellingSuggestionsTotal) {
        _spellingSuggestionsTotal = spellingSuggestionsTotal;
    }

    /**
     * @return the alteredQuery
     */
    public String getAlteredQuery() {
        return _alteredQuery;
    }

    /**
     * @param alteredQuery the alteredQuery to set
     */
    public void setAlteredQuery(String alteredQuery) {
        _alteredQuery = alteredQuery;
    }

    /**
     * @return the alterationOverrideQuery
     */
    public String getAlterationOverrideQuery() {
        return _alterationOverrideQuery;
    }

    /**
     * @param alterationOverrideQuery the alterationOverrideQuery to set
     */
    public void setAlterationOverrideQuery(String alterationOverrideQuery) {
        _alterationOverrideQuery = alterationOverrideQuery;
    }

    /**
     *
     */
    public AzureSearchResultSet() {
        super();
    }

    /**
     *
     * @param result
     */
    public void addResult(T result) {
        _asrs.add(result);
    }

    /**
     *
     * @param _asrs
     */
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
        // lets us use for/in
        return _asrs.iterator();
    }

}
