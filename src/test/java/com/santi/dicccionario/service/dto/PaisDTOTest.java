package com.santi.dicccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaisDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaisDTO.class);
        PaisDTO paisDTO1 = new PaisDTO();
        paisDTO1.setCodigoPais("id1");
        PaisDTO paisDTO2 = new PaisDTO();
        assertThat(paisDTO1).isNotEqualTo(paisDTO2);
        paisDTO2.setCodigoPais(paisDTO1.getCodigoPais());
        assertThat(paisDTO1).isEqualTo(paisDTO2);
        paisDTO2.setCodigoPais("id2");
        assertThat(paisDTO1).isNotEqualTo(paisDTO2);
        paisDTO1.setCodigoPais(null);
        assertThat(paisDTO1).isNotEqualTo(paisDTO2);
    }
}
