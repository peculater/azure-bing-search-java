azure-bing-search-java
======================

Java implementation of Bing Search API hosted in Windows Azure

This is in late beta. Most of it works. Not everything that does work works well. Let me know if you find problems.

Dependent on HTTPCore and HTTPClient out of Apache Commons. For more info, see Using azure-bing-search-java in your java project.

Example use:

```
<%
AzureSearchNewsQuery aq = new AzureSearchNewsQuery();
aq.setAppid(AZURE_APPID);
aq.setQuery("Oklahoma Sooners");
                        
aq.doQuery();
AzureSearchResultSet<AzureSearchNewsResult> ars = aq.getQueryResult();
for (AzureSearchNewsResult anr : ars){
        %>
        <h2><%=anr.getTitle()%> (<%=anr.getSource() %>)</h2>
        <p><%=anr.getDate() %></p>
        <p><%=anr.getDescription() %></p>
        <a href="<%=anr.getUrl() %>"><%=anr.getUrl() %></a>
<%}%>
```
