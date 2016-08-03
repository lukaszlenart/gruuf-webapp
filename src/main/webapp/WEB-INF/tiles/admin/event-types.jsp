<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
  <s:url var="addEvent" action="add-event-type"/>
  <s:a href="%{addEvent}">Add event type</s:a>
</div>

<s:iterator value="list" var="eventType">
  <div class="row">
    <s:property value="name"/>
    <s:property value="created"/>
  </div>
</s:iterator>
