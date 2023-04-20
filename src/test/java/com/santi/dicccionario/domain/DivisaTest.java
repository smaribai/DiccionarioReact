package com.santi.dicccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DivisaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Divisa.class);
        Divisa divisa1 = new Divisa();
        divisa1.setCodigoDivisa("id1");
        Divisa divisa2 = new Divisa();
        divisa2.setCodigoDivisa(divisa1.getCodigoDivisa());
        assertThat(divisa1).isEqualTo(divisa2);
        divisa2.setCodigoDivisa("id2");
        assertThat(divisa1).isNotEqualTo(divisa2);
        divisa1.setCodigoDivisa(null);
        assertThat(divisa1).isNotEqualTo(divisa2);
    }
}
