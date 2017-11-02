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
    <th><s:text name="bikeEvent.description"/></th>
    <th><s:text name="bikeEvent.eventDate"/></th>
    <th>
      <s:text name="bikeEvent.mileage"/>
      (<s:text name="general.current"/>)
    </th>
    <th>
      <s:text name="bikeEvent.mth"/>
      (<s:text name="general.current"/>)
    </th>
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
        <s:property value="description" escapeHtml="false"/>
      </td>
      <td class="text-nowrap">
        <s:date name="registerDate" format="%{userDateFormat}"/>
      </td>
      <td>
        <s:number name="mileage"/>
        <s:if test="isCurrentMileage()">
        <span class="badge" title="<s:text name='bikeEvent.currentMileage.title'/>">
          <s:number name="getCurrentMileage()"/>
          <i class="fa fa-repeat"></i>
        </span>
        </s:if>
      </td>
      <td>
        <s:number name="mth"/>
        <s:if test="isCurrentMth()">
        <span class="badge" title="<s:text name='bikeEvent.currentMth.title'/>">
          <s:number name="getCurrentMth()"/>
          <i class="fa fa-repeat"></i>
        </span>
        </s:if>
      </td>
      <td class="text-nowrap">
        <s:date name="timestamp" format="%{userDateFormat}"/>
      </td>
      <td class="text-nowrap">
        <s:if test="editable">
          <s:url var="editEvent" action="edit-bike-event" escapeAmp="false">
            <s:param name="bikeId" value="%{bikeId}"/>
            <s:param name="bikeEventId" value="id"/>
          </s:url>
          <s:a value="%{editEvent}" cssClass="editable glyphicon glyphicon-edit" title="%{getText('general.edit')}"><s:text name="general.edit"/></s:a>
        </s:if>

        <s:if test="deletable">
        <s:url var="deleteEvent" action="delete-bike-event" escapeAmp="false">
          <s:param name="bikeId" value="%{bikeId}"/>
          <s:param name="bikeEventId" value="id"/>
        </s:url>
        <s:a value="%{deleteEvent}" cssClass="deletable glyphicon glyphicon-trash" title="%{getText('general.delete')}"><s:text name="general.delete"/></s:a>
        </s:if>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>
