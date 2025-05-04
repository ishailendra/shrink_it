package dev.techsphere.shrinkit.telegram.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartHandler implements TelegramCmdHandler {

    @Override
    public String handle(Update update) {
        final String userName = update.getMessage().getFrom().getUserName();
        final String hello = "Hello " + userName;
        final String msg = """
                Shrink IT is a URL shortening service, which provides short aliases for redirection of long URLs.
                /shrink <long-url> To create a shrink/shorten url
                /un_shrink <shrink-url> Returns the original long url
                """;

        return hello +"\n" + msg;
    }
}
