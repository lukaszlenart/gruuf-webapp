<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12">
    <s:form action="register-submit" method="POST" theme="bootstrap" cssClass="form-horizontal">
      <s:textfield name="email"
                   key="biker.email.address"
                   placeholder="%{getText('biker.email.placeholder')}"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>

      <s:password name="password1"
                  key="biker.password"
                  placeholder="%{getText('biker.password.placeholder')}"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"/>

      <s:password name="password2"
                  key="biker.password.repeat"
                  placeholder="%{getText('biker.password.placeholder')}"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"/>

      <div class="form-group">
        <div class="col-sm-offset-7">
          <s:submit cssClass="btn btn-primary" key="general.register" />
        </div>
      </div>
    </s:form>
  </div>
</div>
