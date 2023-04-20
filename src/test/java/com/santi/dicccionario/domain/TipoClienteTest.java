package com.santi.dicccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TipoClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoCliente.class);
        TipoCliente tipoCliente1 = new TipoCliente();
        tipoCliente1.setCodigo("id1");
        TipoCliente tipoCliente2 = new TipoCliente();
        tipoCliente2.setCodigo(tipoCliente1.getCodigo());
        assertThat(tipoCliente1).isEqualTo(tipoCliente2);
        tipoCliente2.setCodigo("id2");
        assertThat(tipoCliente1).isNotEqualTo(tipoCliente2);
        tipoCliente1.setCodigo(null);
        assertThat(tipoCliente1).isNotEqualTo(tipoCliente2);
    }
}
