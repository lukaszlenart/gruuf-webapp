package com.gruuf.web.conversion;

import com.gruuf.model.UserLocale;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

import java.util.Map;

public class UserLocaleConverter extends StrutsTypeConverter {

    private static final Logger LOG = LogManager.getLogger(UserLocaleConverter.class);

    @Override
    public Object convertFromString(Map map, String[] strings, Class aClass) {
        if (strings != null && strings.length > 0) {
            LOG.debug("Converting {} to UserLocale", strings[0]);
            return UserLocale.valueOf(strings[0]);
        }
        LOG.debug("Cannot convert {} to UserLocale", (Object[]) strings);
        return null;
    }

    @Override
    public String convertToString(Map map, Object o) {
        LOG.debug("Converting {} to String", o);
        return String.valueOf(o);
    }
}
