<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-8 col-md-offset-2 text-center">
    <s:form action="bike-delete-confirm" method="POST" class="form-horizontal">
      <s:hidden name="bikeId"/>

      <h2>
        <s:text name="bike.doYouWantToDeleteTheBike">do you want to delete the bike</s:text>
        <s:property value="selectedBike.name"/>
        ?
      </h2>

      <s:url var="cancelUrl" action="garage" namespace="/"/>
      <s:a value="%{cancelUrl}" class="btn btn-primary">
        <s:text name="general.cancel"/>
      </s:a>
      <s:submit cssClass="btn btn-danger" key="general.confirm"/>

    </s:form>
  </div>
</div>
