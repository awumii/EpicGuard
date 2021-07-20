package me.xneox.epicguard.core.storage;

import java.util.Arrays;
import java.util.List;

public class AddressMeta {
    private boolean blacklisted;
    private boolean whitelisted;
    private List<String> nicknames;

    public AddressMeta(boolean blacklisted, boolean whitelisted, String nicknames) {
        this.blacklisted = blacklisted;
        this.whitelisted = whitelisted;
        this.nicknames = Arrays.asList(nicknames.split(","));
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

    public void nicknames(List<String> nicknames) {
        this.nicknames = nicknames;
    }
}
