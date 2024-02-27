package eu.tasgroup.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
/*@EnableRedisRepositories(keyspaceConfiguration = RedisConfig.MyKeyspaceConfiguration.class, 
						 enableKeyspaceEvents = EnableKeyspaceEvents.ON_STARTUP, 
						 shadowCopy = ShadowCopy.OFF)*/
public class RedisConfig {
	
	@Value("${spring.data.redis.host}")
	private String host;
	@Value("${spring.data.redis.port}")
	private int port;
	
	
	@Bean
	LettuceConnectionFactory lettuceConnectionFactory() {
		
		RedisStandaloneConfiguration config=new RedisStandaloneConfiguration();
		config.setHostName(host);
		config.setPort(port);	
	    return new LettuceConnectionFactory(config);
	}

	@Bean
	public RedisTemplate<?, ?> redisTemplate() {
	    RedisTemplate<?, ?> template = new RedisTemplate<>();
	    template.setConnectionFactory(lettuceConnectionFactory());
	    return template;
	}
	
	/*public class MyKeyspaceConfiguration extends KeyspaceConfiguration {

		@Value("${keyspace.tech.ttl:60}")
		private Long ttl;

		@Override
		protected Iterable<KeyspaceSettings> initialConfiguration() {
			KeyspaceSettings keyspaceSettings = new KeyspaceSettings(TechMessageKey.class, "TechMessageKey");
			keyspaceSettings.setTimeToLive(ttl);
			return Collections.singleton(keyspaceSettings);
		}
	}*/
}
