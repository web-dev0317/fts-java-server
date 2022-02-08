package io.berbotworks.mc.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.berbotworks.mc.security.JWT;
import io.berbotworks.mc.security.MCUserDetails;
import lombok.extern.slf4j.Slf4j;

import static io.berbotworks.mc.security.filters.SecurityConstants.*;

@Slf4j
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Inside JwtTokenGen {}", authentication);
        if (authentication != null) {
            MCUserDetails userDetails = (MCUserDetails) authentication.getPrincipal();
            log.debug("uid {}", userDetails.getUid());
            request.setAttribute("uid", userDetails.getUid());
            request.setAttribute("roomId", userDetails.getRoomId());
            String jwt = JWT.generateToken(authentication, userDetails);
            response.setHeader(JWT_HEADER, "Bearer " + jwt);

        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/login");
    }

}
