<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <s:form action="register-submit" method="POST" class="form-horizontal col-sm-6">
    <s:textfield name="email" label="E-mail address" placeholder="ie. lukasz@rbw.me"/>
    <s:password name="password1" label="Password" placeholder="ie. your seecret" />
    <s:password name="password2" label="Repeat" placeholder="ie. your seecret" />
    <s:submit label="Register" class="btn btn-default col-sm-offset-3"/>
  </s:form>
</div>
