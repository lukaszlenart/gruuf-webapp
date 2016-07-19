<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<s:iterator value="list" var="user">
  <div class="row">
    <s:property value="email"/>
    <s:property value="tokens"/>
  </div>
</s:iterator>
