package dev.techsphere.shrinkit.telegram.handler;

import dev.techsphere.shrinkit.exception.ShrinkItException;
import dev.techsphere.shrinkit.model.ShrinkRequest;
import dev.techsphere.shrinkit.model.ShrinkResponse;
import dev.techsphere.shrinkit.service.ShrinkService;
import dev.techsphere.shrinkit.telegram.TelegramCommand;
import dev.techsphere.shrinkit.telegram.TelegramService;
import dev.techsphere.shrinkit.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class ShrinkHandler implements TelegramCmdHandler {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ShrinkService service;

    @Autowired
    private TelegramService tgService;

    @Override
    public String handle(Update update) {
        try {
            Message message = update.getMessage();
            String url = tgService.getCommandString(TelegramCommand.SHRINK, message.getText());

            ShrinkRequest request = new ShrinkRequest();
            request.setOriginalUrl(url);

            ShrinkResponse resp = service.shrinkUrl(request);

            return CommonUtils.getFormattedResp(resp);

        } catch (Exception e) {
            return e instanceof ShrinkItException ? e.getMessage() : "Something went wrong! Please try after sometime!";
        }
    }
}
