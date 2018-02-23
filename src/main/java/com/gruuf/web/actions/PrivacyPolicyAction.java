package com.gruuf.web.actions;

import com.gruuf.auth.Anonymous;
import com.gruuf.model.PolicyType;
import com.gruuf.model.User;
import com.gruuf.services.UserStore;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashSet;
import java.util.Set;

import static com.opensymphony.xwork2.Action.SUCCESS;

@Anonymous
@InterceptorRef("defaultWithMessages")
@Result(name = SUCCESS, location = "privacy-policy")
public class PrivacyPolicyAction extends BaseAction {

    @Inject
    private UserStore userStore;

    @Action("approve-policy")
    public String approve() {
        if (currentUser != null) {
            Set<PolicyType> acceptedPolicies = currentUser.getAcceptedPolicies();
            if (acceptedPolicies == null) {
                acceptedPolicies = new HashSet<>();
            }
            acceptedPolicies.add(PolicyType.PRIVACY_POLICY);

            User newUser = User.clone(currentUser)
                    .withAcceptedPolicies(acceptedPolicies)
                    .build();

            newUser = userStore.put(newUser);
            System.out.println(newUser);
            addActionMessage(getText("user.thankYouForAcceptingPolicy"));
        }

        return PRIVACY_POLICY;
    }

    public boolean isAccepted() {
        return currentUser != null && currentUser.isPrivacyPolicyAccepted();
    }
}
