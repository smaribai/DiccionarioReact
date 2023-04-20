package com.santi.dicccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DivisaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DivisaDTO.class);
        DivisaDTO divisaDTO1 = new DivisaDTO();
        divisaDTO1.setCodigoDivisa("id1");
        DivisaDTO divisaDTO2 = new DivisaDTO();
        assertThat(divisaDTO1).isNotEqualTo(divisaDTO2);
        divisaDTO2.setCodigoDivisa(divisaDTO1.getCodigoDivisa());
        assertThat(divisaDTO1).isEqualTo(divisaDTO2);
        divisaDTO2.setCodigoDivisa("id2");
        assertThat(divisaDTO1).isNotEqualTo(divisaDTO2);
        divisaDTO1.setCodigoDivisa(null);
        assertThat(divisaDTO1).isNotEqualTo(divisaDTO2);
    }
}
