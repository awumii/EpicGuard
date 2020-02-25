package me.ishift.epicguard.universal.types;

import me.ishift.epicguard.universal.Messages;
import me.ishift.epicguard.universal.util.ChatUtil;

import java.util.List;
import java.util.stream.Collectors;

public enum Reason {
    GEO(Messages.messageKickCountry),
    PROXY(Messages.messageKickProxy),
    ATTACK(Messages.messageKickAttack),
    BLACKLIST(Messages.messageKickBlacklist),
    VERIFY(Messages.messageKickVerify),
    NAMECONTAINS(Messages.messageKickNamecontains);

    private List<String> reason;

    public String getReason() {
        return this.reason.stream().map(s -> ChatUtil.fix(s) + "\n").collect(Collectors.joining());
    }

    Reason(List<String> reason) {
        this.reason = reason;
    }
}
