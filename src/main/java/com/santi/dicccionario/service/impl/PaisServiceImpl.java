package com.santi.dicccionario.service.impl;

import com.santi.dicccionario.domain.Pais;
import com.santi.dicccionario.repository.PaisRepository;
import com.santi.dicccionario.service.PaisService;
import com.santi.dicccionario.service.dto.PaisDTO;
import com.santi.dicccionario.service.mapper.PaisMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pais}.
 */
@Service
@Transactional
public class PaisServiceImpl implements PaisService {

    private final Logger log = LoggerFactory.getLogger(PaisServiceImpl.class);

    private final PaisRepository paisRepository;

    private final PaisMapper paisMapper;

    public PaisServiceImpl(PaisRepository paisRepository, PaisMapper paisMapper) {
        this.paisRepository = paisRepository;
        this.paisMapper = paisMapper;
    }

    @Override
    public PaisDTO save(PaisDTO paisDTO) {
        log.debug("Request to save Pais : {}", paisDTO);
        Pais pais = paisMapper.toEntity(paisDTO);
        pais = paisRepository.save(pais);
        return paisMapper.toDto(pais);
    }

    @Override
    public PaisDTO update(PaisDTO paisDTO) {
        log.debug("Request to save Pais : {}", paisDTO);
        Pais pais = paisMapper.toEntity(paisDTO);
        pais.setIsPersisted();
        pais = paisRepository.save(pais);
        return paisMapper.toDto(pais);
    }

    @Override
    public Optional<PaisDTO> partialUpdate(PaisDTO paisDTO) {
        log.debug("Request to partially update Pais : {}", paisDTO);

        return paisRepository
            .findById(paisDTO.getCodigoPais())
            .map(existingPais -> {
                paisMapper.partialUpdate(existingPais, paisDTO);

                return existingPais;
            })
            .map(paisRepository::save)
            .map(paisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PaisDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pais");
        return paisRepository.findAll(pageable).map(paisMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaisDTO> findOne(String id) {
        log.debug("Request to get Pais : {}", id);
        return paisRepository.findById(id).map(paisMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Pais : {}", id);
        paisRepository.deleteById(id);
    }
}
