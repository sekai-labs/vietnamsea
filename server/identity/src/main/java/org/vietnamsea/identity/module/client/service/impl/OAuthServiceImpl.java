package org.vietnamsea.identity.module.client.service.impl;

import org.springframework.stereotype.Service;
import org.vietnamsea.identity.exception.ActionFailedException;
import org.vietnamsea.identity.exception.ValidationException;
import org.vietnamsea.identity.infra.persistence.client.repository.OAuthClientRepository;
import org.vietnamsea.identity.module.client.dto.request.OAuthClientRequest;
import org.vietnamsea.identity.module.client.dto.response.OAuthClientResponse;
import org.vietnamsea.identity.module.client.mapper.OAuthClientEntityMapper;
import org.vietnamsea.identity.module.client.service.OAuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuthServiceImpl implements OAuthService {
    private final OAuthClientRepository oAuthClientRepository;
    private final OAuthClientEntityMapper mapper;

    @Override
    public void createOAuthClient(OAuthClientRequest oauthClient) {
        var entity = mapper.toEntity(oauthClient);
        try {
            oAuthClientRepository.save(entity);
        } catch (Exception ex) {
            throw new ActionFailedException("fail to save oauth client", ex);
        }
    }

    @Override
    public OAuthClientResponse getOAuthClientById(Long id) {
        var entity = oAuthClientRepository.findById(id)
                .orElseThrow(() -> new ValidationException("not found oauth client with this id"));
        return mapper.toDto(entity);
    }
}
