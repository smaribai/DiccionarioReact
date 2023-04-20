package com.santi.dicccionario.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.santi.dicccionario.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClienteDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteDTO.class);
        ClienteDTO clienteDTO1 = new ClienteDTO();
        clienteDTO1.setIdCliente("id1");
        ClienteDTO clienteDTO2 = new ClienteDTO();
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO2.setIdCliente(clienteDTO1.getIdCliente());
        assertThat(clienteDTO1).isEqualTo(clienteDTO2);
        clienteDTO2.setIdCliente("id2");
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO1.setIdCliente(null);
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
    }
}
