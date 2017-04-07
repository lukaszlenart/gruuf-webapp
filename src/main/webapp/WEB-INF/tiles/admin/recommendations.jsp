<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
  <div class="col-md-12">
    <s:url var="addRecommendation" action="recommendation-form"/>
    <s:a href="%{addRecommendation}" class="btn btn-default">Add Recommendation</s:a>
  </div>
</div>

<table class="table table-striped">
  <thead class="header-inverse">
  <tr>
    <th colspan="2">
      <s:text name="bike.metadata"/>
    </th>
    <th colspan="2">
      <br/>
    </th>
  </tr>
  <tr>
    <td colspan="2">
      <s:form method="GET">
        <s:select id="bike-metadata"
                  name="bikeMetadataId"
                  list="metadataOptions"
                  listValueKey="name"
                  listKey="id"
                  emptyOption="true"
                  placeholder="%{getText('bike.metadata.placeholder')}"
                  tooltip="%{getText('bike.metadata.tooltip')}"
                  cssClass="bike"
                  elementCssClass="col-md-8"/>
      </s:form>
    </td>
    <td colspan="2">
      <br/>
    </td>
  </tr>
  <tr>
    <th>Description (English)</th>
    <th>Event Type</th>
    <th>Source</th>
    <th>Notify</th>
    <th>Mileage period</th>
    <th>Monthly period</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="list" var="recommendation">
    <tr>
      <td><s:property value="englishDescription"/></td>
      <td><s:property value="eventType.name"/></td>
      <td><s:text name="%{source.key}"/></td>
      <td><s:property value="notify"/></td>
      <td><s:number name="mileagePeriod"/></td>
      <td><s:number name="monthPeriod"/></td>
      <td>
        <s:url var="edit" action="recommendation-form">
          <s:param name="recommendationId" value="id"/>
        </s:url>
        <s:a value="%{edit}">Edit</s:a>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>

<script type="application/javascript">

  $('#bike-metadata').selectize({
    valueField: 'id',
    labelField: 'name',
    searchField: 'name',
    sortField: 'name',
    create: false,
    closeAfterSelect: true,
    render: {
      option: function(item, escape) {
        return '<div><span class="name">' + escape(item.name) + '</span></div>';
      },
      item: function(item, escape) {
        return '<div><span class="name">' + escape(item.name) + '</span></div>';
      }
    },
    load: function(query, callback) {
      if (!query.length) return callback();
      $.ajax({
        url: 'bike-metadata-json',
        type: 'GET',
        error: function() {
          callback();
        },
        success: function(res) {
          callback(res);
        }
      });
    },
    onChange: function(value) {
      if (value && value.length > 0) {
        $('form').submit();
      }
    }
  });

</script>
