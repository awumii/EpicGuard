package me.ishift.epicguard.common.antibot;

import java.util.List;

public class ProxyChecker {
    private final String url;
    private final List<String> contains;

    public ProxyChecker(String url, List<String> contains) {
        this.url = url;
        this.contains = contains;
    }

    public String getUrl() {
        return this.url;
    }

    public List<String> getContains() {
        return this.contains;
    }
}
