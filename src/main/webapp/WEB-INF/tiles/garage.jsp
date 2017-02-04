<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<s:iterator value="bikeDetails" var="details">
  <div class="row">
    <div class="col-md-12">
      <h3><s:property value="bike.name"/></h3>
    </div>
  </div>
  <table class="table table-striped">
    <thead>
    <tr>
      <th><s:text name="bike.vin"/></th>
      <th><s:text name="bike.modelYear"/></th>
      <th><s:text name="bike.metadata"/></th>
      <th><s:text name="bike.mileageInKm"/></th>
      <th><s:text name="bike.registrationPlate"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td><s:property value="bike.vin"/></td>
      <td><s:property value="bike.modelYear"/></td>
      <td><s:property value="metadata.name"/></td>
      <td><s:property value="mileage"/></td>
      <td><s:property value="registrationPlate"/></td>
    </tr>
    </tbody>
  </table>

  <p class="center-block">
    <s:url var="edit" action="bike-form" namespace="/bike">
      <s:param name="bikeId" value="bike.id"/>
    </s:url>
    <s:a value="%{edit}" class="btn btn-default"><s:text name="general.edit"/></s:a>
  </p>

  <table class="table table-striped">
    <thead>
    <tr>
      <th><s:text name="bikeEvent.eventType"/></th>
      <th><s:text name="bikeEvent.descriptiveName"/></th>
      <th><s:text name="bikeEvent.eventDate"/></th>
      <th><s:text name="bikeEvent.mileage"/></th>
      <th><s:text name="general.timestamp"/>
    </tr>
    </thead>
    <tbody>
    <s:iterator value="events" var="event">
      <tr>
        <td class="text-nowrap">
          <s:iterator value="eventTypes" var="eventType" status="stats">
            <s:property value="name"/><s:if test="%{not #stats.last}">,<br/></s:if>
          </s:iterator>
        </td>
        <td><s:property value="descriptiveName" escapeHtml="false"/></td>
        <td class="text-nowrap"><s:date name="registerDate" format="%{userDateFormat}"/></td>
        <td class="text-nowrap"><s:property value="mileage"/></td>
        <td class="text-nowrap"><s:date name="timestamp" format="%{userDateFormat}"/></td>
      </tr>
    </s:iterator>
    </tbody>
  </table>

  <p class="center-block">
    <s:url var="url" action="history" namespace="/bike">
      <s:param name="bikeId" value="bike.id"/>
    </s:url>
    <s:a class="btn btn-default" role="button" href="%{url}">
      <s:text name="general.viewDetails"/>
    </s:a>
  </p>
</s:iterator>
