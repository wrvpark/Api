package com.wrvpark.apiserver.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * @author Vahid Haghighat
 */
public class SecurityUtil {

    public static boolean hasRole(Authentication auth, String role) {
        role = "ROLE_" + role;
        for(GrantedAuthority authority : auth.getAuthorities()) {
            if (role.equals(authority.getAuthority()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * check if the user has one of the roles
     * @param auth access token
     * @param roles a list of roles
     * @return
     */
    public static boolean checkRoles(Authentication auth, List<String> roles) {
       boolean hasRoles=false;
        for (int i = 0; i < roles.size(); i++) {
            boolean check=hasRole(auth,roles.get(i));;
            hasRoles=(hasRoles||check);
        }
        return hasRoles;
    }
}
