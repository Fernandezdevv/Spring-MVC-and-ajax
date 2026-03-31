package com.example.demo.web.controller;

import com.example.demo.domain.Cargo;
import com.example.demo.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StringToCargoConversor implements Converter<String, Cargo> {


    @Autowired
    private CargoService service;

    @Nullable
    @Override
    public Cargo convert(String text) {
        if(text.isEmpty()){
            return null;
        }
        Long id = Long.valueOf(text);
        return service.buscarPorId(id);

    }
}
