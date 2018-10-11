package nz.co.hamptonsoftware.employee.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		/* Setup some resources for swagger */
	    registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");

	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		List<MediaType> jsonMediaTypes = new ArrayList<MediaType>();
		List<MediaType> xmlMediaTypes = new ArrayList<MediaType>();
		jsonMediaTypes.add(MediaType.APPLICATION_JSON);
		xmlMediaTypes.add(MediaType.APPLICATION_XML);
		xmlMediaTypes.add(MediaType.TEXT_XML);
    	MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
    	MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
    	jsonConverter.setSupportedMediaTypes(jsonMediaTypes);
		converters.add(jsonConverter);
		converters.add(xmlConverter);
    	
    }
	
}
