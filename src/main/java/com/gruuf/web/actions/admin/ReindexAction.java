package com.gruuf.web.actions.admin;

import com.gruuf.auth.Token;
import com.gruuf.auth.Tokens;
import com.gruuf.services.AttachmentsStorage;
import com.gruuf.services.BikeHistory;
import com.gruuf.services.EventTypes;
import com.gruuf.services.Garage;
import com.gruuf.services.Recommendations;
import com.gruuf.web.actions.BaseAction;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.annotation.Result;

@Tokens(Token.ADMIN)
@Result(location = "/", type = "redirect")
public class ReindexAction extends BaseAction {

    @Inject
    private BikeHistory bikeHistory;
    @Inject
    private Garage garage;
    @Inject
    private EventTypes eventTypes;
    @Inject
    private Recommendations recommendations;
    @Inject
    private AttachmentsStorage attachmentsStorage;

    public String execute() {
        garage.reindex();
        bikeHistory.reindex();
        eventTypes.reindex();
        recommendations.reindex();
        attachmentsStorage.reindex();

        return SUCCESS;
    }
}
