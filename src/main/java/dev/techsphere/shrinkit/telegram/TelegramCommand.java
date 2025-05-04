package dev.techsphere.shrinkit.telegram;

import org.apache.commons.lang3.StringUtils;

public enum TelegramCommand {
    START("/start"),
    SHRINK("/shrink"),
    UN_SHRINK("/un_shrink"),

    UNKNOWN("_");;

    private final String command;

    TelegramCommand(final String cmd) {
        this.command = cmd;
    }

    public String getCommandText() {
        return command;
    }

    public static TelegramCommand createFromString(final String cmd) {
        if (StringUtils.isBlank(cmd) || !cmd.startsWith("/")) {
            return UNKNOWN;
        }

        for (TelegramCommand teleCmd : TelegramCommand.values()) {
            if (cmd.equals(teleCmd.getCommandText())) {
                return teleCmd;
            }
        }

        return TelegramCommand.UNKNOWN;
    }



}
