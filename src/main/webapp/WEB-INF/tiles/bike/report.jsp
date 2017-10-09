<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="c" uri="/struts-tags" %>

<div class="row">
  <p class="center-block">
    <button class="btn btn-success hidden-print" onclick="window.print(); return false;"><s:text name="general.print"/></button>
    <span><s:text name="report.reportDate"/>: <s:date name="reportDate" format="%{userDateFormat}"/></span>
  </p>

  <table class="table table-striped">
    <thead>
    <tr>
      <th><s:text name="bikeEvent.eventType"/></th>
      <th><s:text name="bikeEvent.description"/></th>
      <th><s:text name="bikeEvent.eventDate"/></th>
      <th><s:text name="bikeEvent.mileage"/></th>
      <th><s:text name="bikeEvent.mth"/></th>
      <th><s:text name="general.timestamp"/>
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
        <td class="text-nowrap">
          <s:number name="mileage"/>
        </td>
        <td class="text-nowrap">
          <s:number name="mth"/>
        </td>
        <td class="text-nowrap">
          <s:date name="timestamp" format="%{userDateFormat}"/>
        </td>
      </tr>
    </s:iterator>
    </tbody>
  </table>
</div>
  