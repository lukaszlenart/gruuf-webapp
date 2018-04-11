<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<footer class="footer hidden-print">
  <div class="container">
    <div class="text-muted text-center">
      <div id="fb-root"></div>
      <script>(function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = 'https://connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.11&appId=439646633077329';
        fjs.parentNode.insertBefore(js, fjs);
      }(document, 'script', 'facebook-jssdk'));</script>
      <div class="fb-like" data-href="https://www.facebook.com/GruufApp/" data-layout="button" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>
      <s:url var="privacyPolicy" action="privacy-policy"/>
      <s:text name="general.allRightsReserved"/> @ Łukasz Lenart,
      ver. <s:property value="currentVersion"/>,
      <s:a href="%{privacyPolicy}"><s:text name="privacyPolicy.title"/></s:a>
    </div>
  </div>
</footer>
