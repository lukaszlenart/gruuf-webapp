<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<s:iterator value="bikes" var="bike">
  <div class="row">
    <div class="col-md-12">
      <h3><s:property value="name"/></h3>
    </div>
  </div>
  <table class="table table-striped">
    <thead>
    <tr>
      <th><s:text name="bike.vin"/></th>
      <th><s:text name="bike.mileageInKm"/></th>
      <th><s:text name="bike.registrationPlate"/></th>
    </tr>
    </thead>
    <tbody>
    <tr>
      <td><s:property value="vin"/></td>
      <td><s:property value="mileage"/></td>
      <td><s:property value="registrationPlate"/></td>
    </tr>
    </tbody>
  </table>

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
    <s:iterator value="bikeDetails.events" var="event">
      <tr>
        <td><s:property value="eventType.name"/></td>
        <td><s:property value="descriptiveName"/></td>
        <td><s:property value="registerDate"/></td>
        <td><s:property value="mileage"/></td>
        <td><s:property value="timestamp"/></td>
      </tr>
    </s:iterator>
    </tbody>
  </table>

  <p class="center-block">
    <s:url var="url" action="history" namespace="/bike">
      <s:param name="bikeId">${bike.id}</s:param>
    </s:url>
    <s:a class="btn btn-default" role="button" href="%{url}">
      <s:text name="general.viewDetails"/>
    </s:a>
  </p>
</s:iterator>
