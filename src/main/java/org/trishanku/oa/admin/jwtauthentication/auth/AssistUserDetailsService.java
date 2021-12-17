package org.trishanku.oa.admin.jwtauthentication.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.trishanku.oa.admin.entity.TransactionStatusEnum;
import org.trishanku.oa.admin.entity.User;
import org.trishanku.oa.admin.repository.UserRepository;

@Service
public class AssistUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {

        User user= userRepository.findByEmailAddressAndTransactionStatus(emailAddress, TransactionStatusEnum.MASTER).orElseThrow(() -> new RuntimeException("user with the given email address not found"));
        return new AssistUserDetails(user);
    }


}
