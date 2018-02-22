package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import org.apache.struts2.convention.annotation.Result;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Anonymous
@Result(name = SUCCESS, location = "privacy-policy")
public class PrivacyPolicyAction extends BaseAction {
}
