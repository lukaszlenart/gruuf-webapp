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
    <li role="presentation" class="active">
      <s:url var="event" action="new-bike-event">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{event}">
        <s:text name="bike.registerNewEvent"/>
      </s:a>
    </li>
  </ul>
</nav>

<div class="row">
  <div class="col-md-12">
    <s:form action="register-bike-event" id="new-bike-event-form" method="POST" cssClass="form-horizontal">
      <s:hidden name="bikeId"/>

      <s:select list="eventTypesList"
                name="eventTypeId"
                listValueKey="name"
                listKey="id"
                key="bike.eventType"
                labelCssClass="col-md-2"
                elementCssClass="col-md-4"/>

      <s:textarea name="descriptiveName"
                  key="bike.descriptiveName"
                  placeholder="%{getText('bike.descriptiveName.placeholder')}"
                  rows="6"
                  labelCssClass="col-md-2"
                  elementCssClass="col-md-6"/>

      <sj:datepicker name="registerDate"
                     type="date"
                     key="bike.date"
                     displayFormat="yy-mm-dd"
                     placeholder="%{getText('bike.date.placeholder')}"
                     labelCssClass="col-md-2"
                     elementCssClass="col-md-4"/>

      <s:textfield name="mileage"
                   type="number"
                   key="bike.mileageInKm"
                   placeholder="%{getText('bike.mileageInKm.placeholder')}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="input-md"/>

      <div class="form-group">
        <div class="col-sm-offset-2 col-md-9">
          <s:submit cssClass="btn btn-primary" key="general.register"/>
        </div>
      </div>
    </s:form>
  </div>
</div>
