<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-8 col-md-offset-2">
    <s:form action="contact-submit" method="POST" cssClass="form-horizontal">
      <s:textfield name="email"
                   placeholder="%{getText('user.email.placeholder')}"
                   key="contact.yourEmail"
                   cssClass="input-md"/>

      <s:textarea name="message"
                  key="contact.message"
                  cols="40"
                  rows="8"/>

      <div class="form-group">
        <div class="col-md-8 col-sm-offset-3">
          <s:submit cssClass="btn btn-primary" key="general.submit"/>
        </div>
      </div>

    </s:form>
  </div>
</div>
