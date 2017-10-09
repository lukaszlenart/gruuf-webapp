package com.gruuf.web.actions.bike;

import com.gruuf.auth.BikeRestriction;
import com.gruuf.model.BikeDetails;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.Date;

import static com.opensymphony.xwork2.Action.INPUT;

@BikeRestriction
@Result(name = INPUT, location = "bike/report")
public class ReportAction extends BaseBikeAction {

    @SkipValidation
    public String execute() {
        return "report";
    }

    public BikeDetails getBikeDetails() {
        return loadBikeDetails();
    }

    public Date getReportDate() {
        return new Date();
    }

}
