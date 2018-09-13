<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="c" uri="/struts-tags" %>

<div class="row">
  <nav class="navbar">
    <ul class="nav nav-tabs">
      <li role="presentation" class="active">
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
      <li role="presentation">
        <s:url var="attachments" action="attachments">
          <s:param name="bikeId" value="bikeId"/>
        </s:url>
        <s:a value="%{attachments}">
          <s:text name="bike.attachments"/>
        </s:a>
      </li>
    </ul>
  </nav>
</div>
<div class="row">
  <div class="panel panel-primary">
    <div class="panel-body">
      <div class="col-md-6">
        <s:form method="GET" cssClass="form-horizontal">
          <s:hidden name="bikeId"/>
          <s:select name="filter"
                    key="general.show"
                    labelSeparator=":"
                    labelCssClass="col-md-2"
                    elementCssClass="col-md-6"
                    list="searchOptions"
                    listValueKey="key"
                    onchange="this.form.submit()"/>
        </s:form>
      </div>
      <div class="col-md-2 col-md-offset-4">
        <h4>
          <s:text name="general.totalCosts">Total costs:</s:text>:
          <span class="label label-primary">
          <s:number name="bikeDetails.bike.totalCosts" type="currency" currency="%{userCurrency}"/>
        </span>
        </h4>
      </div>
    </div>
    <table class="table table-striped">
      <thead>
      <tr>
        <th><s:text name="bikeEvent.eventType"/></th>
        <th><s:text name="bikeEvent.description"/></th>
        <th class="text-center"><s:text name="bikeEvent.eventDate"/></th>
        <s:if test="bikeDetails.bike.showMileage">
          <th class="text-center">
            <span class="clearfix"><s:text name="bikeEvent.mileage"/></span>
            <span class="badge">
          <s:text name="general.current"/>
          <i class="fa fa-repeat"></i>
        </span>
          </th>
        </s:if>
        <s:if test="bikeDetails.bike.showMth">
          <th class="text-center">
            <span class="clearfix"><s:text name="bikeEvent.mth"/></span>
            <span class="badge">
          <s:text name="general.current"/>
          <i class="fa fa-repeat"></i>
        </span>
          </th>
        </s:if>
        <th class="text-center"><s:text name="bikeEvent.cost"/></th>
        <th class="text-center"><s:text name="general.actions"/></th>
      </tr>
      </thead>
      <tbody>
      <s:iterator value="bikeDetails.events" var="event">
        <tr>
          <td class="text-nowrap">
            <s:iterator value="eventTypes" var="eventType" status="stats">
              <s:property value="name"/><s:if test="%{not #stats.last}">,<br/></s:if>
            </s:iterator>
            <s:if test="temporary">
              <span class="label label-warning"><s:text name="general.temporary">Temporary</s:text></span>
            </s:if>
            <s:if test="system">
              <span class="label label-danger"><s:text name="general.system">System</s:text></span>
            </s:if>
          </td>
          <td>
            <s:property value="description" escapeHtml="false"/>
          </td>
          <td class="text-nowrap text-center">
            <s:date name="registerDate" format="%{userDateFormat}"/>
          </td>
          <s:if test="bikeDetails.bike.showMileage">
            <td class="text-center">
              <span class="clearfix"><s:number name="mileage"/></span>
              <s:if test="showCurrentMileage">
          <span class="badge" title="<s:text name='bikeEvent.currentMileage.title'/>">
            <s:number name="getCurrentMileage()"/>
            <i class="fa fa-repeat"></i>
          </span>
              </s:if>
            </td>
          </s:if>
          <s:if test="bikeDetails.bike.showMth">
            <td class="text-center">
              <span class="clearfix"><s:number name="mth"/></span>
              <s:if test="showCurrentMth">
          <span class="badge" title="<s:text name='bikeEvent.currentMth.title'/>">
            <s:number name="getCurrentMth()"/>
            <i class="fa fa-repeat"></i>
          </span>
              </s:if>
            </td>
          </s:if>
          <td class="text-center">
            <span class="clearfix"><s:number name="cost" type="currency" currency="%{userCurrency}"/></span>
          </td>
          <td class="text-nowrap text-center">
            <s:if test="editable">
              <s:url var="editEvent" action="edit-bike-event" escapeAmp="false">
                <s:param name="bikeId" value="%{bikeId}"/>
                <s:param name="bikeEventId" value="id"/>
              </s:url>
              <s:a value="%{editEvent}" cssClass="editable glyphicon glyphicon-edit" title="%{getText('general.edit')}"><s:text
                  name="general.edit"/></s:a>
            </s:if>

            <s:if test="deletable">
              <s:url var="deleteEvent" action="delete-bike-event" escapeAmp="false">
                <s:param name="bikeId" value="%{bikeId}"/>
                <s:param name="bikeEventId" value="id"/>
              </s:url>
              <s:a value="%{deleteEvent}" cssClass="deletable glyphicon glyphicon-trash" title="%{getText('general.delete')}"><s:text
                  name="general.delete"/></s:a>
            </s:if>

            <s:if test="temporary">
              <s:url var="approveEvent" action="approve-bike-event" escapeAmp="false">
                <s:param name="bikeId" value="%{bikeId}"/>
                <s:param name="bikeEventId" value="id"/>
              </s:url>
              <s:a value="%{approveEvent}" cssClass="approveable glyphicon glyphicon-ok" title="%{getText('general.approve')}"><s:text
                  name="general.approve"/></s:a>
            </s:if>
          </td>
        </tr>
      </s:iterator>
      </tbody>
    </table>
  </div>
</div>