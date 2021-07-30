package me.xneox.epicguard.core.storage;

import java.util.List;

public class AddressMeta {
    private final List<String> nicknames;
    private boolean blacklisted;
    private boolean whitelisted;

    public AddressMeta(boolean blacklisted, boolean whitelisted, List<String> nicknames) {
        this.blacklisted = blacklisted;
        this.whitelisted = whitelisted;
        this.nicknames = nicknames;
    }

    public boolean blacklisted() {
        return this.blacklisted;
    }

    public void blacklisted(boolean blacklisted) {
        this.blacklisted = blacklisted;
    }

    public boolean whitelisted() {
        return this.whitelisted;
    }

    public void whitelisted(boolean whitelisted) {
        this.whitelisted = whitelisted;
    }

    public List<String> nicknames() {
        return this.nicknames;
    }
}
