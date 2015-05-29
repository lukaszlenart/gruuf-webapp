<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:form action="add-submit" method="POST">
<div class="row">
  <s:textfield name="friendlyName"/>
</div>
<div class="row">
  <s:textfield name="vin"/>
</div>
<div class="row">
  <s:submit/>
</div>
</s:form>
