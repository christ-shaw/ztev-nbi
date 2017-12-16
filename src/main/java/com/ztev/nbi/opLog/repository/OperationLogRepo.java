package com.ztev.nbi.opLog.repository;

import com.ztev.nbi.opLog.model.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ${xiaozb} on 2017/11/28.
 *
 * @copyright by ztev
 */
@Repository
public interface OperationLogRepo extends JpaRepository<OperationLog, Long> {
     List<OperationLog> findAllByRemoteAddress(String remoteAddress);
     List<OperationLog> findAllByMethod(String methodName);
     List<OperationLog> findAllByRemoteAddressAndMethod(String remoteAddress,String method);
}
