package com.wanhong.common.web;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
/**
 * @author wangmeng247
 * @date 2018-02-10 11:40
 */
public class UrlBuilder {
    private final URL base;
    private final boolean ignoreEmpty;
    private final Charset charset;
    private final Map<String, Object> queryMap;

    public UrlBuilder(String base) throws MalformedURLException {
        this(base, "utf-8", true);
    }

    public UrlBuilder(String base, String charsetName) throws MalformedURLException {
        this(base, charsetName, true);
    }

    public UrlBuilder(String base, String charsetName, boolean ignoreEmpty) throws MalformedURLException {
        this.base = new URL(base);
        this.charset = Charset.forName(charsetName);
        this.ignoreEmpty = ignoreEmpty;
        String queryString = this.base.getQuery();
        if (!StringUtils.isEmpty(queryString)) {
            this.queryMap = new LinkedHashMap(this.parseQuery(queryString));
        } else {
            this.queryMap = Collections.emptyMap();
        }

    }

    private Map<String, Object> parseQuery(String query) {
        String[] params = query.split("&");
        Map<String, Object> map = new LinkedHashMap(params.length);
        String[] arr$ = params;
        int len$ = params.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String param = arr$[i$];
            String[] strings = param.split("=");
            String name = strings[0];
            String value = null;
            if (strings.length > 1) {
                value = strings[1];
            }

            map.put(name, value);
        }

        return map;
    }

    public UrlBuilder.Builder forPath(String path) {
        return new UrlBuilder.Builder(this.base, path, this.charset.name(), this.ignoreEmpty, this.queryMap);
    }

    public static class Builder {
        final URL base;
        String path;
        String charsetName;
        boolean ignoreEmpty;
        final Map<String, Object> urlParameters;

        Builder(URL base, String path, String charsetName, boolean ignoreEmpty, Map<String, Object> queryMap) {
            this.base = base;
            this.path = path;
            this.charsetName = charsetName;
            this.ignoreEmpty = ignoreEmpty;
            this.urlParameters = new LinkedHashMap(queryMap);
        }

        public UrlBuilder.Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public UrlBuilder.Builder setCharsetName(String charsetName) {
            this.charsetName = charsetName;
            return this;
        }

        public UrlBuilder.Builder setIgnoreEmpty(boolean ignoreEmpty) {
            this.ignoreEmpty = ignoreEmpty;
            return this;
        }

        public String build() throws MalformedURLException {
            String path = this.prefixPath(this.base.getPath(), this.path);
            int port = this.base.getPort();
            if (this.base.getPort() == this.base.getDefaultPort()) {
                port = -1;
            }

            StringBuilder builder = new StringBuilder((new URL(this.base.getProtocol(), this.base.getHost(), port, path)).toString());
            StringBuilder query = new StringBuilder();
            Iterator i$ = this.urlParameters.entrySet().iterator();

            while(true) {
                while(true) {
                    String key;
                    Object value;
                    do {
                        if (!i$.hasNext()) {
                            if (query.length() > 0) {
                                query.replace(0, 1, "?");
                            }

                            return builder.append(query).toString();
                        }

                        Entry<String, Object> entry = (Entry)i$.next();
                        key = (String)entry.getKey();
                        value = entry.getValue();
                    } while(value == null);

                    if (value instanceof Object[]) {
                        Object[] arr = (Object[])((Object[])value);
                        int len = arr.length;

                        for(int i = 0; i < len; ++i) {
                            Object v = arr[i];
                            this.appendQueryString(key, v, query);
                        }
                    } else if (value instanceof Collection) {
                        Iterator i = ((Collection)value).iterator();

                        while(i.hasNext()) {
                            Object v = i.next();
                            this.appendQueryString(key, v, query);
                        }
                    } else {
                        this.appendQueryString(key, value, query);
                    }
                }
            }
        }

        void appendQueryString(String key, Object v, StringBuilder sb) {
            if (v != null) {
                String value = String.valueOf(v);
                if (!this.ignoreEmpty || value.trim().length() != 0) {
                    sb.append("&").append(key).append("=").append(this.encodeUrl(value));
                }
            }
        }

        String encodeUrl(String value) {
            String result;
            try {
                result = URLEncoder.encode(value, StringUtils.isNotBlank(this.charsetName) ? this.charsetName : Charset.defaultCharset().name());
            } catch (UnsupportedEncodingException var4) {
                result = value;
            }

            return result;
        }

        String prefixPath(String contextPath, String path) {
            String returnPath;
            if (path != null && contextPath != null) {
                if (contextPath.endsWith("/") && path.startsWith("/")) {
                    returnPath = contextPath + path.substring(1);
                } else {
                    returnPath = contextPath + path;
                }
            } else if (path == null && contextPath == null) {
                returnPath = "/";
            } else if (contextPath == null) {
                returnPath = path;
            } else {
                returnPath = contextPath;
            }

            return returnPath;
        }

        public UrlBuilder.Builder add(String key, Object value) {
            Object newValue;
            if (this.urlParameters.containsKey(key)) {
                Object o = this.urlParameters.get(key);
                if (o == null) {
                    newValue = value;
                } else {
                    List<Object> container = new LinkedList();
                    this.append(container, o);
                    this.append(container, value);
                    newValue = container;
                }
            } else {
                newValue = value;
            }

            this.urlParameters.put(key, newValue);
            return this;
        }

        void append(List<Object> container, Object o) {
            if (o instanceof Object[]) {
                Object[] arr$ = (Object[])((Object[])o);
                int len$ = arr$.length;

                for(int i$ = 0; i$ < len$; ++i$) {
                    Object e = arr$[i$];
                    container.add(e);
                }
            } else if (o instanceof Collection) {
                container.addAll((Collection)o);
            } else {
                container.add(o);
            }

        }

        public UrlBuilder.Builder add(Map<String, Object> values) {
            Iterator i$ = values.entrySet().iterator();

            while(i$.hasNext()) {
                Entry<String, Object> entry = (Entry)i$.next();
                this.add((String)entry.getKey(), entry.getValue());
            }

            return this;
        }

        public UrlBuilder.Builder put(String key, Object value) {
            this.urlParameters.put(key, value);
            return this;
        }

        public UrlBuilder.Builder put(Map<String, Object> values) {
            this.urlParameters.putAll(values);
            return this;
        }
    }
}
