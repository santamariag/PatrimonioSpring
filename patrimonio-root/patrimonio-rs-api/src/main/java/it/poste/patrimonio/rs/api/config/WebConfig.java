package it.poste.patrimonio.rs.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	

	@Autowired
	private ObjectMapper mapper;
	
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        
        mapper.setSerializationInclusion(Include.NON_NULL);
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(mapper);
        converters.add(jsonConverter);
    }
}