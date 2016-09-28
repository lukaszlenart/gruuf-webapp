<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12">
    <s:form action="update-event-type" method="POST" cssClass="form-horizontal">
      <s:hidden name="userId"/>

      <s:textfield name="name"
                   label="Event name"
                   labelCssClass="col-md-4"
                   elementCssClass="col-md-4"/>

      <s:select list="statuses"
                name="status"
                label="Status"
                labelCssClass="col-md-4"
                elementCssClass="col-md-4"/>

      <div class="form-group">
        <div class="col-sm-offset-7">
          <s:submit cssClass="btn btn-primary" key="Add" />
        </div>
      </div>
    </s:form>
  </div>
</div>
