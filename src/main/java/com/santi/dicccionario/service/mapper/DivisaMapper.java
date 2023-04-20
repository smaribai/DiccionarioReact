package com.santi.dicccionario.service.mapper;

import com.santi.dicccionario.domain.Divisa;
import com.santi.dicccionario.service.dto.DivisaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Divisa} and its DTO {@link DivisaDTO}.
 */
@Mapper(componentModel = "spring")
public interface DivisaMapper extends EntityMapper<DivisaDTO, Divisa> {}
