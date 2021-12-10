package org.trishanku.oa.admin.service;

import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.model.UserDTO;


public interface UserAdminService {

    UserDTO getProfileDetails(String userEmailAddress);
    User getUserDetails(String emailAddress);
}
