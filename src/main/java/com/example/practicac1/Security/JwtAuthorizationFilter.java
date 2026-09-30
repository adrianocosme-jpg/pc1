package com.example.practicac1.Security;

import org.springframework.stereotype.Component;

import org.springframework.util.StringUtils;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthorizationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    public JwtAuthorizationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService) {

        this.jwtService = jwtService;
        this.userDetailsService =
                userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        if (StringUtils.hasText(authHeader)
                && StringUtils.startsWithIgnoreCase(
                authHeader,
                "Bearer ")) {

            String token =
                    authHeader.substring(7);

            if (jwtService.isTokenValid(token)) {

                String username =
                        jwtService.extractUsername(token);

                if (StringUtils.hasText(username)
                        && SecurityContextHolder
                        .getContext()
                        .getAuthentication() == null) {

                    UserDetails userDetails =
                            userDetailsService
                                    .loadUserByUsername(username);

                    SecurityContext context =
                            SecurityContextHolder
                                    .createEmptyContext();

                    UsernamePasswordAuthenticationToken
                            authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    context.setAuthentication(authToken);

                    SecurityContextHolder
                            .setContext(context);
                }
            }
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}