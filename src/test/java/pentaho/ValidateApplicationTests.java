package pentaho;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pentaho.platform.plugin.services.security.userrole.ldap.DefaultLdapAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ReflectionUtils;

import config.LdapConfig;
 
@ActiveProfiles("test")
@RunWith(SpringRunner.class)  
@SpringBootTest(classes = { LdapConfig.class })
public class ValidateApplicationTests {
	@Autowired
	private LdapAuthenticationProvider provider ;
	
	@Autowired
	@Qualifier("allUsernamesSearch")
	private org.pentaho.platform.plugin.services.security.userrole.ldap.search.GenericLdapSearch search ; 
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAuthenticate() throws Exception { 
		Field field = DefaultLdapAuthenticationProvider.class.getDeclaredField("authenticatedRole");
		field.setAccessible(true);
		ReflectionUtils.setField(field,provider,"Authenticated");
		 
		String principal="katy";
		String credentials ="iisi@2619";
		Authentication authentication = new UsernamePasswordAuthenticationToken(principal, credentials);
		provider.authenticate(authentication);
	}
	@Test
	public void testGenericLdapSearch() throws Exception {		
		List list = search.search(new String[] {"DC=demo,DC=local"});
		System.out.println(list.size());
	}
}
