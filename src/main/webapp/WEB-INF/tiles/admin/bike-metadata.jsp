<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
  <div class="col-md-12">
    <s:url var="addMetadata" action="bike-metadata-form"/>
    <s:a href="%{addMetadata}" class="btn btn-default">Add Bike Metadata</s:a>
  </div>
</div>

<table class="table table-striped">
  <thead class="header-inverse">
  <tr>
    <th>Manufacturer</th>
    <th>Model</th>
    <th>Production Start</th>
    <th>Production End</th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="list" var="metadata">
    <tr>
      <td><s:property value="manufacturer"/></td>
      <td><s:property value="model"/></td>
      <td><s:property value="productionStartYear"/></td>
      <td><s:property value="productionEndYear"/></td>
      <td>
        <s:url var="editMetadata" action="bike-metadata-form">
          <s:param name="bikeMetadataId" value="id"/>
        </s:url>
        <s:a value="%{editMetadata}">Edit</s:a>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>

