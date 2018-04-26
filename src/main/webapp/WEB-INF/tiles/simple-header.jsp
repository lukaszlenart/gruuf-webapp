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
          <s:url var="login" action="login"/>
          <s:a href="%{login}"><s:text name="user.login"/></s:a>
        </li>
        <li>
          <s:url var="registerBiker" action="register" namespace="/biker"/>
          <s:a href="%{registerBiker}"><s:text name="general.register"/></s:a>
        </li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li>
          <s:url var="contact" action="contact"/>
          <s:a href="%{contact}"><s:text name="contact.title"/></s:a>
        </li>
        <li>
          <s:url var="about" action="about"/>
          <s:a href="%{about}"><s:text name="about.title"/></s:a>
        </li>
        <li>
          <s:url var="privacyPolicy" action="privacy-policy"/>
          <s:a href="%{privacyPolicy}"><s:text name="privacyPolicy.title"/></s:a>
        </li>
      </ul>
    </div><!--/.nav-collapse -->
  </div>
</nav>
