<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<nav class="navbar">
  <ul class="nav nav-tabs">
    <li role="presentation">
      <s:url var="show" action="show">
        <s:param name="bikeId" value="%{bikeId}"/>
      </s:url>
      <s:a href="%{show}"><s:text name="bike.bikeHistory"/></s:a>
    </li>
    <li role="presentation" class="active">
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
      <td>
        <s:property value="eventType.name"/>
      </td>
      <td>
        <s:property value="descriptiveName"/>
      </td>
      <td>
        <s:date name="registerDate" format="%{userDateFormat}"/>
      </td>
      <td>
        <s:property value="mileage"/>
      </td>
      <td>
        <s:date name="timestamp" format="%{userDateFormat}"/>
      </td>
      <td>
        <s:url var="deleteEvent" action="delete-bike-event">
          <s:param name="bikeEventId" value="id"/>
        </s:url>
        <s:a value="%{deleteEvent}"><s:text name="general.delete"/></s:a>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>

<s:if test="showRegisterForm">
  <script type="application/javascript">
    $('#new-bike-event-form').collapse('show');
  </script>
</s:if>