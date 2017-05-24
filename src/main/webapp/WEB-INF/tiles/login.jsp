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

</script>

<script src="https://apis.google.com/js/client:platform.js?onload=start" async defer></script>

<script>

  function signInCallback(authResult) {
    if (authResult['code']) {

      // Hide the sign-in button now that the user is authorized, for example:
      $('#google-signin').addClass("in-progress").attr("disabled", "disabled");

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

</script>

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
        <div class="col-sm-offset-3 col-md-6">
          <s:submit cssClass="btn btn-primary" key="user.login"/>

          <s:text name="general.or">or</s:text>

          <button id="google-signin" class="btn btn-default google"
                  title="<s:text name='user.signinWithGoogle'/>">
            <i class="fa fa-google" aria-hidden="true"></i>
            <s:text name="user.signin"/>
          </button>

          <script>
            $('#google-signin').click(function() {
              // signInCallback defined in step 6.
              auth2.grantOfflineAccess({'redirect_uri': 'postmessage'}).then(signInCallback);

              return false;
            });
          </script>
        </div>
      </div>
    </s:form>
  </div>
</div>

<div class="row">
  <div class="col-md-5 col-md-offset-4">
    <div class="well">
      <div class="panel panel-info">
      <div class="panel-heading">
          <s:text name="login.youCanLoginWithDemoUser"/>
      </div>
      <div class="panel-body">
        <div>
          <span><s:text name="user.login"/>:</span>
          <span class="text-info"><s:property value="demoUserName"/></span>
        </div>
        <div>
          <span><s:text name="user.password"/>:</span>
          <span class="text-info"><s:property value="demoPassword"/></span>
        </div>
      </div>
    </div>
    </div>
  </div>
</div>
