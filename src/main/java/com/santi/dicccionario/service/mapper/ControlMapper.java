package com.santi.dicccionario.service.mapper;

import com.santi.dicccionario.domain.Control;
import com.santi.dicccionario.service.dto.ControlDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Control} and its DTO {@link ControlDTO}.
 */
@Mapper(componentModel = "spring")
public interface ControlMapper extends EntityMapper<ControlDTO, Control> {}
