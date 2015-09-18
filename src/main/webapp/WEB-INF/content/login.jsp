<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:form action="login-submit" method="POST" class="form-horizontal col-sm-4">
  <s:textfield name="login" placeholder="ie. johnny" label="Login"/>
  <s:password name="password" placeholder="ie. mysecret" label="Password" />
  <s:submit label="Login" class="btn btn-default col-sm-offset-3"/>
</s:form>
