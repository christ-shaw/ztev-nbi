package com.ztev.nbi.security.repository;

import com.ztev.nbi.security.RegistryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ${xiaozb} on 2017/11/24.
 *
 * @copyright by ztev
 */
@Repository
public interface OperatorRepository extends JpaRepository<RegistryInfo, Long> {
    public  RegistryInfo findByOperatorId(String username);
}
