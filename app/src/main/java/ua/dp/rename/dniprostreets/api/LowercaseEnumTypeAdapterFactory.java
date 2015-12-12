package ua.dp.rename.dniprostreets.api;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LowercaseEnumTypeAdapterFactory implements TypeAdapterFactory {

    private String fallbackKey;

    public LowercaseEnumTypeAdapterFactory() {
    }

    public LowercaseEnumTypeAdapterFactory(String fallbackKey) {
        this.fallbackKey = LowercaseEnumTypeAdapter.toLowercase(fallbackKey);
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }

        final Map<String, T> lowerToEnum = new HashMap<String, T>();
        for (T constant : rawType.getEnumConstants()) {
            lowerToEnum.put(LowercaseEnumTypeAdapter.toLowercase(constant), constant);
        }

        return createTypeAdapter(lowerToEnum);
    }

    @NonNull
    protected <T> TypeAdapter<T> createTypeAdapter(Map<String, T> lowerToEnum) {
        return new LowercaseEnumTypeAdapter<T>(lowerToEnum, fallbackKey);
    }

    public static class LowercaseEnumTypeAdapter<T> extends TypeAdapter<T> {

        protected final Map<String, T> lowerToEnum;
        protected final String fallbackKey;
        protected String serviceStringVariable;

        public LowercaseEnumTypeAdapter(Map<String, T> lowerToEnum, String fallbackKey) {
            this.lowerToEnum = lowerToEnum;
            this.fallbackKey = fallbackKey;
        }

        @Override
        public void write(JsonWriter out, T value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(toLowercase(value));
            }
        }

        @Override
        public T read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            } else {
                serviceStringVariable = reader.nextString();
                T t = lowerToEnum.get(serviceStringVariable);
                if (t == null) Log.i("###", serviceStringVariable + " -> unknown");
                return t != null ? t : lowerToEnum.get(fallbackKey);
            }
        }

        private static String toLowercase(Object o) {
            return o.toString().toLowerCase(Locale.US);
        }

    }
}
