package com.santi.dicccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pais.class);
        Pais pais1 = new Pais();
        pais1.setCodigoPais("id1");
        Pais pais2 = new Pais();
        pais2.setCodigoPais(pais1.getCodigoPais());
        assertThat(pais1).isEqualTo(pais2);
        pais2.setCodigoPais("id2");
        assertThat(pais1).isNotEqualTo(pais2);
        pais1.setCodigoPais(null);
        assertThat(pais1).isNotEqualTo(pais2);
    }
}
