package ua.dp.rename.dniprostreets.util;

public class TextUtils {

    /**
     * Give index of first occurence of subString in superString, all lowercased.
     * @param superString where to search
     * @param subString substring to search
     * @return index of first occurence, if any. Otherwise - returns {@link Integer#MAX_VALUE}
     */
    public static int substringLocation(String superString, String subString) {
        String superLowerCase = superString.toLowerCase();
        String subLowerCase = subString.toLowerCase();

        if (!superLowerCase.contains(subLowerCase)) return Integer.MAX_VALUE;

        return superLowerCase.indexOf(subLowerCase);
    }
}
