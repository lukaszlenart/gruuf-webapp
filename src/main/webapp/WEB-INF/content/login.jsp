<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
  <title><s:text name="login.title"/></title>
</head>

<body>

<s:form action="login-submit" method="POST">
  <ul>
    <li>
      <s:text name="login.userName"/>
      <s:textfield name="userName"/>
    </li>
    <li>
      <s:text name="login.password"/>
      <s:password name="userPass"/>
    </li>
    <li>
      <s:submit key="login.submit"/>
    </li>
  </ul>
</s:form>

</body>
</html>
