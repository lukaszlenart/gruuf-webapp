<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<footer class="footer">
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
      <s:text name="general.allRightsReserved"/> @ Łukasz Lenart, ver. <s:property value="currentVersion"/>
      <div class="fb-like" data-href="https://www.facebook.com/GruufApp/" data-layout="button" data-action="like" data-size="small" data-show-faces="true" data-share="true"></div>
    </div>
  </div>
</footer>
