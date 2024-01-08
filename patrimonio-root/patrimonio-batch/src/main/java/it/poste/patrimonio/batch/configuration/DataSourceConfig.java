package it.poste.patrimonio.batch.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Autowired
	private Environment environment;


	@Bean(name = "postgresDataSource")
	public DataSource postgresDataSource() {
		return DataSourceBuilder.create().driverClassName(environment.getProperty("spring.datasource.driverClassName"))
				.url(environment.getProperty("spring.datasource.url"))
				.username(environment.getProperty("spring.datasource.username"))
				.password(environment.getProperty("spring.datasource.password")).build();
	}

	@Bean
	public PlatformTransactionManager postgresDataSourceTransactionManager() {
		return new DataSourceTransactionManager(postgresDataSource());
	}

	
	@Primary
	@Bean(name = "platformTransactionManager")
	public PlatformTransactionManager platformTransactionManager() {
		return new DataSourceTransactionManager(postgresDataSource());
	}
	
	
	/*@Bean
	public MongoClient mongo() {
	    ConnectionString connectionString = new ConnectionString(environment.getProperty("spring.data.mongodb.uri"));
	    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
	            .applyConnectionString(connectionString)
	            .build();

	    return MongoClients.create(mongoClientSettings);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
	    return new MongoTemplate(mongo(), "Test");
	}*/
	
	/*@Bean
	public BatchConfigurer batchConfigurer(DataSource dataSource, EntityManagerFactoryBuilder entityManagerFactory) {
	    return new DefaultBatchConfigurer(dataSource) {
	        @Override
	        public PlatformTransactionManager getTransactionManager() {
	            return new DataSourceTransactionManager (dataSource);
	        }
	    };
	}*/
	
}