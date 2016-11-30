<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-6 col-md-offset-3">
    <s:form action="login-submit" method="POST" cssClass="form-horizontal" focusElement="login-submit_email">
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

          <s:text name="general.or">or</s:text>

          <s:url action="google-login" var="googleLogin"/>
          <s:a href="%{googleLogin}"
               cssClass="btn btn-default google"
               title="%{getText('user.signingWithGoogle')}">
            <i class="fa fa-google" aria-hidden="true"></i>
            <s:text name="user.login"/>
          </s:a>
        </div>
      </div>
    </s:form>
  </div>
</div>
