package com.santi.dicccionario.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = new Cliente();
        cliente1.setIdCliente("id1");
        Cliente cliente2 = new Cliente();
        cliente2.setIdCliente(cliente1.getIdCliente());
        assertThat(cliente1).isEqualTo(cliente2);
        cliente2.setIdCliente("id2");
        assertThat(cliente1).isNotEqualTo(cliente2);
        cliente1.setIdCliente(null);
        assertThat(cliente1).isNotEqualTo(cliente2);
    }
}
