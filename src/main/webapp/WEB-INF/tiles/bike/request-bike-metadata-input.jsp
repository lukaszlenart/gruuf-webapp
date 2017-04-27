<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12 col-md-offset-2">
    <s:form action="request-bike-metadata-submit" method="POST" cssClass="form-horizontal">
      <s:hidden name="userId"/>

      <s:select name="manufacturer"
                list="manufacturers"
                id="manufacturers"
                emptyOption="true"
                key="bikeMetadata.manufacturer"
                labelCssClass="col-md-2"
                elementCssClass="col-md-4"/>

      <s:textfield name="model"
                   key="bikeMetadata.model"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-4"/>

      <s:textfield name="productionStartYear"
                   key="bikeMetadata.productionStartYear"
                   type="number"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="form-control"
                   inputAppendIcon="log-out"/>

      <s:textfield name="productionEndYear"
                   key="bikeMetadata.productionEndYear"
                   type="number"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="form-control"
                   inputAppendIcon="log-in"
                   tooltip="%{getText('bikeMetadata.productionEndYear.tooltip')}"/>

      <div class="form-group">
        <div class="col-sm-offset-2 col-md-9">
          <s:submit cssClass="btn btn-primary" key="general.register" />
        </div>
      </div>
    </s:form>
  </div>
</div>

<script type="application/javascript">

  $('#manufacturers').selectize({
    valueField: 'value',
    searchField: 'value',
    sortField: 'value',
    labelField: 'text',
    create: true,
    preload: true,
    render: {
      option: function(item, escape) {
        return '<div><span class="name">' + escape(item.text) + '</span></div>';
      },
      item: function(item, escape) {
        return '<div><span class="name">' + escape(item.text) + '</span></div>';
      }
    },
    load: function(query, callback) {
      if (!query.length) return callback();
      $.ajax({
        url: 'bike-manufacturers-json',
        type: 'GET',
        error: function() {
          callback();
        },
        success: function(res) {
          callback(res);
        }
      });
    }
  });

</script>