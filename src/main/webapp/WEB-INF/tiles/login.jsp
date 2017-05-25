<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<script>

  function start() {
    gapi.load('auth2', function() {
      auth2 = gapi.auth2.init({
        client_id: '811433969470-bn139qq11labf52qafsuulh69pa5uhc0.apps.googleusercontent.com'
      });
    });
  }

  window.fbAsyncInit = function() {
    FB.init({
      appId      : '439646633077329',
      cookie     : true,
      xfbml      : true,
      version    : 'v2.8'
    });
    FB.AppEvents.logPageView();
  };

  (function(d, s, id){
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) {return;}
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));
</script>

<script src="https://apis.google.com/js/client:platform.js?onload=start" async defer></script>

<div class="row">
  <div class="col-md-6 col-md-offset-3">
    <s:form action="login-submit" method="POST" cssClass="form-horizontal" focusElement="login-submit_email">
      <s:textfield name="email"
                   placeholder="%{getText('user.email.placeholder')}"
                   label="E-mail"
                   cssClass="input-md"/>

      <s:password name="password"
                  placeholder="%{getText('user.password.placeholder')}"
                  key="user.password"/>

      <div class="form-group">
        <div class="col-sm-offset-3 col-md-12">
          <s:submit cssClass="btn btn-primary" key="user.login"/>

          <s:text name="general.or">or</s:text>

          <button id="google-signin" class="btn btn-default google"
                  title="<s:text name='user.signinWithGoogle'/>">
            <i class="fa fa-google" aria-hidden="true"></i>
            <s:text name="user.signinWithGoogle"/>
          </button>

          <s:text name="general.or">or</s:text>

          <button id="facebook-signin" class="btn btn-default facebook"
                  title="<s:text name='user.signinWithFacebook'/>">
            <i class="fa fa-facebook" aria-hidden="true"></i>
            <s:text name="user.signinWithFacebook"/>
          </button>
        </div>
      </div>
      <div class="form-group">
        <div class="col-sm-offset-3 col-md-9">
          <button id="demo-login" class="btn btn-default facebook"
                  title="<s:text name='user.signinWithDemoUser'/>">
            <i class="fa fa-user-secret" aria-hidden="true"></i>
            <s:text name="user.signinWithDemoUser"/>
          </button>
        </div>
      </div>
    </s:form>
  </div>
</div>

<script>

  function disableButtons() {
    $('form button, form input[type=submit]').each(function() {
      $(this).addClass("in-progress").attr("disabled", "disabled");
    })
  }

  function signInGoogleCallback(authResult) {
    if (authResult['code']) {

      disableButtons();

      // Send the code to the server
      $.post({
        dataType: 'json',
        contentType: 'application/json',
        url: '/google-login?struts.enableJSONValidation=true',
        data: JSON.stringify({ 'code': authResult['code'] })
      }).done(function(result) {
        window.location = result.location;
      });
    }
  }

  function signInFacebookCallback(response) {
    if (response.status === 'connected') {

      disableButtons();

      // Send the code to the server
      $.post({
        dataType: 'json',
        contentType: 'application/json',
        url: '/facebook-login?struts.enableJSONValidation=true',
        data: JSON.stringify({ 'accessToken': response.authResponse.accessToken })
      }).done(function(result) {
        window.location = result.location;
      });
    }
  }

  $('#google-signin').click(function() {
    auth2.grantOfflineAccess({'redirect_uri': 'postmessage'}).then(signInGoogleCallback);

    return false;
  });

  $('#facebook-signin').click(function() {
    FB.getLoginStatus(function(response) {
      signInFacebookCallback(response);
    }, {scope: 'public_profile,email'});
    return false;
  });

  $('#demo-login').click(function() {
    disableButtons();

    $('[name=email]').val('<s:property value="demoUserName"/>');
    $('[name=password]').val('<s:property value="demoPassword"/>');
    $('form').submit();

    return false;
  })
</script>
