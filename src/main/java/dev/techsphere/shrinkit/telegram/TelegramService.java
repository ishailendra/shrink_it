package dev.techsphere.shrinkit.telegram;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class TelegramService {

    public Optional<Message> getMessage(final Update update) {
        if (update.hasMessage()) {
            return Optional.of(update.getMessage());
        } else if (update.hasEditedMessage()) {
            return Optional.of(update.getEditedMessage());
        } else {
            return Optional.empty();
        }
    }

    public TelegramCommand extractCommand(final Message message) {
        String[] args = message.getText().split(" ");
        if (args.length == 0) {
            return TelegramCommand.UNKNOWN;
        } else {
            return TelegramCommand.createFromString(args[0]);
        }
    }

    public String getCommandString(TelegramCommand cmd, String text) {
        return StringUtils.isBlank(text) ? "UNKNOWN" : text.replace(cmd.getCommandText(), "").trim();
    }
}
