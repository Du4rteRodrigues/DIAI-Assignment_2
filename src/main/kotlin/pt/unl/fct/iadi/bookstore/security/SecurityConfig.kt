package pt.unl.fct.iadi.bookstore.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.http.HttpMethod

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val apiTokenFilter: ApiTokenFilter,
    private val requestLoggingFilter: RequestLoggingFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {

                it.requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()

                // Public GET endpoints
                it.requestMatchers(HttpMethod.GET, "/books/**").permitAll()

                // Only EDITOR can create books
                it.requestMatchers(HttpMethod.POST, "/books")
                    .hasRole("EDITOR")

                // Everything else requires auth
                it.anyRequest().authenticated()
            }
            .httpBasic { }

        http.addFilterBefore(
            apiTokenFilter,
            org.springframework.security.web.authentication
                .UsernamePasswordAuthenticationFilter::class.java
        )

        http.addFilterAfter(requestLoggingFilter, ApiTokenFilter::class.java)

        return http.build()
    }

    @Bean
    fun users(passwordEncoder: PasswordEncoder): UserDetailsService {
        val editor1 = User.withUsername("editor1")
            .password(passwordEncoder.encode("editor1pass"))
            .roles("EDITOR")
            .build()

        val editor2 = User.withUsername("editor2")
            .password(passwordEncoder.encode("editor2pass"))
            .roles("EDITOR")
            .build()

        val admin = User.withUsername("admin")
            .password(passwordEncoder.encode("adminpass"))
            .roles("ADMIN")
            .build()

        return InMemoryUserDetailsManager(editor1, editor2, admin)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}