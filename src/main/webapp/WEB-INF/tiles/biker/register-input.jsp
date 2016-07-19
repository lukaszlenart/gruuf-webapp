<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12">
    <s:form action="register-submit" method="POST" theme="bootstrap" cssClass="form-horizontal">
      <s:textfield name="email"
                   label="E-mail address"
                   placeholder="e.g. lukasz@gruuf.com"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>

      <s:password name="password1"
                  label="Password"
                  placeholder="e.g. your seecret"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"/>

      <s:password name="password2"
                  label="Repeat"
                  placeholder="e.g. your seecret"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"/>

      <div class="form-group">
        <div class="col-sm-offset-7">
          <s:submit cssClass="btn btn-primary" key="Register" />
        </div>
      </div>
    </s:form>
  </div>
</div>
