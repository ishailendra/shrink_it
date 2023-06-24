package com.shail.shrinkit.telegram.handler;

import com.shail.shrinkit.model.ShrinkRequest;
import com.shail.shrinkit.model.ShrinkResponse;
import com.shail.shrinkit.service.ShrinkService;
import com.shail.shrinkit.telegram.TelegramCommand;
import com.shail.shrinkit.telegram.TelegramService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ShrinkHandler implements TelegramCmdHandler {

    @Autowired
    private ShrinkService service;

    @Autowired
    private TelegramService tgService;

    @Override
    public String handle(Update update) {
        Message message = update.getMessage();
        String url = tgService.getCommandString(TelegramCommand.SHRINK, message.getText());

        ShrinkRequest request = new ShrinkRequest();
        request.setUrl(url);

        ShrinkResponse shrinkResponse = service.shrinkUrl(request);

        if (shrinkResponse.isValidUrl() && StringUtils.isBlank(shrinkResponse.getErrorDesc()))
            return shrinkResponse.getShortenUrl();
        else
            return "Please enter a valid url";
    }
}
