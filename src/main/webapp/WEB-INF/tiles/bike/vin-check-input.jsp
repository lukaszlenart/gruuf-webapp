<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12 col-md-offset-2">
    <s:form action="vin-check-search" method="GET" class="form-horizontal">

      <s:textfield name="vin"
                   key="bike.vin"
                   placeholder="%{getText('bike.vin.placeholder')}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>

      <div class="form-group">
        <div class="col-sm-offset-2 col-md-9">
          <s:submit cssClass="btn btn-primary" key="general.search"/>
        </div>
      </div>

    </s:form>
  </div>

  <div class="row">
    <s:if test="bike != null">
    <div class="col-md-8 col-md-offset-4">
      <h3><s:text name="bike.registered">is registered</s:text></h3>
    </div>
    <div class="col-md-8 col-md-offset-4">
      <s:property value="bike.vin"/>:
      <s:property value="bike.producerAndMake"/> - <s:property value="bike.modelYear"/>
    </div>
    </s:if>
  </div>
</div>