package org.translation;

import java.util.ArrayList;
import java.util.List;

// Task: modify this class so that it also supports the Spanish language code "es" and
//            one more language code of your choice. Each member of your group should add
//            support for one additional langauge code on a branch; then push and create a pull request on GitHub.

// Extra Task: if your group has extra time, you can add support for another country code in this class.

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {
    public static final String CANADA = "can";
    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */

    @Override
    public List<String> getCountryLanguages(String country) {
        // Checkstyle: The String "can" appears 4 times in the file.
        if (CANADA.equals(country)) {
            return new ArrayList<>(List.of("de", "en", "zh", "es", "fr"));
        }
        return new ArrayList<>();
    }

    // Checkstyle: Static variable definition in wrong order.

    /**
     * Returns the country abbreviations for all countries whose translations are
     * available from this Translator.
     *
     * @return list of country abbreviations for which we have translations available
     */
    @Override
    public List<String> getCountries() {
        return new ArrayList<>(List.of(CANADA));
    }

    /**
     * Returns the name of the country based on the specified country abbreviation and language abbreviation.
     *
     * @param country  the country
     * @param language the language
     * @return the name of the country in the given language or null if no translation is available
     */
    @Override
    public String translate(String country, String language) {
        // Checkstyle: Return count is 5 (max allowed for non-void methods/ lambdas is 2).
        // Checkstyle: String literal expressions should be on the left side of an equals comparison
        if (!CANADA.equals(country)) {
            return null;
        }

        String result;
        switch (language) {
            case "de":
                result = "Kanada";
                break;
            case "en":
                result = "Canada";
                break;
            case "zh":
                result = "加拿大";
                break;
            case "es":
                result = "Canadá";
                break;
            case "fr":
                result = "Canada";
                break;
            default:
                result = null;
                break;
        }

        return result;
    }
}
