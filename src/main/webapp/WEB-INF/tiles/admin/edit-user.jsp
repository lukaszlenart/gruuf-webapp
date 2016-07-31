<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12">
    <s:form action="update-user" method="POST" cssClass="form-horizontal">
      <s:hidden name="userId"/>

      <s:textfield name="email"
                   label="e-mail"
                   readonly="true"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-4"/>

      <s:textfield name="firstName"
                  label="First Name"
                  placeholder="e.g. John"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"/>

      <s:textfield name="lastName"
                  label="Last Name"
                  placeholder="e.g. Kowalski"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"/>

      <s:checkboxlist list="availableTokens"
                      name="tokens"
                      labelCssClass="col-md-4"
                      elementCssClass="col-md-4"/>

      <div class="form-group">
        <div class="col-sm-offset-7">
          <s:submit cssClass="btn btn-primary" key="Change" />
        </div>
      </div>
    </s:form>
  </div>
</div>
