package org.trishanku.oa.admin.mapper;

import org.mapstruct.Mapper;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.model.UserDTO;

import java.util.List;

@Mapper
public interface UserMapper {

    User UserDTOToUser(UserDTO userDTO);
    UserDTO UserToUserDTO(User user);
    List<User> UserDTOListToUserList(List<UserDTO> userDTOList);
    List<UserDTO> UserListToUserDTOList(List<User> userList);
}
