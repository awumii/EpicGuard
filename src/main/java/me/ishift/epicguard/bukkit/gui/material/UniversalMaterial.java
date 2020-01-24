package me.ishift.epicguard.bukkit.gui.material;

public enum UniversalMaterial {
    CLOCK("clock"),
    EXP_BOTTLE("exp_bottle"),
    BOOK_AND_QUILL("book_and_quill"),
    CRAFTING("crafting");

    String alias;

    UniversalMaterial(String string) {
        alias = string;
    }

    public String getAlias() {
        return alias;
    }
}
