package com.amrendra.codefiesta.utils;

/**
 * Created by amrendrk on 5/15/16.
 */
public class HtmlUtils {

    public static String getLink(String text, String url) {
        StringBuilder sb = new StringBuilder();
        sb.append("<b><a href=\"").append(url).append("\">").append(text).append("</a></b>");
        return sb.toString();
    }

    public static String getBold(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>").append(text).append("</b>");
        return sb.toString();
    }

    public static String getItalic(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("<i>").append(text).append("</i>");
        return sb.toString();
    }

    public static String getUnderline(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("<u>").append(text).append("</u>");
        return sb.toString();
    }
}
