package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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

    private static final String QUIT_COMMAND = "quit";
    private static final CountryCodeConverter COUNTRY_CODE_CONVERTER = new CountryCodeConverter();
    private static final LanguageCodeConverter LANGUAGE_CODE_CONVERTER = new LanguageCodeConverter();

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
            String country = promptForCountry(translator);
            // CheckStyle: The String "quit" appears 3 times in the file.
            // Checkstyle: String literal expressions should be on the left side of an equals comparison
            if (QUIT_COMMAND.equals(country)) {
                break;
            }
            // Task: Once you switch promptForCountry so that it returns the country
            //            name rather than the 3-letter country code, you will need to
            //            convert it back to its 3-letter country code when calling promptForLanguage
            String countryCode = COUNTRY_CODE_CONVERTER.fromCountry(country);
            if (countryCode == null) {
                System.out.println("Invalid country selection. Try again.");
                continue;
            }

            String language = promptForLanguage(translator, countryCode);
            if (QUIT_COMMAND.equals(language)) {
                break;
            }
            // Task: Once you switch promptForLanguage so that it returns the language
            //            name rather than the 2-letter language code, you will need to
            //            convert it back to its 2-letter language code when calling translate.
            //            Note: you should use the actual names in the message printed below though,
            //            since the user will see the displayed message.
            String languageCode = LANGUAGE_CODE_CONVERTER.fromLanguage(language);
            if (languageCode == null) {
                System.out.println("Invalid language selection. Try again.");
                continue;
            }

            // Perform the translation
            String translatedName = translator.translate(countryCode, languageCode);
            if (translatedName != null) {
                System.out.println(country + " in " + language + " is " + translatedName);
            }
            else {
                System.out.println("Translation not available for " + country + " in " + language);
            }

            System.out.println("Press enter to continue or type quit to exit.");
            Scanner s = new Scanner(System.in);
            String textTyped = s.nextLine();

            if (QUIT_COMMAND.equals(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator) {
        List<String> countryCodes = translator.getCountries();
        // Task: replace the following println call, sort the countries alphabetically,
        //            and print them out; one per line
        //      hint: class Collections provides a static sort method
        // Task: convert the country codes to the actual country names before sorting
        List<String> countryNames = new ArrayList<>();
        for (String code : countryCodes) {
            String countryName = COUNTRY_CODE_CONVERTER.fromCountryCode(code);
            if (countryName != null) {
                countryNames.add(countryName);
            }
        }

        // Sort the country names alphabetically
        Collections.sort(countryNames);

        System.out.println("Available countries:");
        for (String country : countryNames) {
            System.out.println(country);
        }

        System.out.println("Select a country from the above list or type 'quit' to exit:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String country) {
        List<String> languageCodes = translator.getCountryLanguages(country);
        // Task: replace the line below so that we sort the languages alphabetically and print them out; one per line
        // Task: convert the language codes to the actual language names before sorting
        List<String> languageNames = new ArrayList<>();
        for (String code : languageCodes) {
            String languageName = LANGUAGE_CODE_CONVERTER.fromLanguageCode(code);
            if (languageName != null) {
                languageNames.add(languageName);
            }
        }

        // Sort the language names alphabetically
        Collections.sort(languageNames);

        System.out.println("Available languages for " + country + ":");
        for (String language : languageNames) {
            System.out.println(language);
        }

        System.out.println("Select a language from the above list or type 'quit' to exit:");

        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }
}

