package com.santi.dicccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IdiomaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IdiomaDTO.class);
        IdiomaDTO idiomaDTO1 = new IdiomaDTO();
        idiomaDTO1.setCodigo("id1");
        IdiomaDTO idiomaDTO2 = new IdiomaDTO();
        assertThat(idiomaDTO1).isNotEqualTo(idiomaDTO2);
        idiomaDTO2.setCodigo(idiomaDTO1.getCodigo());
        assertThat(idiomaDTO1).isEqualTo(idiomaDTO2);
        idiomaDTO2.setCodigo("id2");
        assertThat(idiomaDTO1).isNotEqualTo(idiomaDTO2);
        idiomaDTO1.setCodigo(null);
        assertThat(idiomaDTO1).isNotEqualTo(idiomaDTO2);
    }
}
