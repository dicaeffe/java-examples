package demo.spring.saml;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import org.opensaml.common.SAMLObject;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.EncryptedAssertion;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.Response;
import org.opensaml.xml.encryption.DecryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLAuthenticationToken;
import org.springframework.security.saml.context.SAMLMessageContext;
import org.springframework.util.Assert;

public class AIPPentahoSamlAuthenticationProvider extends PentahoSamlAuthenticationProvider {

	private static final Logger logger = LoggerFactory.getLogger(AIPPentahoSamlAuthenticationProvider.class);

	private String tenantEntityIds;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		SAMLAuthenticationToken token = (SAMLAuthenticationToken) authentication;
		SAMLMessageContext context = token.getCredentials();
		
		context.setLocalEntityId(extractTenantEntityId(context));
		
		return super.authenticate(authentication);
	}
	
	/** Returns the {@code tenantEntityId} composed with the tenantDomain extracted from the SAML Response's assertion.
	 * 
	 * @param context provides the SAML Response's assertion with the {@code nameId} in the format "username@tenantDomain".
	 * @return a string with the tenant entity id. The format is "pentaho@tenantDomain". If {@code nameId} is null or empty the {@code tenantEntityId} wil be "pentaho".
	 */
	private String extractTenantEntityId(SAMLMessageContext context) {
		
        SAMLObject message = context.getInboundSAMLMessage();
        Response response = (Response) message;
        List<Assertion> assertionList = response.getAssertions();

        // Decrypt assertions
        if (response.getEncryptedAssertions().size() > 0) {
            assertionList = new ArrayList<Assertion>(response.getAssertions().size() + response.getEncryptedAssertions().size());
            assertionList.addAll(response.getAssertions());
            List<EncryptedAssertion> encryptedAssertionList = response.getEncryptedAssertions();
            for (EncryptedAssertion ea : encryptedAssertionList) {
                try {
                    Assert.notNull(context.getLocalDecrypter(), "Can't decrypt Assertion, no decrypter is set in the context");
                    logger.debug("Decrypting assertion");
                    Assertion decryptedAssertion = context.getLocalDecrypter().decrypt(ea);
                    assertionList.add(decryptedAssertion);
                } catch (DecryptionException e) {
                	logger.debug("Decryption of received assertion failed, assertion will be skipped", e);
                }
            }
        }
        
        //Extract the nameId from the assertion of the SAML Response.
        String nameId = "";
		for (Assertion assertion : assertionList) {
			NameID nameID = assertion.getSubject().getNameID();
			logger.info("nameID ==========> {}", nameID.getValue());
			nameId = nameID.getValue();
//			Conditions conditions = assertion.getConditions();
//			List<AudienceRestriction> audienceRestrictions = conditions.getAudienceRestrictions();
		}
		
		//Build the tenantEntityId with the format "pentaho@tenantDomail".
		String tenantEntityId = "pentaho";
		if (nameId != null && !nameId.isEmpty()) {
			int index = nameId.lastIndexOf("@");

			String tenantDomain = nameId.substring(index+1);
			tenantEntityId = tenantEntityId.concat("@").concat(tenantDomain);
		}
		
//		String[] availableTenantEntityIdsArray = tenantEntityIds.split(",");
//		for (String availableTenantEntityId : availableTenantEntityIdsArray) {
//			if (availableTenantEntityId != null && !availableTenantEntityId.isBlank()) {
//				
//			}
//		}

		return tenantEntityId;
	}

	public String getTenantEntityIds() {
		return tenantEntityIds;
	}

	public void setTenantEntityIds(String tenantEntityIds) {
		this.tenantEntityIds = tenantEntityIds;
	}
}