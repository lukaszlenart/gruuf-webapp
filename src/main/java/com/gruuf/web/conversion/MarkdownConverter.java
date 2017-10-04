package com.gruuf.web.conversion;

import com.github.rjeschke.txtmark.Processor;
import com.gruuf.model.Markdown;
import org.apache.struts2.util.StrutsTypeConverter;

import java.util.Map;

public class MarkdownConverter extends StrutsTypeConverter {

    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        if (values != null && values.length > 0) {
            return Markdown.of(values[0]);
        }
        return null;
    }

    @Override
    public String convertToString(Map context, Object o) {
        if (o instanceof Markdown) {
            Markdown md = (Markdown) o;
            return Processor.process(md.getContent());
        }
        return null;
    }
}
