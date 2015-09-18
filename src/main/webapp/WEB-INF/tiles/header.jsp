<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<nav class="navbar navbar-inverse">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/"><s:text name="project.name"/></a>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
      <ul class="nav navbar-nav">
        <li>
          <s:url var="home" action="home" namespace="/" />
          <s:a href="%{home}">Home</s:a>
        </li>
        <li>
          <s:url var="registerBiker" action="register" namespace="/biker" />
          <s:a href="%{registerBiker}">Register</s:a>
        </li>
        <li>
          <s:url var="addbike" action="add" namespace="/bike"/>
          <s:a href="%{addbike}">Add bike</s:a>
        </li>
        <li><a href="#contact">Contact</a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>
