<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <s:form action="add-submit" method="POST" class="form-horizontal col-md-4">
    <div class="form-group">
      <label for="friendlyName" class="col-sm-4 control-label">Friendly name</label>
      <div class="col-sm-8">
        <s:textfield name="friendlyName" class="form-control" placeholder="ie. myBike"/>
        <s:fielderror fieldName="friendlyName"/>
      </div>
    </div>
    <div class="form-group">
      <label for="vin" class="col-sm-4 control-label">VIN</label>
      <div class="col-sm-8">
        <s:textfield name="vin" class="form-control" placeholder="ie. JP0912121212" />
        <s:fielderror fieldName="vin"/>
      </div>
    </div>
    <div class="form-group">
      <div class="col-sm-offset-4 col-sm-10">
        <s:submit label="Register" class="btn btn-default"/>
      </div>
    </div>
  </s:form>
</div>
