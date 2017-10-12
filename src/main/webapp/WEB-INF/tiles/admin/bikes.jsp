<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<table class="table table-striped">
  <thead>
  <tr>
    <th>
      <s:text name="bike.friendlyName"/>
      -
      <s:text name="bike.metadata"/>
    </th>
    <th>
      <s:text name="biker.fullName"/>
      (<s:text name="biker.email.address"/>)
    </th>
    <th><s:text name="general.actions"/></th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="list" var="bike">
    <tr>
      <td>
        <s:property value="name"/>
        <s:if test="bikeMetadata">
          -
          <s:property value="bikeMetadata.manufacturer"/>
          <s:property value="bikeMetadata.model"/>
          (<s:property value="bikeMetadata.productionStartYear"/>
          -
          <s:property value="bikeMetadata.productionEndYear"/>
          )
        </s:if>
      </td>
      <td>
        <s:property value="owner.fullName"/>
        (<s:property value="owner.email"/>)
      </td>
      <td>
        <s:url var="reportUrl" action="report" namespace="/bike">
          <s:param name="bikeId" value="id"/>
        </s:url>
        <s:a class="btn btn-info" role="button" href="%{reportUrl}" target="_blank">
          <s:text name="general.fullReport"/>
        </s:a>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>