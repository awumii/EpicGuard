package me.ishift.epicguard.common.detection;

import java.util.List;

public class ProxyChecker {
    private String url;
    private List<String> contains;

    public ProxyChecker(String url, List<String> contains) {
        this.url = url;
        this.contains = contains;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getContains() {
        return contains;
    }
}
