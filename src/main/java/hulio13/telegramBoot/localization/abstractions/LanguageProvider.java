package hulio13.telegramBoot.localization.abstractions;

import hulio13.telegramBoot.localization.entities.Language;

import java.util.List;

public interface LanguageProvider {
    List<Language> getAll();
}
