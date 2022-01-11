package demo.spring.saml;

import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLProcessingFilter;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.security.saml.websso.WebSSOProfileConsumerImpl;

/**
 * factory that builds an appropriate org.opensaml.util.resource.Resource implementation
 */
public class FindClass extends SAMLContextProviderImpl {
	org.springframework.security.saml.metadata.ExtendedMetadataDelegate a;
	org.springframework.security.saml.metadata.ExtendedMetadata b;
	SAMLUserDetailsService c;
	org.springframework.security.saml.metadata.MetadataGenerator d;
	org.springframework.security.saml.metadata.MetadataGeneratorFilter e;
	org.springframework.security.saml.websso.WebSSOProfileConsumerImpl f;
	org.springframework.security.saml.context.SAMLContextProviderLB g;
	org.springframework.security.saml.storage.EmptyStorageFactory h;
//	org.springframework.session.web.http.SessionRepositoryFilter i;
	WebSSOProfileConsumerImpl j;
	SAMLAuthenticationProvider k;
	SAMLContextProviderImpl l;
	org.springframework.security.saml.SAMLProcessingFilter m;
	org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler n;
	org.springframework.beans.factory.InitializingBean o;
	org.springframework.security.web.authentication.logout.LogoutFilter;
	org.springframework.security.web.FilterChainProxy;
}
