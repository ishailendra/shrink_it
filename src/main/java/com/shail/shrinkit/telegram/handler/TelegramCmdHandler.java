package com.shail.shrinkit.telegram.handler;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramCmdHandler {

    public String handle(Update update);
}
