<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12">
    <s:form action="update-password" method="POST" cssClass="form-horizontal">
      <s:hidden name="userId"/>

      <s:password name="oldPassword"
                  label="Old password"
                  placeholder="e.g. your seecret"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"/>

      <s:password name="newPassword1"
                  label="New password"
                  placeholder="e.g. your seecret"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"/>

      <s:password name="newPassword2"
                  label="Repeat new password"
                  placeholder="e.g. your seecret"
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
