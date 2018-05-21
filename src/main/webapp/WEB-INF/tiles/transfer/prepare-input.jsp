<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <h3 class="col-md-12 col-md-offset-2">
    <s:property value="selectedBike.name"/>
  </h3>
</div>

<div class="row">
  <div class="col-md-12 col-md-offset-2">
    <s:form action="perform" method="POST" class="form-horizontal">
      <s:hidden name="bikeId"/>
      <s:hidden name="emailAddress"/>

      <h4>
        <s:text name="transfer.confirmation">
          Please confirm ownership transfer to
        </s:text>:

        <s:property value="emailAddress"/>
      </h4>

      <div class="form-group">
        <div class="col-md-9">
          <s:submit cssClass="btn btn-primary" key="general.confirm"/>
        </div>
      </div>

    </s:form>
  </div>

</div>