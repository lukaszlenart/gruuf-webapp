<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
  <div class="col-md-12">
    <s:url var="addEvent" action="event-type-form"/>
    <s:a href="%{addEvent}" class="btn btn-default">Add event type</s:a>
  </div>
</div>

<table class="table table-striped">
  <thead class="header-inverse">
  <tr>
    <th>Name</th>
    <th>Status</th>
    <th>Created</th>
    <th>Requested by</th>
    <th>Approved</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="list" var="eventType">
    <tr>
      <td><s:property value="name"/></td>
      <td><s:property value="status"/></td>
      <td><s:date name="created" format="%{userDateFormat}"/></td>
      <td><s:property value="requesterFullName"/></td>
      <td><s:property value="approved"/></td>
      <td>
        <s:url var="editEvent" action="event-type-form">
          <s:param name="eventTypeId" value="id"/>
        </s:url>
        <s:a value="%{editEvent}">Edit</s:a>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>

