<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<s:if test="showShortcuts">
  <div class="row">
    <div class="col-md-12">
      <div class="panel panel-default">
        <div class="panel-body">
          <ul class="nav nav-pills">
            <s:iterator value="bikeDetails" var="details">
              <li role="presentation">
                <a href="#<s:property value='bike.id'/>">
                  <s:property value="bike.name"/>
                </a>
              </li>
            </s:iterator>
          </ul>
        </div>
      </div>
    </div>
  </div>
</s:if>

<s:iterator value="bikeDetails" var="details" status="status">
  <div class="row">
    <div class="col-md-12">
      <div class="panel panel-primary">

        <div class="panel-heading">
          <div id="<s:property value='bike.id'/>" class="bike-anchor"></div>
          <h2><s:property value="bike.name"/></h2>
        </div>

        <div class="panel-body">
          <table class="table table-striped">
            <thead>
            <tr>
              <th><s:text name="bike.vin"/></th>
              <th><s:text name="bike.modelYear"/></th>
              <th><s:text name="bike.metadata"/></th>
              <s:if test="bike.showMileage">
                <th><s:text name="bike.mileageInKm"/></th>
              </s:if>
              <s:if test="bike.showMth">
                <th><s:text name="bike.mth"/></th>
              </s:if>
              <th><s:text name="bike.registrationPlate"/></th>
            </tr>
            </thead>
            <tbody>
            <tr>
              <td><s:property value="bike.vin"/></td>
              <td><s:property value="bike.modelYear"/></td>
              <td><s:property value="metadata.name"/></td>
              <s:if test="bike.showMileage">
                <td><s:number name="mileage"/></td>
              </s:if>
              <s:if test="bike.showMth">
                <td><s:number name="mth"/></td>
              </s:if>
              <td><s:property value="registrationPlate"/></td>
            </tr>
            </tbody>
          </table>

          <s:url var="editUrl" action="bike-form" namespace="/bike">
            <s:param name="bikeId" value="bike.id"/>
          </s:url>
          <s:a value="%{editUrl}" class="btn btn-primary">
            <s:text name="general.edit"/>
            <i class="glyphicon glyphicon-edit"></i>
          </s:a>

          <s:url var="deleteUrl" action="bike-delete" namespace="/bike">
            <s:param name="bikeId" value="bike.id"/>
          </s:url>
          <s:a value="%{deleteUrl}" class="btn btn-danger pull-right">
            <s:text name="general.delete"/>
            <i class="glyphicon glyphicon-trash"></i>
          </s:a>
        </div>

        <div class="panel-body">
          <table class="table table-striped">
            <thead>
            <tr>
              <th><s:text name="bikeEvent.eventType"/></th>
              <th><s:text name="bikeEvent.description"/></th>
              <th><s:text name="bikeEvent.eventDate"/></th>
              <s:if test="bike.showMileage">
                <th><s:text name="bikeEvent.mileage"/></th>
              </s:if>
              <s:if test="bike.showMth">
                <th><s:text name="bikeEvent.mth"/></th>
              </s:if>
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
                <td><s:property value="description" escapeHtml="false"/></td>
                <td class="text-nowrap"><s:date name="registerDate" format="%{userDateFormat}"/></td>
                <s:if test="bike.showMileage">
                  <td class="text-nowrap"><s:number name="mileage"/></td>
                </s:if>
                <s:if test="bike.showMth">
                  <td class="text-nowrap"><s:number name="mth"/></td>
                </s:if>
                <td class="text-nowrap"><s:date name="timestamp" format="%{userDateFormat}"/></td>
              </tr>
            </s:iterator>
            </tbody>
          </table>

          <s:url var="historyUrl" action="history" namespace="/bike">
            <s:param name="bikeId" value="bike.id"/>
          </s:url>
          <s:a class="btn btn-primary" role="button" href="%{historyUrl}">
            <s:text name="general.viewDetails"/>
            <i class="glyphicon glyphicon-th-list"></i>
          </s:a>
          <s:url var="reportUrl" action="report" namespace="/bike">
            <s:param name="bikeId" value="bike.id"/>
          </s:url>
          <s:a class="btn btn-info" role="button" href="%{reportUrl}" target="_blank">
            <s:text name="general.fullReport"/>
            <i class="glyphicon glyphicon-print"></i>
          </s:a>
        </div>

      </div>
    </div>
  </div>
</s:iterator>
