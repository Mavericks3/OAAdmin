package org.trishanku.oa.admin.jwtauthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.jwtauthentication.configuration.model.AuthenticationRequestDetails;
import org.trishanku.oa.admin.jwtauthentication.configuration.model.AuthenticationResponseDetails;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.RSAUtil;

@RestController
@Profile("JWT")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    RSAUtil rsaUtil;

    @Autowired
    UserDetailsService securityUserDetailsService;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthenticationResponseDetails> authenticate(@RequestBody AuthenticationRequestDetails authenticationRequestDetails) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequestDetails.getEmailAddress(), authenticationRequestDetails.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = securityUserDetailsService
                .loadUserByUsername(authenticationRequestDetails.getEmailAddress());

        final String jwt = jwtUtil.generateToken(userDetails,rsaUtil.getPrivateKey());

        return new ResponseEntity<>(new AuthenticationResponseDetails(jwt),HttpStatus.OK);
    }


}
