package com.santi.dicccionario.service.mapper;

import com.santi.dicccionario.domain.Control;
import com.santi.dicccionario.domain.Producto;
import com.santi.dicccionario.domain.ProductoControles;
import com.santi.dicccionario.service.dto.ControlDTO;
import com.santi.dicccionario.service.dto.ProductoControlesDTO;
import com.santi.dicccionario.service.dto.ProductoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductoControles} and its DTO {@link ProductoControlesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductoControlesMapper extends EntityMapper<ProductoControlesDTO, ProductoControles> {
    @Mapping(target = "codigoArancelario", source = "codigoArancelario", qualifiedByName = "productoId")
    @Mapping(target = "idControl", source = "idControl", qualifiedByName = "controlId")
    ProductoControlesDTO toDto(ProductoControles s);

    @Named("productoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductoDTO toDtoProductoId(Producto producto);

    @Named("controlId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ControlDTO toDtoControlId(Control control);
}
