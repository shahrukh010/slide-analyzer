package com.paperflite.slideanalyzer.services.impl;

import java.util.Objects;

public class HtmlObject {
    private String htmlContent;

    public HtmlObject(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    @Override
    public String toString() {
        return "{\"htmlContent\": \"" + htmlContent.replaceAll("\"", "\\\\\"") + "\"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HtmlObject that = (HtmlObject) o;
        return Objects.equals(htmlContent, that.htmlContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(htmlContent);
    }
}
