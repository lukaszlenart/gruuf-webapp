<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-4 col-md-offset-4">
    <s:form action="reset-submit" method="POST" class="form-vertical" focusElement="reset-submit_email">
      <s:textfield name="email"
                   placeholder="%{getText('user.email.placeholder')}"
                   key="user.email"
                   elementCssClass="has-feedback"
                   class="input-md" />

      <div class="form-group">
        <s:submit cssClass="btn btn-primary" key="user.reset"/>
      </div>
    </s:form>
  </div>
</div>
