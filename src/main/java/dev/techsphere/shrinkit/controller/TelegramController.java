package dev.techsphere.shrinkit.controller;

import dev.techsphere.shrinkit.telegram.TelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequestMapping("/api")
public class TelegramController {

    @Autowired
    private TelegramBot telegramBot;

    @PostMapping("/webhook")
    public BotApiMethod<?> handleWebhook(@RequestBody Update update) {
        return telegramBot.onWebhookUpdateReceived(update);
    }
}
