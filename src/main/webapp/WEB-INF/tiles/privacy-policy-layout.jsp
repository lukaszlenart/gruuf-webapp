<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<s:if test="loggedIn">
  <tiles:insertDefinition name="privacy-policy.logged-in"/>
</s:if>

<s:if test="not loggedIn">
  <tiles:insertDefinition name="privacy-policy.not-logged-in"/>
</s:if>
