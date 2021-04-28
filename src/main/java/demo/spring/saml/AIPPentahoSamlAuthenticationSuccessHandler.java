package demo.spring.saml;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

/** Manage the redirect after a successful authentication.
 * Apply the redirect to the Pentaho console Home if and only if the original {@code targetUrl} is not a Pentaho exposed API but the home (contextPath/Home).
 * 
 * The other actions of the {@code onAuthenticationSuccess} method are inherited from the super Class.
 */
public class AIPPentahoSamlAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	protected final Log logger = LogFactory.getLog(this.getClass());

	private RequestCache requestCache = new HttpSessionRequestCache();
	private String contextPath;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) throws ServletException, IOException {

		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		//Apply the redirect to the Pentaho console Home if and only if the original targetUrl is not a Pentaho exposed API but the home (contextPath/Home)
		if (!savedRequest.getRedirectUrl().contains("API") && savedRequest.getRedirectUrl().contains(contextPath+"/Home")) {
			//The Pentaho console Home is set as defaultTargetUrl in the blueprint.xml
			this.setAlwaysUseDefaultTargetUrl(true);
			logger.info("The request is not on a Pentaho API. Forcing the target URL to redirect to the defaultTargetUrl");
		}
		super.onAuthenticationSuccess(request, response, authentication);
		
		//retore the original value of alwaysUseDefaultTargetUrl
		this.setAlwaysUseDefaultTargetUrl(false);
	}
	
	/* ***** ***** ***** Getters and Setters ***** ***** ***** */
	
	/**
	 * @return the requestCache
	 */
	public RequestCache getRequestCache() {
		return requestCache;
	}
	
	/**
	 * @param requestCache the requestCache to set
	 */
	@Override
	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

	/**
	 * @return the contextPath
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param contextPath the contextPath to set
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
}