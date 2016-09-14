<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <s:form action="add-submit" method="POST" class="form-horizontal">
    <s:textfield name="friendlyName"
                 key="bike.friendlyName"
                 placeholder="%{getText('bike.friendlyName.placeholder')}"
                 labelCssClass="col-md-4"
                 elementCssClass="col-md-4"
                 cssClass="input-md"/>

    <s:textfield name="vin"
                 key="bike.vin"
                 placeholder="%{getText('bike.vin.placeholder')}"
                 labelCssClass="col-md-4"
                 elementCssClass="col-md-4"
                 cssClass="input-md"/>

    <div class="form-group">
      <div class="col-sm-offset-7">
        <s:submit cssClass="btn btn-primary" key="general.register"/>
      </div>
    </div>

  </s:form>
</div>
