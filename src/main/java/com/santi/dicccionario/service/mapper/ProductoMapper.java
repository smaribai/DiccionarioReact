package com.santi.dicccionario.service.mapper;

import com.santi.dicccionario.domain.Producto;
import com.santi.dicccionario.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {}
