package com.example.springapi.auth.jwt.filter;

import com.example.springapi.auth.jwt.service.JwtService;
import com.example.springapi.auth.principal.UserPrincipal;
import com.example.springapi.user.models.User;
import com.example.springapi.user.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private final JwtService jwtService;
    private final UserService userService;

   /* public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService)
    {
        this.jwtService = jwtService;
        this.userDetailsService= userDetailsService;

    }*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            filterChain.doFilter(request,response);
            return;
        }

        String token = authHeader.substring(7);

        try
        {
            Claims claims = jwtService.extractAllClaims(token);

            String email = claims.getSubject();
            String username = claims.get("username", String.class);
            String role = claims.get("role", String.class);

            Number idNumber = claims.get("userId",Number.class);
            Long userId = idNumber.longValue();

            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                User user = userService.getUserById(userId);
                if(user!= null && jwtService.isTokenValid(token, user))
                {
                    UserPrincipal userPrincipal = new UserPrincipal(
                            userId,
                            email,
                            username
                    );
                    List<SimpleGrantedAuthority> authorities = List.of(
                            new SimpleGrantedAuthority("ROLE_" + role)
                    );
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userPrincipal,
                                    null,
                                    authorities
                            );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    log.info("JWT valid for userId = {}", userId);
                    log.info("ROLE FROM TOKEN = {}", role);
                }

            }
        }
        catch(Exception e)
        {
            log.warn("JWT processing failed: {}", e.getMessage());

        }


        filterChain.doFilter(request, response);
    }
}
