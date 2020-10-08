<!DOCTYPE html>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta property="og:url" content="https://app.gruuf.com"/>
  <meta property="og:type" content="website"/>
  <meta property="og:title" content="GruufApp"/>
  <meta property="og:description"
        content="Collect information about your motorbike and be up to date with periodic maintenance"/>
  <meta property="og:image" content="https://app.gruuf.com/img/wheel-spinner.gif"/>

  <title>
    <tiles:insertAttribute name="titleName" ignore="true"/>
  </title>

  <sj:head loadFromGoogle="true" locale="%{userLanguage}"/>
  <sb:head includeScripts="true" includeStyles="true"/>

  <script type="application/javascript"
          src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.4/js/standalone/selectize.js"></script>

  <link rel="stylesheet" href="/css/font-awesome.min.css">
  <link rel="stylesheet" href="/css/main.css">
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.4/css/selectize.bootstrap3.min.css">

  <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>

<div class="loader">
  <img src="${pageContext.request.contextPath}/img/wheel-spinner.gif">
</div>

<tiles:insertAttribute name="header"/>

<div class="container">
  <div class="row">
    <div class="page-header">
      <tiles:insertAttribute name="title"/>
    </div>
  </div>
  <div class="row">
    <div class="col-md-8 col-md-offset-2">
      <s:actionerror/>
      <s:actionmessage/>
    </div>
  </div>

  <tiles:insertAttribute name="body"/>
</div>

<tiles:insertAttribute name="footer"/>

<script type="application/javascript">
  $('.dropdown-toggle').dropdown()
</script>
</body>
</html>
