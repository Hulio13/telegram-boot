package hulio13.telegramBoot.localization;

import java.util.Map;
import java.util.Set;

public final class VariablesInPhraseInserter {
    static public String insert(String phrase, Map<String, Object> vars) {
        Set<String> keys = vars.keySet();

        for (var key :
                keys) {
            phrase = phrase.replace("{{" + key + "}}", vars.get(key).toString());
        }

        return phrase;
    }
}
