<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<nav class="navbar">
  <ul class="nav nav-tabs">
    <li role="presentation">
      <s:url var="history" action="history">
        <s:param name="bikeId" value="%{bikeId}"/>
      </s:url>
      <s:a href="%{history}"><s:text name="bike.bikeHistory"/></s:a>
    </li>
    <li role="presentation">
      <s:url var="recommendations" action="recommendations">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{recommendations}">
        <s:text name="bike.recommendations"/>
      </s:a>
    </li>
    <li role="presentation">
      <s:url var="event" action="new-bike-event">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{event}">
        <s:text name="bike.registerNewEvent"/>
      </s:a>
    </li>
    <li role="presentation" class="active">
      <s:url var="attachments" action="attachments">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{attachments}">
        <s:text name="bike.attachments"/>
      </s:a>
    </li>
  </ul>
</nav>

<div class="row">
  <div class="col-md-6">
<s:if test="spaceAvailable">
    <s:form action="upload" enctype="multipart/form-data" method="POST" cssClass="form-inline">
      <s:hidden name="bikeId"/>

      <s:file name="attachment" key="bikeEvent.attachment"/>

      <div class="form-group">
        <div class="col-sm-offset-3 col-md-6">
          <s:submit key="bikeEvent.upload" cssClass="btn btn-default"/>
        </div>
      </div>

    </s:form>

    <p class="help-block"><s:text name="bikeEvent.spaceLeft"/>: <s:number name="spaceLeft"/> <s:text name="bikeEvent.kiloBytes"/></p>
</s:if>
<s:if test="not spaceAvailable">
    <p class="help-block"><s:text name="bikeEvent.noSpaceLeft"/></p>
</s:if>
  </div>
</div>

<hr/>

<table class="table table-striped">
  <thead>
  <tr>
    <th><s:text name="bikeEvent.fileName"/></th>
    <th><s:text name="bikeEvent.contentType"/></th>
    <th><s:text name="bikeEvent.size"/></th>
    <th><s:text name="bikeEvent.link"/></th>
    <th><s:text name="general.timestamp"/>
    <th><s:text name="general.actions"/>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="attachments" var="attachment">
    <tr>
      <td>
        <s:property value="originalFileName"/>
      </td>
      <td>
        <s:property value="contentType"/>
      </td>
      <td>
        <s:number name="size"/> <s:text name="bikeEvent.kiloBytes"/>
      </td>
      <td>
        <s:a value="%{link}">
          <s:property value="originalFileName"/>
        </s:a>
      </td>
      <td class="text-nowrap">
        <s:date name="timestamp" format="%{userDateFormat}"/>
      </td>
      <td class="text-nowrap">
        <s:url var="delete" action="delete" escapeAmp="false">
          <s:param name="bikeId" value="bikeId"/>
          <s:param name="attachmentId" value="%{id}"/>
        </s:url>
        <s:a value="%{delete}">
          <s:text name="general.delete"/>
        </s:a>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>
