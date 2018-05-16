<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<h1>
  <tiles:insertAttribute name="titleName"/>
  <s:url var="editBike" action="bike-form">
    <s:param name="bikeId" value="%{bikeId}"/>
  </s:url>
  <s:a href="%{editBike}" class="media-middle edit-icon" title="%{getText('general.edit')}">
    <span class="glyphicon glyphicon-edit"></span>
  </s:a>
</h1>