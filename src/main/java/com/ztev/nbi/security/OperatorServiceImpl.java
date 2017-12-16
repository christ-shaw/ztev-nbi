package com.ztev.nbi.security;

import com.ztev.nbi.security.repository.OperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Created by ${xiaozb} on 2017/11/23.
 *
 * @copyright by ztev
 */
@Service
public class OperatorServiceImpl implements UserDetailsService
{
    @Autowired
    private OperatorRepository repository;
    @Override
    public UserDetails loadUserByUsername(String operatorID) throws UsernameNotFoundException {
        RegistryInfo registryInfo = repository.findByOperatorId(operatorID);
        if (registryInfo == null)
        {
            throw new UsernameNotFoundException("unknown operator " + operatorID);
        }
        else
        {
            return new User(registryInfo.getOperatorId(),registryInfo.getSecretKey(), Collections.emptyList());
        }
    }
}
