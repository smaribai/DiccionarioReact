package com.santi.dicccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoClienteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoClienteDTO.class);
        TipoClienteDTO tipoClienteDTO1 = new TipoClienteDTO();
        tipoClienteDTO1.setCodigo("id1");
        TipoClienteDTO tipoClienteDTO2 = new TipoClienteDTO();
        assertThat(tipoClienteDTO1).isNotEqualTo(tipoClienteDTO2);
        tipoClienteDTO2.setCodigo(tipoClienteDTO1.getCodigo());
        assertThat(tipoClienteDTO1).isEqualTo(tipoClienteDTO2);
        tipoClienteDTO2.setCodigo("id2");
        assertThat(tipoClienteDTO1).isNotEqualTo(tipoClienteDTO2);
        tipoClienteDTO1.setCodigo(null);
        assertThat(tipoClienteDTO1).isNotEqualTo(tipoClienteDTO2);
    }
}
