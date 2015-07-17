package me.rbw.web;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandler;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.views.tiles.TilesResult;
import org.apache.tiles.TilesContainer;
import org.apache.tiles.access.TilesAccess;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TilesUnknownHandler implements UnknownHandler {
    @Override
    public ActionConfig handleUnknownAction(String namespace, String actionName) throws XWorkException {
        return null;
    }

    @Override
    public Result handleUnknownResult(ActionContext actionContext, String actionName, ActionConfig actionConfig, String resultCode) throws XWorkException {

        ServletContext servletContext = ServletActionContext.getServletContext();
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response = ServletActionContext.getResponse();

        TilesContainer container = TilesAccess.getContainer(servletContext);

        if (container.isValidDefinition(actionName, request, response)) {
            return new TilesResult(actionName);
        }

        String definition = actionName + "-" + resultCode;
        if (container.isValidDefinition(definition, request, response)) {
            return new TilesResult(definition);
        }

        return null;
    }

    @Override
    public Object handleUnknownActionMethod(Object action, String methodName) {
        return null;
    }
}
