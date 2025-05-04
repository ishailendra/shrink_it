package dev.techsphere.shrinkit.telegram.handler;

import dev.techsphere.shrinkit.exception.ShrinkItException;
import dev.techsphere.shrinkit.model.ShrinkResponse;
import dev.techsphere.shrinkit.model.ShrinkUrlInfoRequest;
import dev.techsphere.shrinkit.service.ShrinkService;
import dev.techsphere.shrinkit.telegram.TelegramCommand;
import dev.techsphere.shrinkit.telegram.TelegramService;
import dev.techsphere.shrinkit.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UnShrinkHandler implements dev.techsphere.shrinkit.telegram.handler.TelegramCmdHandler {

    @Autowired
    private ShrinkService service;

    @Autowired
    private TelegramService tgService;

    @Override
    public String handle(Update update) {
        try {
            Message message = update.getMessage();

            String shrinkUrl = tgService.getCommandString(TelegramCommand.UN_SHRINK, message.getText());
            ShrinkResponse resp = service.shrinkUrlInfo(new ShrinkUrlInfoRequest(shrinkUrl));
            return CommonUtils.getFormattedResp(resp);

        } catch (Exception e) {
            return e instanceof ShrinkItException ? e.getMessage() : "Something went wrong! Please try after sometime!";
        }
    }

}
