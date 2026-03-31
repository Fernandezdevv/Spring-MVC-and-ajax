package com.example.demo.web.controller;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StringToInteger implements Converter <String, Integer>{

    @Nullable
    @Override
    public Integer convert(String text) {
        text = text.trim();

        if(text.matches("[0-9]+")){
            return Integer.valueOf(text);
        }
        return null;
    }
}
