package org.trishanku.oa.admin.jwtauthentication.filter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.trishanku.oa.admin.jwtauthentication.auth.AssistUserDetails;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.JWTUtil;
import org.trishanku.oa.admin.jwtauthentication.configuration.service.RSAUtil;
import org.trishanku.oa.admin.mapper.UserMapper;
import org.trishanku.oa.admin.model.RoleDTO;
import org.trishanku.oa.admin.model.UserDTO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Profile("JWT")
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService securityUserDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    @Autowired
    RSAUtil rsaUtil;
    @Autowired
    UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        String roles = null;
        PublicKey publicKey = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                publicKey = rsaUtil.getPublicKey();
                username = jwtUtil.extractUsername(jwt, publicKey);
                roles = jwtUtil.extractClaim(jwt,"roles", publicKey);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

//            // generating the user object from JWT
//            List<RoleDTO> rolesDtoList = new ArrayList<RoleDTO>();
//            Arrays.stream(roles.split(",")).forEach(role ->{rolesDtoList.add(new RoleDTO(UUID.randomUUID(),role));});
//            UserDTO userDTO = UserDTO.builder().emailAddress(username).roles(rolesDtoList).build();
//            UserDetails userDetails = new AssistUserDetails(userMapper.userDTOToUser(userDTO));

            UserDetails userDetails = this.securityUserDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails, publicKey)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}
