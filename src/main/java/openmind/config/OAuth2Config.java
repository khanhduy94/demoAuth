package openmind.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Collection;

@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenEnv tokenEnv;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public JwtAccessTokenConverter tokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(tokenEnv.getPrivateKey());
        converter.setVerifierKey(tokenEnv.getPublicKey());
        return converter;
    }

    @Bean
    public JwtTokenStore tokenStore() {
        Collection<JwtAccessTokenConverter> tokenEnhancers = applicationContext.getBeansOfType(JwtAccessTokenConverter.class).values();
        return new JwtTokenStore(tokenEnhancers.iterator().next());
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        Collection<JwtAccessTokenConverter> tokenEnhancers = applicationContext.getBeansOfType(JwtAccessTokenConverter.class).values();
        Collection<JwtTokenStore> tokenStore = applicationContext.getBeansOfType(JwtTokenStore.class).values();

        endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore.iterator().next())
                .accessTokenConverter(tokenEnhancers.iterator().next());
    }
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(tokenEnv.getClientId()).secret(tokenEnv.getClientSecret()).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);

    }
}
