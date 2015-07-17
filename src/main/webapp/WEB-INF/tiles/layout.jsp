<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>

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
</head>
<body>
  <tiles:insertAttribute name="header"/>

  <div class="container">
    <div class="row">
      <h2><tiles:insertAttribute name="title"/></h2>
    </div>
    <tiles:insertAttribute name="body"/>
  </div>

  <tiles:insertAttribute name="footer"/>
</body>
</html>
