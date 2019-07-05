package com.etoak.crawl.util.http;

import com.etoak.crawl.util.enums.ContentTypes;
import com.etoak.crawl.util.text.StringUtil;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.message.BasicHeader;

import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;


public class HttpConfig {

    private String proxy;
    private ConcurrentSkipListSet<Header> headers = new ConcurrentSkipListSet<>(new Comparator<Header>() {
        @Override
        public int compare(Header o1, Header o2) {
            return o1.getName().compareTo(o2.getName());
        }
    });

    private ContentTypes contentType = ContentTypes.FORM;
    private HttpVersion httpVersion;
    private String referer;
    private String accept;

    public ContentTypes getContentType() {
        return contentType;
    }

    public void setContentType(ContentTypes contentType) {
        this.contentType = contentType;
    }

    public HttpConfig addHeader(String name, String value) {
        headers.add(new BasicHeader(name, value));
        return this;
    }

    public HttpConfig updateHeader(String name, String value) {
        removeHeader(name);
        headers.add(new BasicHeader(name, value));
        return this;
    }

    public HttpConfig remvoeHeader(String name, String value) {
        headers.remove(new BasicHeader(name, value));
        return this;
    }

    public HttpConfig removeHeader(String name) {
        Header needRemovedHeader = null;
        for (Header header : headers) {
            if (header.getName().equals(name)) {
                needRemovedHeader = header;
            }
        }

        if (!StringUtil.isEmpty(needRemovedHeader)) {
            headers.remove(needRemovedHeader);
        }

        return this;
    }

    public Header[] getHeaders() {
        Header[] headerArray = new Header[headers.size()];
        return headers.toArray(headerArray);
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        addHeader("Referer", referer);
        this.referer = referer;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        addHeader("Accept", accept);
        this.accept = accept;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }
}
