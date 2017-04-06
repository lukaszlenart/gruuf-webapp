<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-12 col-md-offset-2">
    <s:form action="update-recommendation" method="POST" cssClass="form-horizontal">
      <s:hidden name="recommendationId"/>

      <s:select id="bike-metadata"
                name="bikeMetadataId"
                key="bike.metadata"
                list="metadataOptions"
                listValueKey="name"
                listKey="id"
                emptyOption="true"
                placeholder="%{getText('bike.metadata.placeholder')}"
                labelCssClass="col-md-2"
                elementCssClass="col-md-4"/>

      <s:select id="event-type"
                name="eventTypeId"
                list="eventTypes"
                listKey="id"
                listValue="name"
                emptyOption="true"
                key="bike.eventType"
                labelCssClass="col-md-2"
                elementCssClass="col-md-4"/>

      <s:textarea name="englishDescription"
                  label="Description (in English)"
                  cols="20"
                  rows="4"
                  labelCssClass="col-md-2"
                  elementCssClass="col-md-4"/>

      <s:select name="source"
                list="allSources"
                listValueKey="'source.' + name().toLowerCase()"
                label="Source of recommendation"
                labelCssClass="col-md-2"
                elementCssClass="col-md-2"
                cssClass="form-control"/>

      <s:checkbox name="monthlyReview"
                  label="Anual maintenance review"/>

      <s:textfield name="monthPeriod"
                   type="number"
                   label="Every months"
                   disabled="%{monthlyReview != true}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-1"/>

      <s:checkbox name="mileageReview"
                  label="Mileage maintenance review"/>

      <s:textfield name="mileagePeriod"
                   type="number"
                   label="Every mileage"
                   disabled="%{mileageReview != true}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-2"/>

      <div class="form-group">
        <div class="col-sm-offset-2 col-md-9">
          <s:submit cssClass="btn btn-primary" key="Save" />
        </div>
      </div>
    </s:form>
  </div>
</div>

<script type="application/javascript">

  $(document).ready(function() {

    $('#bike-metadata').selectize({
      valueField: 'id',
      labelField: 'name',
      searchField: 'name',
      sortField: 'name',
      create: false,
      closeAfterSelect: true,
      render: {
        option: function (item, escape) {
          return '<div><span class="name">' + escape(item.name) + '</span></div>';
        },
        item: function (item, escape) {
          return '<div><span class="name">' + escape(item.name) + '</span></div>';
        }
      },
      load: function (query, callback) {
        if (!query.length) return callback();
        $.ajax({
          url: 'bike-metadata-json',
          type: 'GET',
          error: function () {
            callback();
          },
          success: function (res) {
            callback(res);
          }
        });
      }
    });

    $('#event-type').selectize({
      valueField: 'id',
      labelField: 'name',
      searchField: 'name',
      sortField: 'name',
      create: false,
      render: {
        option: function (item, escape) {
          return '<div><span class="name">' + escape(item.name) + '</span></div>';
        }
      },
      load: function (query, callback) {
        if (!query.length) return callback();
        $.ajax({
          url: 'event-types-json',
          type: 'GET',
          error: function () {
            callback();
          },
          success: function (res) {
            callback(res);
          }
        });
      }
    });

    $("[name=monthlyReview]").on("change", function () {
      if ($(this).is(":checked")) {
        $("[name=monthPeriod]").removeAttr("disabled").focus();
      } else {
        $("[name=monthPeriod]").attr("disabled", "disabled");
      }
    })

    $("[name=mileageReview]").on("change", function () {
      if ($(this).is(":checked")) {
        $("[name=mileagePeriod]").removeAttr("disabled").focus();
      } else {
        $("[name=mileagePeriod]").attr("disabled", "disabled");
      }
    })
  });

</script>
