package com.santi.dicccionario.service.mapper;

import com.santi.dicccionario.domain.Cliente;
import com.santi.dicccionario.domain.Divisa;
import com.santi.dicccionario.domain.Envio;
import com.santi.dicccionario.domain.Idioma;
import com.santi.dicccionario.domain.Pais;
import com.santi.dicccionario.service.dto.ClienteDTO;
import com.santi.dicccionario.service.dto.DivisaDTO;
import com.santi.dicccionario.service.dto.EnvioDTO;
import com.santi.dicccionario.service.dto.IdiomaDTO;
import com.santi.dicccionario.service.dto.PaisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Envio} and its DTO {@link EnvioDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnvioMapper extends EntityMapper<EnvioDTO, Envio> {
    @Mapping(target = "paisOrigen", source = "paisOrigen", qualifiedByName = "paisCodigoPais")
    @Mapping(target = "paisDestino", source = "paisDestino", qualifiedByName = "paisCodigoPais")
    @Mapping(target = "divisa", source = "divisa", qualifiedByName = "divisaCodigoDivisa")
    @Mapping(target = "idioma", source = "idioma", qualifiedByName = "idiomaCodigo")
    @Mapping(target = "refCliente", source = "refCliente", qualifiedByName = "clienteIdCliente")
    EnvioDTO toDto(Envio s);

    @Named("paisCodigoPais")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "codigoPais", source = "codigoPais")
    PaisDTO toDtoPaisCodigoPais(Pais pais);

    @Named("divisaCodigoDivisa")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "codigoDivisa", source = "codigoDivisa")
    DivisaDTO toDtoDivisaCodigoDivisa(Divisa divisa);

    @Named("idiomaCodigo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "codigo", source = "codigo")
    IdiomaDTO toDtoIdiomaCodigo(Idioma idioma);

    @Named("clienteIdCliente")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idCliente", source = "idCliente")
    ClienteDTO toDtoClienteIdCliente(Cliente cliente);
}
