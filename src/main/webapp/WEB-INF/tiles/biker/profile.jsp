<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-offset-1">
    <s:form action="profile-submit" method="POST" theme="bootstrap" cssClass="form-horizontal">
      <s:textfield name="email"
                   key="biker.email.address"
                   placeholder="%{getText('biker.email.placeholder')}"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-4"
                   cssClass="input-md"
                   helpText="%{getText('biker.email.changeHelp')}"/>

      <s:textfield name="firstName"
                   key="biker.firstName"
                   placeholder="%{getText('biker.firstName.placeholder')}"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>

      <s:textfield name="lastName"
                   key="biker.lastName"
                   placeholder="%{getText('biker.lastName.placeholder')}"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>

      <s:select list="availableUserLocales"
                key="biker.language"
                name="userLocale"
                labelCssClass="col-md-4"
                elementCssClass="col-sm-1"/>

      <s:set var="s2b_form_label_class" value="'col-sm-4'"/>
      <s:set var="s2b_form_element_class" value="'col-sm-8'"/>
      <s:checkbox name="notify"
                  key="biker.notify"
                  labelposition="inline"/>

      <s:password name="password1"
                  key="biker.password"
                  placeholder="%{getText('biker.password.placeholder')}"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"
                  helpText="%{getText('biker.password.changeHelp')}"/>

      <s:password name="password2"
                  key="biker.repeatPassword"
                  placeholder="%{getText('biker.password.placeholder')}"
                  labelCssClass="col-md-4"
                  elementCssClass="col-md-4"/>

      <div class="form-group">
        <div class="col-md-8">
          <s:submit cssClass="btn btn-primary pull-right" key="general.save" />
        </div>
      </div>

      <hr class="col-md-8 col-md-offset-2 alert-danger"/>

      <div class="form-group">
        <div class="col-md-8 col-md-offset-2 text-center">
          <h3>
            <s:text name="user.deleteMyAccount">delete my account</s:text>
          </h3>
          <s:url var="deleteUrl" action="delete"/>
          <s:a href="%{deleteUrl}" cssClass="btn btn-danger">
            <s:text name="general.delete">delete</s:text>
          </s:a>
        </div>
      </div>

    </s:form>
  </div>
</div>
