<!DOCTYPE html>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="sb" uri="/struts-bootstrap-tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
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
  <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

  <link rel="stylesheet" href="/main.css">

  <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
  <!--[if lt IE 9]>
  <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
  <![endif]-->

  <sb:head includeScripts="false" includeScriptsValidation="false" includeStyles="false"/>
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
