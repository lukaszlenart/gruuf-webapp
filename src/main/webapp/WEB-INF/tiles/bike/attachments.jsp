<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<nav class="navbar">
  <ul class="nav nav-tabs">
    <li role="presentation">
      <s:url var="history" action="history">
        <s:param name="bikeId" value="%{bikeId}"/>
      </s:url>
      <s:a href="%{history}"><s:text name="bike.bikeHistory"/></s:a>
    </li>
    <li role="presentation">
      <s:url var="recommendations" action="recommendations">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{recommendations}">
        <s:text name="bike.recommendations"/>
      </s:a>
    </li>
    <li role="presentation">
      <s:url var="event" action="new-bike-event">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{event}">
        <s:text name="bike.registerNewEvent"/>
      </s:a>
    </li>
    <li role="presentation" class="active">
      <s:url var="attachments" action="attachments">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{attachments}">
        <s:text name="bike.attachments"/>
      </s:a>
    </li>
  </ul>
</nav>

<s:form action="upload" enctype="multipart/form-data" method="POST">
  <s:file name="attachment"/>
  <s:submit/>
</s:form>

<table class="table table-striped">
  <thead>
  <tr>
    <th><s:text name="bikeEvent.fileName"/></th>
    <th><s:text name="bikeEvent.contentType"/></th>
    <th><s:text name="bikeEvent.link"/></th>
    <th><s:text name="general.timestamp"/>
    <th><s:text name="general.actions"/>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="bikeDetails.events" var="event">
    <tr>
      <td class="text-nowrap">
        <s:property value="fileName"/>
      </td>
      <td>
        <s:property value="contentType"/>
      </td>
      <td class="text-nowrap">
        <s:a value="%{link}">
          <s:property value="fileName"/>
        </s:a>
      </td>
      <td class="text-nowrap">
        <s:date name="timestamp" format="%{userDateFormat}"/>
      </td>
      <td class="text-nowrap">
        delete
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>
