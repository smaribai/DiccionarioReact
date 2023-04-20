package com.santi.dicccionario.service.mapper;

import com.santi.dicccionario.domain.Cliente;
import com.santi.dicccionario.domain.Pais;
import com.santi.dicccionario.domain.TipoCliente;
import com.santi.dicccionario.service.dto.ClienteDTO;
import com.santi.dicccionario.service.dto.PaisDTO;
import com.santi.dicccionario.service.dto.TipoClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cliente} and its DTO {@link ClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClienteMapper extends EntityMapper<ClienteDTO, Cliente> {
    @Mapping(target = "tipoCliente", source = "tipoCliente", qualifiedByName = "tipoClienteCodigo")
    @Mapping(target = "pais", source = "pais", qualifiedByName = "paisCodigoPais")
    ClienteDTO toDto(Cliente s);

    @Named("tipoClienteCodigo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "codigo", source = "codigo")
    TipoClienteDTO toDtoTipoClienteCodigo(TipoCliente tipoCliente);

    @Named("paisCodigoPais")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "codigoPais", source = "codigoPais")
    PaisDTO toDtoPaisCodigoPais(Pais pais);
}
