<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
  <div class="col-md-12">
    <h3>
      <s:property value="bikeMetadata.fullName"/>
    </h3>
  </div>
</div>

<div class="row">
  <s:form action="request-bike-parameter" method="POST" cssClass="form-inline col-md-12">
    <h4>Register a new parameter</h4>
    <s:hidden name="bikeMetadataId"/>

    <s:set var="s2b_form_element_class" value="'inline-input'"/>

    <s:textarea name="description"
                key="bikeParameter.description"
                rows="1"
                cols="40"
                onfocus="expandRows(this)"
                onblur="shrinkRows(this)"/>

    <s:textfield name="value"
                 key="general.value"/>

    <s:textfield name="partNumber"
                 key="bikeParameter.partNumber"/>

    <s:submit cssClass="btn btn-primary" key="general.save"/>

  </s:form>
</div>

<hr/>

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
      <td width="15%" class="text-center"><s:property value="partNumber"/></td>
      <td width="15%" class="text-center"><s:text name="%{approvedKey}"/></td>
    </tr>
  </s:iterator>
  </tbody>
</table>

<script type="application/javascript">

  function expandRows(textarea) {
    $(textarea).attr('rows', 4);
  }

  function shrinkRows(textarea) {
    $(textarea).attr('rows', 1);
  }

</script>