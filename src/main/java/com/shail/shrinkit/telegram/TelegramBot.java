package com.shail.shrinkit.telegram;

import com.shail.shrinkit.model.ShrinkRequest;
import com.shail.shrinkit.model.ShrinkResponse;
import com.shail.shrinkit.service.ShrinkService;
import com.shail.shrinkit.telegram.handler.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final String token;

    private final String username;

    public TelegramBot(@Value("${bot.token}") String token, @Value("${bot.username}") String username) {
        this.token = token;
        this.username = username;

    }

    @Autowired
    private ShrinkService service;

    @Autowired
    private TelegramService tgService;

    @Autowired
    private StartHandler startHandler;

    @Autowired
    private ShrinkHandler shrinkHandler;

    @Autowired
    private UnShrinkHandler unshrinkHandler;

    @Autowired
    private DefaultHandler defaultHandler;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {

            String msg = null;
            Optional<Message> message = tgService.getMessage(update);
            Long chatId = (update == null || update.getMessage() == null) ? -1l : update.getMessage().getChatId();

            if (message.isEmpty() || StringUtils.isBlank(message.get().getText())) {
                msg = "Server Error! Please try again after sometime!";
                sendMessage(msg, chatId);
                return;
            }

            final TelegramCommand cmd = tgService.extractCommand(message.get());

            TelegramCmdHandler handler = switch (cmd) {
                case START ->  startHandler;
                case SHRINK -> shrinkHandler;
                case UN_SHRINK -> unshrinkHandler;
                default -> defaultHandler;
            };

            msg = handler.handle(update);

            sendMessage(msg, chatId);
        }
    }

    private void sendMessage(final String message, final long chatId) {
        if (chatId == -1l) {
            return;
        }
        try {
            execute(createSendMessage(message, chatId));
        } catch (TelegramApiException e) {

        }
    }

    public SendMessage createSendMessage(final String message, final long chatId) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .build();
    }

}
