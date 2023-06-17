package hulio13.telegramBoot.localization.entities;

import org.apache.commons.collections4.BidiMap;

public record Language(String langTag, BidiMap<String, String> phrases, BidiMap<String, String> buttons) {
}
