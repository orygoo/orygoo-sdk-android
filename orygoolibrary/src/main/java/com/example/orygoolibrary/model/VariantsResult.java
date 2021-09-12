package com.example.orygoolibrary.model;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class VariantsResult {
    @SerializedName("variantName")
    private String variantName;
    @SerializedName("value")
    private Map<String, String> value;

    public VariantsResult(String variantName, Map<String, String> value) {
        this.variantName = variantName;
        this.value = value;
    }

    public VariantsResult() {
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public Map<String, String> getValue() {
        return value;
    }

    public void setValue(Map<String, String> value) {
        this.value = value;
    }

    public String getValue(String key, String defaultValue) {
        String result = defaultValue;

        if (value.containsKey(key))
            result = value.get(key);

        return result != null ? result : defaultValue;
    }
}
