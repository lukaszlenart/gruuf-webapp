<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="c" uri="/struts-tags" %>

<nav class="navbar">
  <ul class="nav nav-tabs">
    <li role="presentation" class="active">
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
    <li role="presentation">
      <s:url var="attachments" action="attachments">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{attachments}">
        <s:text name="bike.attachments"/>
      </s:a>
    </li>
  </ul>
</nav>

<table class="table table-striped">
  <thead>
  <tr>
    <th><s:text name="bikeEvent.eventType"/></th>
    <th><s:text name="bikeEvent.descriptiveName"/></th>
    <th><s:text name="bikeEvent.eventDate"/></th>
    <th><s:text name="bikeEvent.mileage"/></th>
    <th><s:text name="general.timestamp"/>
    <th><s:text name="general.actions"/>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="bikeDetails.events" var="event">
    <tr>
      <td class="text-nowrap">
        <s:iterator value="eventTypes" var="eventType" status="stats">
          <s:property value="name"/><s:if test="%{not #stats.last}">,</s:if>
        </s:iterator>
      </td>
      <td>
        <s:property value="descriptiveName" escapeHtml="false"/>
      </td>
      <td class="text-nowrap">
        <s:date name="registerDate" format="%{userDateFormat}"/>
      </td>
      <td class="text-nowrap">
        <s:number name="mileage"/>
      </td>
      <td class="text-nowrap">
        <s:date name="timestamp" format="%{userDateFormat}"/>
      </td>
      <td class="text-nowrap">
        <s:if test="deletable">
        <s:url var="deleteEvent" action="delete-bike-event">
          <s:param name="bikeEventId" value="id"/>
        </s:url>
        <s:a value="%{deleteEvent}"><s:text name="general.delete"/></s:a>
        </s:if>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>
