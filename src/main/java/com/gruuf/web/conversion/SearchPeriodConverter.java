package com.gruuf.web.conversion;

import com.gruuf.model.SearchPeriod;
import org.apache.struts2.util.StrutsTypeConverter;

import java.util.Map;

public class SearchPeriodConverter extends StrutsTypeConverter {

    @Override
    public Object convertFromString(Map map, String[] strings, Class aClass) {
        if (strings != null && strings.length > 0) {
            return SearchPeriod.valueOf(strings[0]);
        }
        return null;
    }

    @Override
    public String convertToString(Map map, Object o) {
        return String.valueOf(o);
    }
}
