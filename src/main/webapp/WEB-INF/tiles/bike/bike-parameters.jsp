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

<table class="table table-striped">
  <thead class="header-inverse">
  <tr>
    <th>Description</th>
    <th>Value</th>
    <th>Part Number</th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="bikeParameters">
    <tr>
      <td><s:property value="description"/></td>
      <td><s:property value="value"/></td>
      <td><s:property value="partNumber"/></td>
    </tr>
  </s:iterator>
  </tbody>
</table>

