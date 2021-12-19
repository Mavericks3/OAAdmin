package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.model.RMDTO;
import org.trishanku.oa.admin.model.UserDTO;

import java.util.List;

public interface RMService {


    List<RMDTO> getAllRMUsers();

    RMDTO getRMUserById(String rmId);

    RMDTO addRMUser(RMDTO rmdto);

    RMDTO modifyRMUser(String userId, RMDTO rmDTO);

    RMDTO authoriseRMUser(String rmId);

    List<RMDTO> getPendingRMUsers();

    RMDTO deleteRMUser(String rmId);

    String getNewReference();
}
