package demo.spring.saml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.saml2.metadata.provider.ResourceBackedMetadataProvider;
import org.opensaml.xml.parse.XMLParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bsh.StringUtil;

public class SSOMetadataProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(SSOMetadataProvider.class);

	public static List<MetadataProvider> metadataList() throws MetadataProviderException, XMLParserException, IOException, Exception {
//		ResourceBackedMetadataProvider a;
//		MetadataResourceFactory b;
		
		LOGGER.info("Starting : Loading Metadata Data for all SSO enabled companies...");
		List<MetadataProvider> metadataList = new ArrayList<>();
		org.opensaml.xml.parse.StaticBasicParserPool parserPool = new org.opensaml.xml.parse.StaticBasicParserPool();
		parserPool.initialize();

		//Get XML from DB -> convertIntoInputStream -> pass below as const argument
		InputStreamMetadataProvider inputStreamMetadata = null;
		try {
			//Getting list from DB
			List companyList = someServiceClass.getAllSSOEnabledCompanyDTO();

			if (companyList != null) {
				for (Object obj : companyList) {
					CompanyDTO companyDTO = (CompanyDTO) obj;
					if (companyDTO != null && companyDTO.getCompanyid() > 0 && companyDTO.getSsoSettingsDTO() != null && !StringUtil.isNullOrEmpty(companyDTO.getSsoSettingsDTO().getSsoMetadataXml())) {
						LOGGER.info("Loading Metadata for Company : "+companyDTO.getCompanyname()+" , companyId : "+companyDTO.getCompanyid());

						inputStreamMetadata = new InputStreamMetadataProvider(companyDTO.getSsoSettingsDTO().getSsoMetadataXml());
						inputStreamMetadata.setParserPool(parserPool);
						inputStreamMetadata.initialize();

						//ExtendedMetadataDelegateWrapper extMetadaDel = new ExtendedMetadataDelegateWrapper(inputStreamMetadata , new org.springframework.security.saml.metadata.ExtendedMetadata());
						SSOMetadataDelegate extMetadaDel = new SSOMetadataDelegate(inputStreamMetadata , new org.springframework.security.saml.metadata.ExtendedMetadata()) ;

						extMetadaDel.initialize();
						extMetadaDel.setTrustFiltersInitialized(true);
						metadataList.add(extMetadaDel);

						LOGGER.info("Loading Metadata bla bla");
					}
				}
			}

		} catch (MetadataProviderException | IOException | XMLParserException mpe) {
			LOGGER.warn(mpe.toString());
			throw mpe;

		} catch (Exception e) {
			LOGGER.warn(e.toString());
		}

		LOGGER.info("Finished : Loading Metadata Data for all SSO enabled companies...");
		return metadataList;
	}
}