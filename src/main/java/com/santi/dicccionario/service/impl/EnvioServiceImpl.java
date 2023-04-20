package com.santi.dicccionario.service.impl;

import com.santi.dicccionario.domain.Envio;
import com.santi.dicccionario.repository.EnvioRepository;
import com.santi.dicccionario.service.EnvioService;
import com.santi.dicccionario.service.dto.EnvioDTO;
import com.santi.dicccionario.service.mapper.EnvioMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Envio}.
 */
@Service
@Transactional
public class EnvioServiceImpl implements EnvioService {

    private final Logger log = LoggerFactory.getLogger(EnvioServiceImpl.class);

    private final EnvioRepository envioRepository;

    private final EnvioMapper envioMapper;

    public EnvioServiceImpl(EnvioRepository envioRepository, EnvioMapper envioMapper) {
        this.envioRepository = envioRepository;
        this.envioMapper = envioMapper;
    }

    @Override
    public EnvioDTO save(EnvioDTO envioDTO) {
        log.debug("Request to save Envio : {}", envioDTO);
        Envio envio = envioMapper.toEntity(envioDTO);
        envio = envioRepository.save(envio);
        return envioMapper.toDto(envio);
    }

    @Override
    public EnvioDTO update(EnvioDTO envioDTO) {
        log.debug("Request to save Envio : {}", envioDTO);
        Envio envio = envioMapper.toEntity(envioDTO);
        envio = envioRepository.save(envio);
        return envioMapper.toDto(envio);
    }

    @Override
    public Optional<EnvioDTO> partialUpdate(EnvioDTO envioDTO) {
        log.debug("Request to partially update Envio : {}", envioDTO);

        return envioRepository
            .findById(envioDTO.getId())
            .map(existingEnvio -> {
                envioMapper.partialUpdate(existingEnvio, envioDTO);

                return existingEnvio;
            })
            .map(envioRepository::save)
            .map(envioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnvioDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Envios");
        return envioRepository.findAll(pageable).map(envioMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnvioDTO> findOne(Long id) {
        log.debug("Request to get Envio : {}", id);
        return envioRepository.findById(id).map(envioMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Envio : {}", id);
        envioRepository.deleteById(id);
    }
}
