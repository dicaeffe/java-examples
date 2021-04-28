package demo.spring.saml;

import java.io.Serializable;

import org.opensaml.saml2.metadata.provider.AbstractReloadingMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InputStreamMetadataProvider extends AbstractReloadingMetadataProvider implements Serializable {
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(InputStreamMetadataProvider.class);

	private byte[] metadataInputStream;

	public InputStreamMetadataProvider(String metadata) throws MetadataProviderException {
		super();
		//metadataInputStream = metadata;
		metadataInputStream = getIdpAsStream(metadata);
	}

	@Override
	protected byte[] fetchMetadata() throws MetadataProviderException {
		byte[] metadataBytes = metadataInputStream ;

		if(metadataBytes.length>0) {
			return metadataBytes;
		} else { 
			return null;
		}
	}

	public byte[] getMetadataInputStream() {
		return metadataInputStream;
	}

	public static byte[] getIdpAsStream(String metadatXml) {
		return metadatXml.getBytes();
	}

	@Override
	protected String getMetadataIdentifier() {
		return "";
	}
}