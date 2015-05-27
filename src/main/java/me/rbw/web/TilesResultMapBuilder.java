package me.rbw.web;

import com.opensymphony.xwork2.config.entities.PackageConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.convention.DefaultResultMapBuilder;
import org.apache.struts2.convention.annotation.Action;

import javax.servlet.ServletContext;
import java.util.Collections;
import java.util.Map;

public class TilesResultMapBuilder extends DefaultResultMapBuilder {

    @Inject
    public TilesResultMapBuilder(ServletContext servletContext, Container container,
                                 @Inject("struts.convention.relative.result.types") String relativeResultTypes) {
        super(servletContext, container, relativeResultTypes);
    }

    @Override
    public Map<String, ResultConfig> build(Class<?> actionClass, Action annotation, String actionName, PackageConfig packageConfig) {
        return Collections.emptyMap();
    }
}
