<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:form action="login-submit" method="POST" class="form-horizontal col-md-4">
  <div class="form-group">
    <label for="login" class="col-sm-4 control-label">Login</label>
    <div class="col-sm-8">
      <s:textfield name="login" class="form-control" placeholder="ie. johnny"/>
    </div>
  </div>
  <div class="form-group">
    <label for="password" class="col-sm-4 control-label">Password</label>
    <div class="col-sm-8">
      <s:password name="password" class="form-control" placeholder="ie. mysecret" />
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-4 col-sm-10">
      <s:submit label="Login" class="btn btn-default"/>
    </div>
  </div>
</s:form>
