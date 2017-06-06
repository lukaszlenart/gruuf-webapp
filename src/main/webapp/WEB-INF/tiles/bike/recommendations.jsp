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
    <li role="presentation" class="active">
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

<div class="row">
  <div class="col-md-12">
    <s:url var="requestRecommendation" action="request-recommendation-form">
      <s:param name="bikeId" value="%{bikeId}"/>
    </s:url>
    <s:a href="%{requestRecommendation}" class="btn btn-default">
      <s:text name="general.requestNew"/>
    </s:a>
  </div>
</div>

<table class="table table-striped">
  <thead>
  <tr>
    <th><s:text name="recommendations.recommendation"/></th>
    <th><s:text name="recommendations.source"/></th>
    <th><s:text name="recommendations.period"/></th>
    <th><s:text name="recommendations.fulfilled"/></th>
    <th><s:text name="recommendations.fulfillDate"/></th>
    <th><s:text name="recommendations.fulfillMileage"/></th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="bikeRecommendations">
    <tr>
      <td>
        <s:property value="recommendation.eventType.name"/>
      </td>
      <td class="text-nowrap">
        <s:text name="%{recommendation.source.key}"/>
      </td>
      <td class="text-nowrap">
        <s:if test="recommendation.mileagePeriod">
          <s:text name="recommendations.every"/> <s:number name="recommendation.mileagePeriod"/> <s:text name="general.km"/>
        </s:if>
        <s:if test="recommendation.mileagePeriod && (recommendation.mthPeriod || recommendation.monthPeriod)">
          <s:text name="general.or"/><br/>
        </s:if>
        <s:if test="recommendation.mthPeriod">
          <s:text name="recommendations.every"/> <s:number name="recommendation.mthPeriod"/> <s:text name="general.hours"/>
        </s:if>
        <s:if test="recommendation.mthPeriod && recommendation.monthPeriod">
          <s:text name="general.or"/><br/>
        </s:if>
        <s:if test="recommendation.monthPeriod">
          <s:text name="recommendations.every"/> <s:number name="recommendation.monthPeriod"/> <s:text name="general.months"/>
        </s:if>
      </td>
      <s:if test="fulfilled">
      <td><s:property value="bikeEvent.descriptiveName"/></td>
      <td class="text-nowrap"><s:date name="bikeEvent.registerDate" format="%{userDateFormat}"/></td>
      <td class="text-nowrap"><s:number name="bikeEvent.mileage"/></td>
      </s:if>
      <s:if test="not fulfilled">
      <td colspan="3" class="missing alert alert-danger">
        <s:text name="recommendations.notFulfilled"/>
      </td>
      </s:if>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>
