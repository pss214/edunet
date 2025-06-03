package fiveguys.edunet.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configurable
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

    @Override
	public void addCorsMappings(CorsRegistry registry) {
		WebMvcConfigurer.super.addCorsMappings(registry);
		registry.addMapping("/**").allowedOrigins("*");
	}
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler(
	                                 "/static/css/**",
                                     "/static/image/**",
	                                "/static/js/**")
	             .addResourceLocations("classpath:/static/image/",
	                                   "classpath:/static/css/",
	                                   "classpath:/static/js/");
	}
}
