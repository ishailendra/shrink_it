package com.shail.shrinkit.config;

import com.shail.shrinkit.telegram.TelegramBot;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@ConditionalOnBean(TelegramBot.class)
@Configuration
public class TelegramBotAutoConfig {

    private final List<BotSession> sessions = new ArrayList<>();

    private final TelegramBot telegramBot;

    @PostConstruct
    public void start() {


        boolean isBotAvailable = Objects.nonNull(telegramBot);

        if (isBotAvailable) {
            try {
                TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
                sessions.add(api.registerBot(telegramBot));
            } catch (TelegramApiException e) {

            }
        }
    }
}
