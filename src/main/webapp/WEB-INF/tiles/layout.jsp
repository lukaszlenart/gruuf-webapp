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
  <title>
    <tiles:insertAttribute name="title" ignore="true"/>
  </title>

  <sj:head loadFromGoogle="true" locale="%{userLanguage}"/>
  <sb:head includeScripts="false" includeScriptsValidation="false" includeStyles="true"/>

  <script type="application/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.4/js/standalone/selectize.js"></script>

  <link rel="stylesheet" href="/css/font-awesome.min.css">
  <link rel="stylesheet" href="/main.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.4/css/selectize.bootstrap3.min.css">

</head>
<body>
  <tiles:insertAttribute name="header"/>

  <div class="container">
    <div class="row">
      <div class="page-header">
        <h1><tiles:insertAttribute name="title"/></h1>
      </div>
    </div>
    <div class="row">
      <s:actionerror cssClass="col-md-12"/>
      <s:actionmessage cssClass="col-md-12"/>
    </div>

    <tiles:insertAttribute name="body"/>
  </div>

  <tiles:insertAttribute name="footer"/>
</body>
</html>
