<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-6 col-md-offset-3">
    <s:form action="update-user" method="POST" cssClass="form-horizontal">
      <s:hidden name="userId"/>

      <s:textfield name="email"
                   label="e-mail"
                   readonly="true"
                   cssClass="input-md"/>

      <s:textfield name="firstName"
                  label="First Name"
                  placeholder="e.g. John"
                   cssClass="input-md"/>

      <s:textfield name="lastName"
                  label="Last Name"
                  placeholder="e.g. Kowalski"
                   cssClass="input-md"/>

      <s:checkboxlist list="availableTokens"
                      name="tokens"
                      label="Auth tokens"
                      labelposition="inline"/>

      <s:checkboxlist list="availablePolicies"
                      name="acceptedPolicies"
                      label="Accepted policies"
                      labelposition="inline"/>

      <s:select list="availableUserLocales"
                name="userLocale"
                label="User locale"
                elementCssClass="col-sm-2"/>

      <s:checkbox name="notify"
                  label="Notify"/>

      <div class="form-group">
        <div class="col-sm-offset-3 col-md-9">
          <s:submit cssClass="btn btn-primary" key="Change" />
        </div>
      </div>
    </s:form>
  </div>
</div>
