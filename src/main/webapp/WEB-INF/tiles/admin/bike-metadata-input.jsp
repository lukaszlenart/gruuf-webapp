<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12 col-md-offset-2">
    <s:form action="update-bike-metadata" method="POST" cssClass="form-horizontal">
      <s:hidden name="userId"/>
      <s:hidden name="bikeMetadataId"/>

      <s:textfield name="manufacturer"
                   label="Manufacturer"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-4"/>

      <s:textfield name="model"
                   label="Model"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-4"/>

      <s:textfield name="productionStartYear"
                   label="Production Start Year"
                   type="number"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="form-control"
                   inputAppendIcon="log-out"/>

      <s:textfield name="productionEndYear"
                   label="Production End Year"
                   type="number"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="form-control"
                   inputAppendIcon="log-in"/>

      <div class="form-group">
        <div class="col-sm-offset-2 col-md-9">
          <s:submit cssClass="btn btn-primary" key="Save" />
        </div>
      </div>
    </s:form>
  </div>
</div>
