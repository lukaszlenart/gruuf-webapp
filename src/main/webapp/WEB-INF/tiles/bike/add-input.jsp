<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <s:form action="add-submit" method="POST" class="form-horizontal col-sm-6">
    <s:textfield name="friendlyName" label="Friendly name" placeholder="ie. myBike"/>
    <s:textfield name="vin" label="VIN" placeholder="ie. JP0912121212" />
    <s:submit label="Register" class="btn btn-default col-sm-offset-3"/>
  </s:form>
</div>
