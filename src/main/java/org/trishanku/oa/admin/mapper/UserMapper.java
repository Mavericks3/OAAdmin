package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.model.UserDTO;

import java.util.List;

@Mapper
public interface UserMapper {

    User userDTOToUser(UserDTO userDTO);
    UserDTO userToUserDTO(User user);
    List<User> userDTOListToUserList(List<UserDTO> userDTOList);
    List<UserDTO> userListToUserDTOList(List<User> userList);
}
