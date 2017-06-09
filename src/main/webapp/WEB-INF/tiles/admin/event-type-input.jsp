<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-6 col-md-offset-2">
    <s:form action="update-event-type?eventTypeId=%{eventTypeId}" method="POST" cssClass="form-horizontal">

      <s:property value="names"/>

      <s:iterator value="availableUserLocales" var="userLocale">
        <s:label value="%{userLocale}"
                 elementCssClass="col-sm-2"/>

        <s:textfield name="names['%{userLocale}']"
                     value="%{names[userLocale]}"
                     key="eventType.eventName"/>
      </s:iterator>


      <s:select list="availableStatuses"
                name="status"
                key="eventType.status"/>

      <div class="form-group">
        <div class="col-md-offset-10">
          <s:submit cssClass="btn btn-primary" key="general.save" />
        </div>
      </div>
    </s:form>
  </div>
</div>
