package com.santi.dicccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IdiomaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Idioma.class);
        Idioma idioma1 = new Idioma();
        idioma1.setCodigo("id1");
        Idioma idioma2 = new Idioma();
        idioma2.setCodigo(idioma1.getCodigo());
        assertThat(idioma1).isEqualTo(idioma2);
        idioma2.setCodigo("id2");
        assertThat(idioma1).isNotEqualTo(idioma2);
        idioma1.setCodigo(null);
        assertThat(idioma1).isNotEqualTo(idioma2);
    }
}
