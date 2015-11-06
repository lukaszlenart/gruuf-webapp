<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <s:property value="bikeDetails.bike.name"/>
</div>
<s:iterator value="bikeDetails.history" var="event">
  <div class="row">
    <s:property value="event.timestamp"/>
  </div>
</s:iterator>
