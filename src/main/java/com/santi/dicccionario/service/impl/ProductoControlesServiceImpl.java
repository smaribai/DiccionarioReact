package com.santi.dicccionario.service.impl;

import com.santi.dicccionario.domain.ProductoControles;
import com.santi.dicccionario.repository.ProductoControlesRepository;
import com.santi.dicccionario.service.ProductoControlesService;
import com.santi.dicccionario.service.dto.ProductoControlesDTO;
import com.santi.dicccionario.service.mapper.ProductoControlesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductoControles}.
 */
@Service
@Transactional
public class ProductoControlesServiceImpl implements ProductoControlesService {

    private final Logger log = LoggerFactory.getLogger(ProductoControlesServiceImpl.class);

    private final ProductoControlesRepository productoControlesRepository;

    private final ProductoControlesMapper productoControlesMapper;

    public ProductoControlesServiceImpl(
        ProductoControlesRepository productoControlesRepository,
        ProductoControlesMapper productoControlesMapper
    ) {
        this.productoControlesRepository = productoControlesRepository;
        this.productoControlesMapper = productoControlesMapper;
    }

    @Override
    public ProductoControlesDTO save(ProductoControlesDTO productoControlesDTO) {
        log.debug("Request to save ProductoControles : {}", productoControlesDTO);
        ProductoControles productoControles = productoControlesMapper.toEntity(productoControlesDTO);
        productoControles = productoControlesRepository.save(productoControles);
        return productoControlesMapper.toDto(productoControles);
    }

    @Override
    public ProductoControlesDTO update(ProductoControlesDTO productoControlesDTO) {
        log.debug("Request to save ProductoControles : {}", productoControlesDTO);
        ProductoControles productoControles = productoControlesMapper.toEntity(productoControlesDTO);
        productoControles = productoControlesRepository.save(productoControles);
        return productoControlesMapper.toDto(productoControles);
    }

    @Override
    public Optional<ProductoControlesDTO> partialUpdate(ProductoControlesDTO productoControlesDTO) {
        log.debug("Request to partially update ProductoControles : {}", productoControlesDTO);

        return productoControlesRepository
            .findById(productoControlesDTO.getId())
            .map(existingProductoControles -> {
                productoControlesMapper.partialUpdate(existingProductoControles, productoControlesDTO);

                return existingProductoControles;
            })
            .map(productoControlesRepository::save)
            .map(productoControlesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductoControlesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProductoControles");
        return productoControlesRepository.findAll(pageable).map(productoControlesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductoControlesDTO> findOne(Long id) {
        log.debug("Request to get ProductoControles : {}", id);
        return productoControlesRepository.findById(id).map(productoControlesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductoControles : {}", id);
        productoControlesRepository.deleteById(id);
    }
}
