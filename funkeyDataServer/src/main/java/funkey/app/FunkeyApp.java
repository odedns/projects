package funkey.app;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;


 
@Configuration 
@EnableAutoConfiguration 
@ComponentScan(basePackages = { "funkey.service","funkey.dao" })
@EnableScheduling
public class FunkeyApp extends SpringBootServletInitializer { 
	private static final Logger log = Logger.getLogger(FunkeyApp.class);
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(FunkeyApp.class);
    }
	
	public static void main(String[] args) {
        SpringApplication.run(FunkeyApp.class, args);
        log.info("server started...");
    }
	
	@Bean
	public DynamoDB dynamoDB()
	{
		// TODO Auto-generated method stub
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setConnectionTimeout(50000);
		AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
		client.setRegion(Region.getRegion(Regions.US_WEST_2));
		DynamoDB dynamoDB = new DynamoDB(client);
		return(dynamoDB);
		
	}

@EnableWebSecurity
@Configuration
static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
  @Override
  protected void configure(HttpSecurity http) throws Exception {
	
    http.authorizeRequests()
    .antMatchers("/rest/test").permitAll()
    .anyRequest().fullyAuthenticated()
    .and()
    .httpBasic()
    .and()
    .csrf().disable();  
  }
	
	
 }
}

  
  






