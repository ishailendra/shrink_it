package com.shail.shrinkit.telegram.handler;

import com.shail.shrinkit.service.ShrinkService;
import com.shail.shrinkit.telegram.TelegramCommand;
import com.shail.shrinkit.telegram.TelegramService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnShrinkHandler implements TelegramCmdHandler {

    @Autowired
    private ShrinkService service;

    @Autowired
    private TelegramService tgService;

    @Override
    public String handle(Update update) {
        Message message = update.getMessage();

        String shrinkUrl = tgService.getCommandString(TelegramCommand.UN_SHRINK, message.getText());
        String key = service.getShrinkKey(shrinkUrl);
        String fullUrl = service.getFullUrl(key);

        return StringUtils.isBlank(fullUrl) ? "Error: Unable to find URL to redirect to." : fullUrl;
    }
}
