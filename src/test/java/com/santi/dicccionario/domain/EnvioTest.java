package com.santi.dicccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnvioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Envio.class);
        Envio envio1 = new Envio();
        envio1.setId(1L);
        Envio envio2 = new Envio();
        envio2.setId(envio1.getId());
        assertThat(envio1).isEqualTo(envio2);
        envio2.setId(2L);
        assertThat(envio1).isNotEqualTo(envio2);
        envio1.setId(null);
        assertThat(envio1).isNotEqualTo(envio2);
    }
}
