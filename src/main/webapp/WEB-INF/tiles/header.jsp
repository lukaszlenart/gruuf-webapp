<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
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
          <s:a href="%{home}"><s:text name="home.title"/></s:a>
        </li>
        <li>
          <s:url var="addbike" action="add" namespace="/bike"/>
          <s:a href="%{addbike}"><s:text name="bike.add"/></s:a>
        </li>
        <s:if test="admin">
        <li>
          <s:url var="users" action="users" namespace="/admin"/>
          <s:a href="%{users}"><s:text name="users.title"/></s:a>
        </li>
        <li>
          <s:url var="eventTypes" action="event-types" namespace="/admin"/>
          <s:a href="%{eventTypes}"><s:text name="eventTypes.title"/></s:a>
        </li>
        <li>
          <s:url var="backup" action="backup" namespace="/admin"/>
          <s:a href="%{backup}"><s:text name="backup.title"/></s:a>
        </li>
        </s:if>
        <li>
          <s:url var="logout" action="logout"/>
          <s:a href="%{logout}"><s:text name="logout.title"/></s:a>
        </li>
        <li><a href="#contact"><s:text name="contact.title"/></a></li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>
