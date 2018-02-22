<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="row">
  <div class="col-md-8 col-md-offset-2">
    <div class="jumbotron">
      <h2><s:text name="about.aboutGruuf">about gruuf</s:text></h2>
      <p><s:text name="about.intro">
        this service allows you to control history of your motorbike, keep track of incoming periodic
        maintenance activities and any other service activities your perform in your motorbike.
      </s:text></p>
    </div>
    <div class="panel panel-info">
      <div class="panel-heading">
        <s:text name="about.currentState">currently the following features are available:</s:text>
      </div>
      <ul class="list-group">
        <li class="list-group-item"><s:text name="about.feature1">
          register details of your motorbike i.e.: vin, manufacturer, make, production years
        </s:text></li>
        <li class="list-group-item"><s:text name="about.feature2">
          list history of performed service activities
        </s:text></li>
        <li class="list-group-item"><s:text name="about.feature3">
          register new service activities performed in your motorbike
        </s:text></li>
        <li class="list-group-item"><s:text name="about.feature4">
          be notified about incoming/missing periodic maintenance activities
        </s:text></li>
        <li class="list-group-item"><s:text name="about.feature5">
          request new periodic maintenance activities specific to your motorbike
        </s:text></li>
      </ul>
    </div>
    <div class="well well-sm"><s:text name="about.moreFeatures">
      we continue to extend the system by adding new features, if something is missing do not hesitate to contact us with ideas you have on mind
    </s:text></div>

    <div class="panel panel-danger">
      <div class="panel-heading">
        <s:text name="about.pricing">pricing</s:text>
      </div>
      <div class="panel-body"><s:text name="about.noteAboutPraising">
        the system is free of charge forever but some options can be extra paid in the feature
      </s:text></div>
    </div>

    <div class="panel panel-success">
      <div class="panel-heading">
        <s:text name="privacyPolicy.title">privacy policy</s:text>
      </div>
      <div class="panel-body">
        <s:text name="privacyPolicy.pleaseReadOur">please read our</s:text>
        <s:url var="privacyPolicy" action="privacy-policy"/>
        <s:a href="%{privacyPolicy}"><s:text name="privacyPolicy.privacyPolicy"/></s:a>
      </div>
    </div>
  </div>
</div>
