<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12">
    <s:form action="login-submit" method="POST" cssClass="form-horizontal">
      <s:textfield name="email"
                   placeholder="e.g. lukasz@gruuf.com"
                   label="E-mail"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>

      <s:password name="password"
                  placeholder="e.g. mysecret"
                  label="Password"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"
                  cssClass="input-md"/>

      <div class="form-group">
        <div class="col-sm-offset-7">
          <s:submit cssClass="btn btn-primary" key="Login"/>
        </div>
      </div>

    </s:form>
  </div>
</div>
