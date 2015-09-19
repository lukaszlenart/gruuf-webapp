<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
  home
</div>

<s:iterator value="motorbikes" var="motorbike" >
  <div class="row">
  <s:url var="url" action="show" namespace="/bike">
    <s:param name="motorbike">${motorbike.id}</s:param>
  </s:url>
  <s:a href="%{url}">${motorbike.name}</s:a>
  </div>
</s:iterator>
