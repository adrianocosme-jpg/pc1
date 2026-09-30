package com.example.practicac1.Security;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final AccountService userDetailsService;

    private final JwtAuthorizationFilter jwtFilter;

    public SecurityConfig(
            AccountService userDetailsService,
            JwtAuthorizationFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider
    authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(
                    passwordEncoder());
        return provider;
    }
    @Bean
    public AuthenticationManager
    authenticationManager() {
        return new ProviderManager(
                List.of(authenticationProvider())
        );
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {return http);

        }
    }
