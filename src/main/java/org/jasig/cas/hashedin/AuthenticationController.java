package org.jasig.cas.hashedin;

import org.jasig.cas.authentication.handler.support.AbstractUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;
import org.springframework.beans.factory.annotation.Autowired;

public final class AuthenticationController extends
		AbstractUsernamePasswordAuthenticationHandler {

	private LdapAuthenticationHandler objLdapHandler = null;

	public AuthenticationController() {
		log.warn(this.getClass().getName()
				+ " is only to be used in a testing environment.  NEVER enable this in a production environment.");
	}

	@Override
	protected boolean authenticateUsernamePasswordInternal(
			final UsernamePasswordCredentials credentials) {

		final String userName = credentials.getUsername();
		final String password = credentials.getPassword();
		try {
			objLdapHandler = new LdapAuthenticationHandler("Your Server Ldap Address");
			objLdapHandler.getUserData(userName, password);
			return true;
		}

		catch (Exception e) {
			return false;
		}

	}

}
