<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12 col-md-offset-2">
    <s:form action="request-bike-metadata-submit" method="POST" cssClass="form-horizontal">
      <s:hidden name="userId"/>

      <s:textfield name="manufacturer"
                   key="bikeMetadata.manufacturer"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-4"/>

      <s:textfield name="model"
                   key="bikeMetadata.model"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-4"/>

      <s:textfield name="productionStartYear"
                   key="bikeMetadata.productionStartYear"
                   type="number"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="form-control"
                   inputAppendIcon="log-out"/>

      <s:textfield name="productionEndYear"
                   key="bikeMetadata.productionEndYear"
                   type="number"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="form-control"
                   inputAppendIcon="log-in"/>

      <div class="form-group">
        <div class="col-sm-offset-2 col-md-9">
          <s:submit cssClass="btn btn-primary" key="general.register" />
        </div>
      </div>
    </s:form>
  </div>
</div>
