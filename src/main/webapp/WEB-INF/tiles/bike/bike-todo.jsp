<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<tiles:putAttribute name="js">
  <script src='js/event-types.js'></script>
</tiles:putAttribute>

<div class="row">
  <h3>
    <s:property value="bikeMetadata.fullName"/>
  </h3>
</div>

<div class="row">
  <s:form action="bike-todo-submit" method="POST" cssClass="form-inline col-md-12">
    <h4>Register a new TODO</h4>
    <s:hidden name="bikeId"/>
    <s:hidden name="bikeEventId"/>

    <s:textfield id="event-types"
                 name="eventTypeIds"
                 key="bike.eventTypes"
                 placeholder="%{getText('bike.eventTypes.placeholder')}"
                 tooltip="%{getText('bike.eventTypes.tooltip')}"
                 labelCssClass="col-md-4"
                 elementCssClass="col-md-8"
                 cssClass="input-md"/>

    <s:set var="s2b_form_element_class" value="'inline-input'"/>

    <s:textarea name="description"
                key="bikeTodo.description"
                rows="1"
                cols="40"
                onfocus="expandRows(this)"
                onblur="shrinkRows(this)"/>

    <s:submit cssClass="btn btn-primary" key="general.save"/>

  </s:form>
</div>

<hr/>

<div class="row">
  <table class="table table-striped">
  <thead class="header-inverse">
  <tr>
    <th>Description</th>
    <th>Value</th>
    <th class="text-center">Part Number</th>
    <th class="text-center">Approved</th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="bikeParameters">
    <tr>
      <td width="40%"><s:property value="description" escapeHtml="false"/></td>
      <td width="30%"><s:property value="value"/></td>
    </tr>
  </s:iterator>
  </tbody>
</table>
</div>

<script type="application/javascript">

  bindEventTypes($('#event-types'));

</script>