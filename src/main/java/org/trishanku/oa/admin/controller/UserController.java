package org.trishanku.oa.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.model.UserDTO;
import org.trishanku.oa.admin.service.UserAdminService;

@RestController
@RequestMapping(path = "/api/v1")

public class UserController {


    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    UserAdminService userAdminService;

    //Mapping to get the details of logged-in user
    @GetMapping("/myprofile")
    @Transactional
    public ResponseEntity<UserDTO> getProfileDetails()
    {

        UserDTO userDTO = userAdminService.getProfileDetails(jwtUtil.extractUsernameFromRequest());
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    //Mapping to get the user details for other Micro services
    @GetMapping("/userDetails/{userEmailAddress}")
    @Transactional
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable (name = "userEmailAddress") String emailAddress)
    {

        UserDTO userDTO = userAdminService.getProfileDetails(emailAddress);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}
