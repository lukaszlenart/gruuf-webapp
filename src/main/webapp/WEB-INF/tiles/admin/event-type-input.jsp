<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-6 col-md-offset-2">
    <s:form action="update-event-type" method="POST" cssClass="form-horizontal">
      <s:hidden name="userId"/>
      <s:hidden name="eventTypeId"/>

      <s:textfield name="name"
                   label="Event name"/>

      <s:select list="availableStatuses"
                name="status"
                label="Status"/>

      <div class="form-group">
        <div class="col-md-offset-10">
          <s:submit cssClass="btn btn-primary" key="Add" />
        </div>
      </div>
    </s:form>
  </div>
</div>
