package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // Task: pick appropriate instance variables for this class
    private final Map<String, Map<String, String>> translations;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        translations = new HashMap<>();
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObject = jsonArray.getJSONObject(i);

                String alpha3Code = countryObject.getString("alpha3").toLowerCase();

                Map<String, String> languageMap = new HashMap<>();

                for (String key : countryObject.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                        String translatedName = countryObject.getString(key);
                        languageMap.put(key, translatedName);
                    }
                }

                translations.put(alpha3Code, languageMap);
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        if (translations.containsKey(country.toLowerCase())) {
            return new ArrayList<>(translations.get(country.toLowerCase()).keySet());
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        // Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        return new ArrayList<>(translations.keySet());
    }

    @Override
    public String translate(String country, String language) {
        // Task: complete this method using your instance variables as needed
        if (translations.containsKey(country.toLowerCase())) {
            Map<String, String> languageMap = translations.get(country.toLowerCase());
            if (languageMap.containsKey(language.toLowerCase())) {
                return languageMap.get(language.toLowerCase());
            }
        }
        return null;
    }
}
