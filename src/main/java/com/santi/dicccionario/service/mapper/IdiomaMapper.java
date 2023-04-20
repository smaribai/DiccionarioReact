package com.santi.dicccionario.service.mapper;

import com.santi.dicccionario.domain.Idioma;
import com.santi.dicccionario.service.dto.IdiomaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Idioma} and its DTO {@link IdiomaDTO}.
 */
@Mapper(componentModel = "spring")
public interface IdiomaMapper extends EntityMapper<IdiomaDTO, Idioma> {}
