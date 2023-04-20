package com.santi.dicccionario.service.mapper;

import com.santi.dicccionario.domain.TipoCliente;
import com.santi.dicccionario.service.dto.TipoClienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoCliente} and its DTO {@link TipoClienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface TipoClienteMapper extends EntityMapper<TipoClienteDTO, TipoCliente> {}
