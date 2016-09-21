<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

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
                  placeholder="%{getText('bike.descriptiveName.placeholder')}"
                  cols="40"
                  rows="6"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-6"
                  cssClass="input-md"/>

      <sj:datepicker name="registerDate"
                     type="date"
                     key="bike.date"
                     displayFormat="yy-mm-dd"
                     placeholder="%{getText('bike.date.placeholder')}"
                     labelCssClass="col-md-4"
                     elementCssClass="col-md-6"
                     cssClass="input-md"/>

      <s:textfield name="mileage"
                   type="number"
                   key="bike.mileageInKm"
                   placeholder="%{getText('bike.mileageInKm.placeholder')}"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-6"
                   cssClass="input-md"/>

      <div class="form-group">
        <div class="col-sm-offset-8">
          <s:submit cssClass="btn btn-primary" key="general.register"/>
        </div>
      </div>

    </s:form>
  </div>
</div>

<div class="row">
  <div class="col-md-6">
    <h3><s:text name="bike.bikeHistory"/></h3>
  </div>
</div>
<div class="row">
  <div class="col-md-12">
    <table class="table table-striped">
      <thead>
      <tr>
        <th><s:text name="bikeEvent.eventType"/></th>
        <th><s:text name="bikeEvent.descriptiveName"/></th>
        <th><s:text name="bikeEvent.eventDate"/></th>
        <th><s:text name="bikeEvent.mileage"/></th>
        <th><s:text name="bikeEvent.timestamp"/>
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
            <s:property value="timestamp"/>
          </td>
        </tr>
      </s:iterator>
      </tbody>
    </table>
  </div>
</div>

<s:if test="showRegisterForm">
  <script type="application/javascript">
    $('#new-bike-event-form').collapse('show');
  </script>
</s:if>