package ua.dp.rename.dniprostreets.util;

import android.support.annotation.Nullable;

public class ValidationUtils {

    private ValidationUtils() {
    }

    public static boolean isPhotoTitleValid(String title) {
        return !title.trim().isEmpty();
    }


    public static class VResult {
        protected boolean valid;
        protected int messageRes;

        public VResult(boolean valid, int message) {
            this.valid = valid;
            this.messageRes = message;
        }

        public boolean isValid() {
            return valid;
        }

        public int getMessage() {
            return messageRes;
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new IllegalArgumentException("reference is null");
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference            an object reference
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *                             message is formed by replacing each {@code %s} placeholder in the template with an
     *                             argument. These are matched by position - the first {@code %s} gets {@code
     *                             errorMessageArgs[0]}, etc.  Unmatched arguments will be appended to the formatted message
     *                             in square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
     *                             are converted to strings using {@link String#valueOf(Object)}.
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    public static <T> T checkNotNull(T reference,
                                     @Nullable String errorMessageTemplate,
                                     @Nullable Object... errorMessageArgs) {
        if (reference == null) {
            // If either of these parameters is null, the right thing happens anyway
            throw new IllegalArgumentException(format(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    // Note that this is somewhat-improperly used from Verify.java as well.
    static String format(String temp, @Nullable Object... args) {
        String template = String.valueOf(temp); // null -> "null"

        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template.substring(templateStart, placeholderStart));
            builder.append(args[i]);
            i++;
            templateStart = placeholderStart + 2;
        }
        builder.append(template.substring(templateStart));

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i]);
            i++;
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i]);
                i++;
            }
            builder.append(']');
        }

        return builder.toString();
    }
}
