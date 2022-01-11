package demo.spring.saml;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.opensaml.saml2.metadata.impl.EntityDescriptorImpl;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.ExtendedMetadataDelegate;

public class SSOCachingMetadataManager extends CachingMetadataManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(SSOCachingMetadataManager.class);

	public SSOCachingMetadataManager(List<MetadataProvider> providers) throws MetadataProviderException {
		super(providers);
	}

	@Override
	public ExtendedMetadata getExtendedMetadata(String entityID) throws MetadataProviderException {
		ExtendedMetadata extendedMetadata = null;

		try {
			//List<MetadataProvider> metadataList = (List<MetadataProvider>) GenericCache.getInstance().getCachedObject("ssoMetadataList", List.class.getClassLoader());
			List<MetadataProvider> metadataList = SSOMetadataProvider.metadataList();

			log.info("Retrieved Metadata List from Cassendra Cache size is :"+ (metadataList != null ? metadataList.size(): 0) );

			org.opensaml.xml.parse.StaticBasicParserPool parserPool = new org.opensaml.xml.parse.StaticBasicParserPool();
			parserPool.initialize();

			if (metadataList != null) {
				//metadataList.addAll(getAvailableProviders());
				//metadataList.addAll(getProviders());

				//To remove duplicate entries from list, if any
				Set<MetadataProvider> hs = new HashSet<MetadataProvider> ();
				hs.addAll(metadataList);

				metadataList.clear();
				metadataList.addAll(hs);
				//setAllProviders(metadataList);
				//setTrustFilterInitializedToTrue();
				//refreshMetadata();
			}

			if (metadataList != null && metadataList.size()>0) {

				for(MetadataProvider metadataProvider : metadataList) {

					log.info("metadataProvider instance of ExtendedMetadataDelegate: Looking for entityId"+entityID);

					SSOMetadataDelegate ssoMetadataDelegate = null;           
					ExtendedMetadataDelegateWrapper extMetadaDel = null;

					//	           extMetadaDel.getDelegate()
					if (metadataProvider instanceof SSOMetadataDelegate) {
						ssoMetadataDelegate = (SSOMetadataDelegate) metadataProvider;

						((InputStreamMetadataProvider)ssoMetadataDelegate.getDelegate()).setParserPool(parserPool);
						((InputStreamMetadataProvider)ssoMetadataDelegate.getDelegate()).initialize();
						ssoMetadataDelegate.initialize();

						ssoMetadataDelegate.setTrustFiltersInitialized(true);

						if (!isMetadataAlreadyExist(ssoMetadataDelegate)) {
							addMetadataProvider(ssoMetadataDelegate);  
						}
						extMetadaDel = new ExtendedMetadataDelegateWrapper(ssoMetadataDelegate.getDelegate() , new org.springframework.security.saml.metadata.ExtendedMetadata());

					} else {
						extMetadaDel = new ExtendedMetadataDelegateWrapper(metadataProvider, new org.springframework.security.saml.metadata.ExtendedMetadata());
					}

					extMetadaDel.initialize();
					extMetadaDel.setTrustFiltersInitialized(true);

					extMetadaDel.initialize();

					refreshMetadata();

					extendedMetadata = extMetadaDel.getExtendedMetadata(entityID);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (extendedMetadata != null) {
			return extendedMetadata;

		} else {
			return super.getExtendedMetadata(entityID);             
		}
	}

	private boolean isMetadataAlreadyExist(SSOMetadataDelegate ssoMetadataDelegate) {

		boolean isExist = false;
		for(ExtendedMetadataDelegate item : getAvailableProviders()) {

			if (item.getDelegate() != null && item.getDelegate() instanceof SSOMetadataDelegate) { 

				SSOMetadataDelegate that = (SSOMetadataDelegate) item.getDelegate();
				try {

					log.info("This Entity ID: "+ssoMetadataDelegate.getMetadata() != null ? ((EntityDescriptorImpl)ssoMetadataDelegate.getMetadata()).getEntityID() : "nullEntity"+

	                  "That Entity ID: "+that.getMetadata() != null ? ((EntityDescriptorImpl)that.getMetadata()).getEntityID() : "nullEntity");

					EntityDescriptorImpl e = (EntityDescriptorImpl) that.getMetadata();

					isExist = this.getMetadata() != null ? ((EntityDescriptorImpl)ssoMetadataDelegate.getMetadata()).getEntityID().equals(e.getEntityID()) : false;

					if (isExist) {
						return isExist;
					}

				} catch (MetadataProviderException e1) {
					e1.printStackTrace();
				}
			}
		}
		return isExist;
	}
}