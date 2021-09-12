package com.example.orygoolibrary;

import com.example.orygoolibrary.model.VariantsResult;

import java.util.Map;

public class OrygooVariantsResult {
    private Map<String, VariantsResult> variantsResultMap;

    public OrygooVariantsResult(Map<String, VariantsResult> variantsResultMap) {
        this.variantsResultMap = variantsResultMap;
    }

    public OrygooVariantsResult() {
    }

    public Map<String, VariantsResult> getVariantsResultMap() {
        return variantsResultMap;
    }

    public void setVariantsResultMap(Map<String, VariantsResult> variantsResultMap) {
        this.variantsResultMap = variantsResultMap;
    }

    public String getValue(String namespace, String key, String defaultValue) {
        String result = defaultValue;

        if (variantsResultMap.containsKey(namespace)) {
            VariantsResult variantsResult = variantsResultMap.get(namespace);
            result = variantsResult != null ? variantsResult.getValue(key, defaultValue): defaultValue;
        }

        return result;
    }
}
