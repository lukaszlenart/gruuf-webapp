<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <h3 class="col-md-12 col-md-offset-2">
    <s:property value="selectedBike.name"/>
  </h3>
</div>

<div class="row">
  <h4 class="col-md-12 col-md-offset-2">
    <s:text name="transfer.explanation">
      Enter e-mail address of a new owner of the bike
    </s:text>
  </h4>
</div>

<div class="row">
  <div class="col-md-12 col-md-offset-2">
    <s:form action="prepare" method="POST" class="form-horizontal">
      <s:hidden name="bikeId"/>

      <s:textfield name="emailAddress"
                   placeholder="%{getText('biker.email.placeholder')}"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>

      <div class="form-group">
        <div class="col-md-9">
          <s:submit cssClass="btn btn-primary" key="general.transfer"/>
        </div>
      </div>

    </s:form>
  </div>

</div>