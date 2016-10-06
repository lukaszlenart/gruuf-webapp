<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-6 col-md-offset-3">
    <s:form action="bike-form-submit" method="POST" class="form-horizontal">
      <s:hidden name="bikeId"/>

      <s:textfield name="friendlyName"
                   key="bike.friendlyName"
                   placeholder="%{getText('bike.friendlyName.placeholder')}"
                   cssClass="input-md"/>

      <s:textfield name="vin"
                   key="bike.vin"
                   placeholder="%{getText('bike.vin.placeholder')}"
                   cssClass="input-md"/>

      <s:textfield name="mileage"
                   key="bike.mileageInKm"
                   type="number"
                   placeholder="%{getText('bike.mileageInKm.placeholder')}"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>
      <s:hidden name="currentMileage"/>

      <div class="form-group">
        <div class="col-sm-offset-3 col-md-9">
          <s:submit cssClass="btn btn-primary" key="general.save"/>
        </div>
      </div>

    </s:form>
  </div>
</div>
