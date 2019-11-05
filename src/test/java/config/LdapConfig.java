package config;

import java.io.File;
import java.net.URL;
import java.util.Set;

import org.pentaho.platform.config.LdapConfigProperties;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath:applicationContext-spring-security-ldap.xml",
					"classpath:applicationContext-pentaho-security-ldap.xml" })
public class LdapConfig {
	
	@Bean
	public PropertyPlaceholderConfigurer getLdapConfigProperties() {
		URL url = this.getClass().getResource("/applicationContext-security-ldap.properties");

		final PropertyPlaceholderConfigurer result = new PropertyPlaceholderConfigurer();
		 
		java.util.Properties properties = new java.util.Properties();
		 
		try {
			LdapConfigProperties pp = new LdapConfigProperties(new File(url.toURI()));
			Set<String> keyNameSet = pp.getProperties().stringPropertyNames();
			keyNameSet.forEach((k) -> {
				final String value = pp.getProperties().get(k).toString();
				properties.setProperty("ldap." + k, value);
			});
			result.setProperties(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Bean("IPasswordService")
	public org.pentaho.platform.util.KettlePasswordService config() {
		return new org.pentaho.platform.util.KettlePasswordService();
	}

	@Bean({ "defaultRole", "singleTenantAuthenticatedAuthorityName" })
	public String getDefaultRole() {
		return "Authenticated";
	}

}