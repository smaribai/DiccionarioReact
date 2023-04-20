package com.santi.dicccionario.service.mapper;

import com.santi.dicccionario.domain.Pais;
import com.santi.dicccionario.service.dto.PaisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pais} and its DTO {@link PaisDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaisMapper extends EntityMapper<PaisDTO, Pais> {}
