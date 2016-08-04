<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<s:iterator value="bikes" var="bike" >
  <div class="row">
  <s:url var="url" action="show" namespace="/bike">
    <s:param name="bike">${bike.id}</s:param>
  </s:url>
  <s:a href="%{url}">${bike.name}</s:a>
  </div>
</s:iterator>
