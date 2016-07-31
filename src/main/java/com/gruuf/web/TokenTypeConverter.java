package com.gruuf.web;

import com.gruuf.auth.Token;
import org.apache.struts2.util.StrutsTypeConverter;

import java.util.Map;

public class TokenTypeConverter extends StrutsTypeConverter {

    @Override
    public Object convertFromString(Map map, String[] strings, Class aClass) {
        if (strings != null && strings.length > 0) {
            return Token.valueOf(strings[0]);
        }
        return null;
    }

    @Override
    public String convertToString(Map map, Object o) {
        return String.valueOf(o);
    }
}
