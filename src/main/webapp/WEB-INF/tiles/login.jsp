<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-6 col-md-offset-3">
    <s:form action="login-submit" method="POST" cssClass="form-horizontal">
      <s:textfield name="email"
                   placeholder="%{getText('user.email.placeholder')}"
                   label="E-mail"
                   cssClass="input-md"/>

      <s:password name="password"
                  placeholder="%{getText('user.password.placeholder')}"
                  key="user.password"/>

      <div class="form-group">
        <div class="col-sm-offset-3 col-md-6">
          <s:submit cssClass="btn btn-primary" key="user.login"/>
        </div>
      </div>

    </s:form>
  </div>
</div>
