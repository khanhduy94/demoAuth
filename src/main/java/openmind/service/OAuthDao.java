package openmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class OAuthDao {

    @Autowired
    private UserMapper userMapper;

    public UserEntity getUserDetails(String username) {
        UserEntity user = userMapper.findByUsername(username);
        if (user != null) {
            Collection<GrantedAuthority> grantedAuthoritiesList = new ArrayList<>();
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_SYSTEMADMIN");
            grantedAuthoritiesList.add(grantedAuthority);
            user.setGrantedAuthoritiesList(grantedAuthoritiesList);
            return user;
        }

        return null;
    }
}
