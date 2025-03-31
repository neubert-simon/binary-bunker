package utility;

import enumerations.questions.FilterTypes;
import enumerations.questions.I_DB_Filter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class WebControllerUtility {

    /**
     * Validates if all keys in the provided set match any of the valid filter types.
     * @param keySet a set of filter keys to be validated.
     * @throws IllegalArgumentException if any key does not match a valid filter type.
     */
    public static void validateQuizKey(final Set<String> keySet) {
        for (final String key : keySet) {
            if (!Arrays.stream(FilterTypes.values())
                    .map(Enum::name)
                    .map(String::toLowerCase)
                    .toList()
                    .contains(key.toLowerCase())) {
                throw new IllegalArgumentException("Invalid Filter Keys passed in with JSON: " + key);
            }
        }
    }

    /**
     * Processes the provided JSON body and applies filters corresponding to the given key.
     * This method iterates through all available filter types and attempts to match each one with the provided key.
     * @param jsonBody the JSON body containing filter data.
     * @param key the key used to identify which filters to apply.
     * @param filters a list where the matched filters will be added.
     */
    public static void getFilterFromKey(final Map<String, String> jsonBody, final String key, final List<I_DB_Filter> filters) {
        for (final FilterTypes filterType : FilterTypes.values()) {
            getFilterClass(jsonBody, key, filters, filterType);
        }
    }

    /**
     * Fetches the corresponding filter class based on the filter type and key.
     * If the filter type's name matches the provided key, it adds the appropriate filters to the provided list.
     * @param jsonBody the JSON body containing the filter data.
     * @param key the key that is matched with the filter type name.
     * @param filters a list where the matched filters will be added.
     * @param filterType the filter type to be checked.
     */
    @SuppressWarnings("unchecked")
    private static void getFilterClass(final Map<String, String> jsonBody, final String key, List<I_DB_Filter> filters, final FilterTypes filterType) {
        if (!filterType.name().equalsIgnoreCase(key)) return;
        final Class<? extends Enum<? extends I_DB_Filter>> enumClass = (Class<? extends Enum<? extends I_DB_Filter>>) filterType.filterClass;
        for (final Enum<?> constant : enumClass.getEnumConstants()) {
            addFilter(jsonBody, key, constant, filters);
        }
    }

    /**
     * Adds the filter to the list if the name of the filter constant matches the value in the JSON body.
     * @param jsonBody the JSON body containing the filter data.
     * @param key the key used to identify the filter.
     * @param constant the filter constant to be added.
     * @param filters a list where the matched filter will be added.
     */
    private static void addFilter(final Map<String, String> jsonBody, final String key, final Enum<?> constant, final List<I_DB_Filter> filters) {
        final I_DB_Filter filter = (I_DB_Filter) constant;
        if (constant.name().equalsIgnoreCase(jsonBody.get(key))) {
            filters.add(filter);
        }
    }

    /**
     * Formats an Enum Class's name and returns it
     * @param enumClass Class whose name is to be returned
     * @return The name without package prefix
     */
    public static String formatEnumClassName(final Class<? extends Enum<? extends I_DB_Filter>> enumClass) {
        final String name = enumClass.toString();
        return name.substring(name.lastIndexOf(".") + 1);
    }

    /**
     * Get all filters from an Filter enum
     * @param enumClass I_DB_Filter type enum
     * @return List of I_DB_Filter filter
     */
    public static List<I_DB_Filter> getCategoriesFromDBFilter(final Class<? extends Enum<? extends I_DB_Filter>> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .map(enumConstant -> (I_DB_Filter) enumConstant)
                .collect(Collectors.toList());
    }
}
