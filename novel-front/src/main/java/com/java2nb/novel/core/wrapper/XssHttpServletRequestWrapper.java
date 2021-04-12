package com.java2nb.novel.core.wrapper;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Arrays;
import java.util.List;

/**
 * Pemrosesan filter XSS
 *
 * @author Administrator
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Jika ada kode html yang dikirimkan sendiri, Anda perlu mengatur nama yang sesuai tanpa penyaringan
     */
    private static final List<String> noFilterNames = Arrays.asList("content");

    /**
     * @param request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (!noFilterNames.contains(name) && values != null) {
            int length = values.length;
            String[] escapseValues = new String[length];
            for (int i = 0; i < length; i++) {
                escapseValues[i] = values[i].replaceAll("<", "&lt;").replaceAll(">", "&gt;");
            }
            return escapseValues;
        }
        return values;
    }
}
