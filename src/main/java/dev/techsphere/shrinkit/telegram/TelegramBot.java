package dev.techsphere.shrinkit.telegram;

import dev.techsphere.shrinkit.service.ShrinkService;
import dev.techsphere.shrinkit.telegram.handler.ShrinkHandler;
import dev.techsphere.shrinkit.telegram.handler.StartHandler;
import dev.techsphere.shrinkit.telegram.handler.TelegramCmdHandler;
import dev.techsphere.shrinkit.telegram.handler.UnShrinkHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;


@Component
public class TelegramBot extends TelegramWebhookBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.webhook-path}")
    private String webhookPath;

    @Autowired
    private ShrinkService service;

    @Autowired
    private dev.techsphere.shrinkit.telegram.TelegramService tgService;

    @Autowired
    private StartHandler startHandler;

    @Autowired
    private ShrinkHandler shrinkHandler;

    @Autowired
    private UnShrinkHandler unshrinkHandler;

    public TelegramBot(@Value("${telegram.bot.token}") String botToken) {
        super(botToken);
    }

    public SendMessage createSendMessage(final String message, final long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        long chatId = -1L;

        if (update.hasMessage()) {
            String msg = null;
            Optional<Message> message = tgService.getMessage(update);
            chatId = update.getMessage() == null ? -1L : update.getMessage().getChatId();

            if (message.isEmpty() || StringUtils.isBlank(message.get().getText())) {
                msg = "Server Error! Please try again after sometime!";
                return createSendMessage(msg, chatId);
            }

            final TelegramCommand cmd = tgService.extractCommand(message.get());

            TelegramCmdHandler handler = switch (cmd) {
                case SHRINK -> shrinkHandler;
                case UN_SHRINK -> unshrinkHandler;
                default -> startHandler;
            };

            msg = handler.handle(update);

            return createSendMessage(msg, chatId);
        }

        return createSendMessage("Server Error! Please try again after sometime!", chatId);
    }

    @Override
    public String getBotPath() {
        return webhookPath;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

}
