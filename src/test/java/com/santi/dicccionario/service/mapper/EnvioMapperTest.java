package com.santi.dicccionario.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnvioMapperTest {

    private EnvioMapper envioMapper;

    @BeforeEach
    public void setUp() {
        envioMapper = new EnvioMapperImpl();
    }
}
