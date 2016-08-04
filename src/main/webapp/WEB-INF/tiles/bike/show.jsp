<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <h2>
    <s:property value="bikeDetails.bike.name"/>
  </h2>
</div>
<div class="row">
  <div class="col-md-6">
    <h3>
      <s:text name="bike.registerNewEvent"/>
      <button class="glyphicon glyphicon-chevron-down btn btn-link collapsed" aria-hidden="true" type="button"
              data-toggle="collapse" data-target="#new-bike-event-form"
              aria-expanded="false" aria-controls="new-bike-event-form" title="<s:text name="bike.expand"/>">
      </button>
    </h3>
    <s:form action="register-bike-event" id="new-bike-event-form" method="POST" cssClass="collapse form-horizontal">
      <s:hidden name="bikeId"/>

      <s:select list="eventTypesList"
                name="eventTypeId"
                listValueKey="name"
                listKey="id"
                key="bike.eventType"
                labelCssClass="col-md-4"
                elementCssClass="col-md-6"
                cssClass="input-md"/>

      <s:textarea name="descriptiveName"
                  key="bike.descriptiveName"
                  placeholder="e.g. bought a new bike"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-6"
                  cssClass="input-md"/>

      <s:textfield name="registerDate"
                   type="date"
                   key="bike.date"
                   placeholder="e.g. 12/Aug/2016"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-6"
                   cssClass="input-md"/>

      <s:textfield name="mileage"
                   type="number"
                   key="bike.mileageInKm"
                   placeholder="e.g. 12 000"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-6"
                   cssClass="input-md"/>

      <div class="form-group">
        <div class="col-sm-offset-8">
          <s:submit cssClass="btn btn-primary" key="general.register" />
        </div>
      </div>

    </s:form>
  </div>
</div>

<s:iterator value="bikeDetails.events" var="event">
  <div class="row">
    <s:property value="#event.descriptiveName"/>
    <s:property value="#event.timestamp"/>
  </div>
</s:iterator>
