package com.wrvpark.apiserver.configuration;

import com.google.common.collect.ImmutableList;
import com.wrvpark.apiserver.util.ConstantUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Vahid Haghighat
 * Description: Holds the settings for Spring Security.
 */
public class SecurityConfig {
    @Configuration
    @EnableWebSecurity
    public static class Anonymous extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().configurationSource(request -> {
                CorsConfiguration cors = new CorsConfiguration();
                cors.setAllowedOrigins(ImmutableList.of("*"));
                cors.setAllowedMethods(ImmutableList.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                cors.setAllowedHeaders(ImmutableList.of("*"));
                return cors;
            });
            http.csrf().disable().authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/user/**").anonymous()
                    .antMatchers(HttpMethod.GET, "/events/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/documents/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/categories/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/forum/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/salerent/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/services/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/lostfound/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/pictures/**").permitAll()
                    .antMatchers("/files/**").permitAll()
                    .antMatchers("/log/**").hasRole(ConstantUtil.ROLE_ADMIN)
                    .antMatchers(HttpMethod.GET, "/" + ConstantUtil.UPLOAD_FOLDER_NAME + "/**").permitAll()
                    .antMatchers( "/pictures/**").hasRole(ConstantUtil.ROLE_ADMIN)//only admin can add/delete website pictures
                    .antMatchers( "/documents/**").hasAnyRole(ConstantUtil.MANAGEMENT_ROLE)//only admin, park management, board members can add. update, delete park documents
                    .antMatchers( "/categories/document/**").hasAnyRole(ConstantUtil.MANAGEMENT_ROLE)//only admin, park management, board members can add & delete park document sub-categories
                    .antMatchers( "/categories/other/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)//all registered user can add non-park document sub-categories
                    //all registered user can access the following
                    .antMatchers( HttpMethod.POST, "/forum/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.PUT, "/forum/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.DELETE, "/forum/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.POST,"/events/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.PUT,"/events/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.POST,"/lostfound/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.PUT,"/lostfound/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.POST,"/salerent/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.PUT,"/salerent/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.POST,"/services/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    .antMatchers( HttpMethod.PUT,"/services/**").not().hasAnyRole(ConstantUtil.PUBLIC_ROLES)
                    //Admin Section
                    .antMatchers("/admin/**").hasRole(ConstantUtil.ROLE_ADMIN)
                    .anyRequest().authenticated()
                    .and()
                    .oauth2ResourceServer().jwt(
                            token -> token.jwtAuthenticationConverter(jwtAuthenticationConverter()));
        }

        private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter() {
            JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
            jwtConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
            return jwtConverter;
        }
    }

    public static class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
        @Override
        public Collection<GrantedAuthority> convert(Jwt jwt) {
            final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
            return ((List<String>)realmAccess.get("roles")).stream()
                    .map(roleName -> "ROLE_" + roleName) // prefix to map to a Spring Security "role"
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }
    }
}