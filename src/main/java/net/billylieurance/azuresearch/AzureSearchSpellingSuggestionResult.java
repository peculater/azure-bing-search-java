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

/**
 *
 * @author wlieurance
 */

public class AzureSearchSpellingSuggestionResult extends AbstractAzureSearchResult {

    /*
     <d:ID m:type="Edm.Guid">a48d5602-41c4-4b38-8959-14da67c20728</d:ID>
     <d:Value m:type="Edm.String">oklahoma sooners</d:Value>
     */
    private String _value;

    /**
     *
     * @return
     */
    public String getValue() {
        return _value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        _value = value;
    }

}
