<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12 col-md-offset-2">
    <s:form action="bike-form-submit" method="POST" class="form-horizontal">
      <s:hidden name="bikeId"/>

      <s:textfield name="friendlyName"
                   key="bike.friendlyName"
                   placeholder="%{getText('bike.friendlyName.placeholder')}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>

      <div class="row">
        <div class="col-md-6">
          <s:select id="metadata"
                    name="bikeMetadataId"
                    list="metadataOptions"
                    listValueKey="name"
                    listKey="id"
                    key="bike.metadata"
                    emptyOption="true"
                    placeholder="%{getText('bike.metadata.placeholder')}"
                    tooltip="%{getText('bike.metadata.tooltip')}"
                    labelCssClass="col-md-4"
                    elementCssClass="col-md-8"
                    cssClass="input-md"/>
        </div>
        <div class="col-md-2">
          <s:a class="media-middle center-block new-request" action="request-bike-metadata" target="_blank">
            <s:text name="general.requestNew"/>
            <span class="glyphicon glyphicon-new-window"></span>
          </s:a>
        </div>
      </div>

      <s:textfield name="vin"
                   key="bike.vin"
                   placeholder="%{getText('bike.vin.placeholder')}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-4"
                   cssClass="input-md"/>

      <s:textfield name="modelYear"
                   key="bike.modelYear"
                   placeholder="%{getText('bike.modelYear.placeholder')}"
                   type="number"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="input-md"
                   inputAppendIcon="log-out"/>

      <s:textfield name="mileage"
                   key="bike.mileageInKm"
                   type="number"
                   placeholder="%{getText('bike.mileageInKm.placeholder')}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="input-md"/>
      <s:hidden name="currentMileage"/>

      <s:set var="s2b_form_label_class" value="'col-sm-2'"/>
      <s:set var="s2b_form_element_class" value="'col-sm-8'"/>

      <s:checkbox name="showMileage"
                  key="bike.showMileage"/>

      <s:textfield name="mth"
                   key="bike.mth"
                   type="number"
                   placeholder="%{getText('bike.mth.placeholder')}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"
                   cssClass="input-md"/>
      <s:hidden name="currentMth"/>

      <s:checkbox name="showMth"
                  key="bike.showMth"/>

      <div class="form-group">
        <div class="col-sm-offset-2 col-md-9">
          <s:submit cssClass="btn btn-primary" key="general.save"/>
        </div>
      </div>

    </s:form>
  </div>
</div>

<script type="application/javascript">

  $('#metadata').selectize({
    valueField: 'id',
    searchField: 'name',
    sortField: 'name',
    labelField: 'name',
    create: false,
    preload: true,
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
    }
  });

</script>