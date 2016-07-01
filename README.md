![Travis CI Build status](https://api.travis-ci.org/peculater/azure-bing-search-java.svg)

*********
*********
As of December 15, 2016, Microsoft will no longer be supporting the Bing Search APIs.  This library will probably not be working after that.
*********
*********


azure-bing-search-java
======================

Java implementation of Bing Search API hosted in Windows Azure

Works in (read: tested against) Oracle JDK 7 and 8, and OpenJDK 6, 7, and 8.

This is in late beta. Most of it works. Not everything that does work works well. Let me know if you find problems.

Dependent on HTTPCore and HTTPClient out of Apache Commons. For more info, see [Using azure-bing-search-java in your java project](https://github.com/peculater/azure-bing-search-java/wiki/IncludingTheJar.wiki).

Example use:

```java
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
