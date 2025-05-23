package com.mbi_re.airport_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Web MVC configuration class.
 * <p>
 * Configures the tenant interceptor to apply to API endpoints,
 * and sets up Cross-Origin Resource Sharing (CORS) policies
 * to allow specified frontend origins, HTTP methods, headers, and credentials.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private TenantInterceptor tenantInterceptor;

    /**
     * Registers the {@link TenantInterceptor} to intercept all requests
     * matching the pattern "/api/**".
     *
     * @param registry the interceptor registry used to add interceptors
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor).addPathPatterns("/api/**");
    }

    /**
     * Configures CORS mappings to allow cross-origin requests from specified frontend origins.
     * <p>
     * Allows GET, POST, PUT, DELETE, and OPTIONS methods.
     * Allows all headers and supports credentials such as cookies or authorization headers.
     *
     * @param registry the CORS registry to configure mappings
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5179", "http://localhost:5173") // Allow your frontend origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*") // Allow any headers
                .allowCredentials(true); // Allow credentials if you are using cookies or authorization headers
    }
}
