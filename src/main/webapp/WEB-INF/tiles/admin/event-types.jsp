<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
  <div class="col-md-12">
    <s:url var="addEvent" action="add-event-type"/>
    <s:a href="%{addEvent}">Add event type</s:a>
  </div>
</div>

<table class="table table-striped">
  <thead class="header-inverse">
  <tr>
    <th>Name</th>
    <th>Status</th>
    <th>Created</th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="list" var="eventType">
    <tr>
      <td><s:property value="name"/></td>
      <td><s:property value="status"/></td>
      <td><s:property value="created"/></td>
    </tr>
  </s:iterator>
  </tbody>
</table>

