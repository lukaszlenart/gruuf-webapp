<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-6 col-md-offset-2">
    <s:form action="request-event-type-submit" method="POST" cssClass="form-horizontal">
      <s:hidden name="userId"/>

      <s:textfield name="name"
                   key="eventType.eventName"/>

      <s:select list="availableStatuses"
                name="status"
                listValueKey="key"
                key="eventType.status"/>

      <div class="form-group">
        <div class="col-md-offset-10">
          <s:submit cssClass="btn btn-primary" key="general.save" />
        </div>
      </div>
    </s:form>
  </div>
</div>
