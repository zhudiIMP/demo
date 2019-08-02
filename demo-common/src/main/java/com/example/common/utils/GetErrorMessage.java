package com.example.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by User on 2018/5/28.
 */
public class GetErrorMessage {
    public static String printStackTraceToString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }
}
