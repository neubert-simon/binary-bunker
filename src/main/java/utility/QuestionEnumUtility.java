package utility;

import enumerations.questions.I_DB_Filter;

public class QuestionEnumUtility {

    /**
     * Retrieves the corresponding filter from the provided array of filter types based on the given JSON key.
     * @param filterTypes an array of filter types to search through.
     * @param jsonKey the JSON key that corresponds to the filter's string representation.
     * @return the {@link I_DB_Filter} that matches the provided JSON key.
     * @throws IllegalArgumentException if no filter type matches the provided JSON key.
     */
    public static I_DB_Filter getFilterFromJSONKey(final I_DB_Filter[] filterTypes, final String jsonKey) {
        for(final I_DB_Filter filter : filterTypes) {
            if(filter.toString().equals(jsonKey)) return filter;
        }
        throw new IllegalArgumentException("Invalid FilterType.");
    }
}