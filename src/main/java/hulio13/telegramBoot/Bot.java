package hulio13.telegramBoot;

import hulio13.telegramBoot.inputHandlers.MessageContainer;
import hulio13.telegramBoot.inputHandlers.utils.KeyboardConverter;
import hulio13.telegramBoot.tgUserProperties.TgUserProperties;
import hulio13.telegramBoot.tgUserProperties.database.jsonDb.JsonTgUserPropertiesRepository;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public final class Bot extends TelegramLongPollingBot {

    private final String botName;

    private boolean isStarted = false;

    public Bot(String token, String botName) {
        super(token);
        this.botName = botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String telegramId = update.getMessage().getFrom().getId().toString();

        UserIOService userIOService = new UserIOService(telegramId);

        userIOService.processInput(update.getMessage().getText());
        MessageContainer msg = userIOService.getMessageForUser();

        var keyboard = KeyboardConverter.fromStringToTelegram(msg.getStringKeyboard());

        try {
            execute(
                    SendMessage.builder()
                            .text(msg.getMsg())
                            .chatId(update.getMessage().getChatId())
                            .replyMarkup(keyboard)
                            .build()
            );
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private TgUserProperties initializeTgUserProperties(String telegramId,
                                                        JsonTgUserPropertiesRepository repository) {
        TgUserProperties properties;
        properties = new TgUserProperties(telegramId, telegramId);


        return properties;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    public void start() throws TelegramApiException {
        if (!isStarted) {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            isStarted = true;
        } else {
            throw new RuntimeException("Bot already started.");
        }
    }
}