<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="c" uri="/struts-tags" %>

<div class="row">
  <h4 class="center-block">
    <s:property value="bikeDetails.bike.producerAndMake"/> - <s:property value="bikeDetails.bike.modelYear"/>
  </h4>
  <h5>
    <s:if test="bikeDetails.bike.showMileage">
      <s:text name="bike.mileageInKm"/>: <s:number name="bikeDetails.mileage"/>
    </s:if>
    <s:if test="bikeDetails.bike.showMth">
      <s:text name="bike.mth"/>: <s:number name="bikeDetails.mth"/>
    </s:if>
  </h5>
</div>

<div class="row">
  <div class="center-block">
    <span><s:text name="report.reportDate"/>: <s:date name="reportDate" format="%{userDateFormat}"/></span>
    <button class="btn btn-success hidden-print pull-right" onclick="window.print(); return false;">
      <s:text name="general.print"/>
    </button>
  </div>
</div>

<div class="row">
  <table class="table table-striped">
    <thead>
    <tr>
      <th><s:text name="bikeEvent.eventType"/></th>
      <th><s:text name="bikeEvent.description"/></th>
      <th><s:text name="bikeEvent.eventDate"/></th>
      <s:if test="selectedBike.showMileage">
        <th><s:text name="bikeEvent.mileage"/></th>
      </s:if>
      <s:if test="selectedBike.showMth">
        <th><s:text name="bikeEvent.mth"/></th>
      </s:if>
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
        <s:if test="selectedBike.showMileage">
          <td class="text-nowrap">
            <s:number name="mileage"/>
          </td>
        </s:if>
        <s:if test="selectedBike.showMth">
          <td class="text-nowrap">
            <s:number name="mth"/>
          </td>
        </s:if>
        <td class="text-nowrap">
          <s:date name="timestamp" format="%{userDateFormat}"/>
        </td>
      </tr>
    </s:iterator>
    </tbody>
  </table>
</div>
  