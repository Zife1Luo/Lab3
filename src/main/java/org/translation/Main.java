package org.translation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    private static final String QUIT = "quit";

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        // Task: once you finish the JSONTranslator,
        //            you can use it here instead of the InLabByHandTranslator
        //            to try out the whole program!
        // Translator translator = new JSONTranslator(null);
        Translator translator = new JSONTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        while (true) {
            String countryCode = promptForCountry(translator);
            // CheckStyle: The String "quit" appears 3 times in the file.
            // Checkstyle: String literal expressions should be on the left side of an equals comparison
            if (QUIT.equalsIgnoreCase(countryCode)) {
                break;
            }
            // Task: Once you switch promptForCountry so that it returns the country
            //            name rather than the 3-letter country code, you will need to
            //            convert it back to its 3-letter country code when calling promptForLanguage
            String language = promptForLanguage(translator, countryCode);
            if (QUIT.equalsIgnoreCase(language)) {
                break;
            }
            // Task: Once you switch promptForLanguage so that it returns the language
            //            name rather than the 2-letter language code, you will need to
            //            convert it back to its 2-letter language code when calling translate.
            //            Note: you should use the actual names in the message printed below though,
            //            since the user will see the displayed message.
            String translatedCountry = translator.translate(countryCode, language);
            String countryName = new CountryCodeConverter().fromCountryCode(countryCode);
            String languageName = new LanguageCodeConverter().fromLanguageCode(language);
            System.out.println(countryName + " in " + languageName + " is " + translatedCountry);

            System.out.println("Press enter to continue or quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT.equalsIgnoreCase(textTyped)) {
                break;
            }
        }
    }
    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        CountryCodeConverter countryCodeConverter = new CountryCodeConverter();
        // Task: replace the following println call, sort the countries alphabetically,
        //            and print them out; one per line
        //      hint: class Collections provides a static sort method
        // Task: convert the country codes to the actual country names before sorting
        List<String> countryCodes = translator.getCountries();

        Map<String, String> codeToNameMap = countryCodes.stream()
                .collect(Collectors.toMap(code -> code, countryCodeConverter::fromCountryCode));

        List<String> countryNames = codeToNameMap.values().stream().collect(Collectors.toList());

        Collections.sort(countryNames);

        for (String countryName : countryNames) {
            System.out.println(countryName);
        }

        System.out.println("select a country from above:");

        Scanner s = new Scanner(System.in);
        String countryName = s.nextLine();

        return codeToNameMap.entrySet().stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase(countryName))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(QUIT);
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        LanguageCodeConverter languageCodeConverter = new LanguageCodeConverter();
        // Task: replace the line below so that we sort the languages alphabetically and print them out; one per line
        // Task: convert the language codes to the actual language names before sorting
        List<String> languageCodes = translator.getCountryLanguages(country);

        Map<String, String> codeToNameMap = languageCodes.stream()
                .collect(Collectors.toMap(code -> code, languageCodeConverter::fromLanguageCode));

        List<String> languageNames = codeToNameMap.values().stream().collect(Collectors.toList());

        Collections.sort(languageNames);

        for (String languageName : languageNames) {
            System.out.println(languageName);
        }

        System.out.println("select a language from above:");

        Scanner s = new Scanner(System.in);
        String languageName = s.nextLine();

        return codeToNameMap.entrySet().stream()
                .filter(entry -> entry.getValue().equalsIgnoreCase(languageName))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(QUIT);
    }
}
