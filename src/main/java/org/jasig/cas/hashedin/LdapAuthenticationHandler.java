package org.jasig.cas.hashedin;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import org.jasig.cas.hashedin.UserData;
import org.jasig.cas.hashedin.AuthenticationExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/*
 * All the authentication related query manage by this class
 */

public class LdapAuthenticationHandler {

	private final String ldapUrl;

	public LdapAuthenticationHandler(String ldapUrl) {
		this.ldapUrl = ldapUrl;
	}

	/*
	 * This method get userId and password as parameter, and for valid user
	 * gnerate token, otherwise raise exception for it
	 */

	public UserData getUserData(String userId, String password)
			throws AuthenticationExceptionHandler {

		UserData objAdUserData = null;
		LdapContext ctx = authenticate(userId, password);
		SearchControls constraint = new SearchControls();
		constraint.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> answer;
		String[] splitStr = userId.split("@");
		String[] splitDC = splitStr[1].split("\\.");

		String dc = "";

		for (String objStr : splitDC) {
			dc += "DC=" + objStr + ",";
		}

		try {
			answer = ctx.search(dc.substring(0, dc.length() - 1),
					"sAMAccountName=" + splitStr[0], constraint);

			if (answer.hasMore()) {

				Attributes attrs = ((SearchResult) answer.next())
						.getAttributes();
				String email = getAttributeData(attrs, "mail");
				String displayName = getAttributeData(attrs, "displayName");
				String firstName = getAttributeData(attrs, "givenName");
				String lastName = getAttributeData(attrs, "sn");
				String initials = getAttributeData(attrs, "initials");
				String accountName = getAttributeData(attrs, "samAccountName");
				String accessKey = "AccEsSToKen";
				objAdUserData = new UserData(accountName, displayName,
						firstName, lastName, initials, email, accessKey);
				return objAdUserData;

			} else {
				throw new AuthenticationExceptionHandler(
						"UserName does not exist");
			}
		} catch (Exception e) {
			throw new AuthenticationExceptionHandler("Unable to fetch data");

		}

	}

	private String getAttributeData(Attributes attrs, String key) {
		try {
			return attrs.get(key).get().toString();
		} catch (Exception e) {
			return null;
		}
	}

	private LdapContext authenticate(String userId, String password)
			throws AuthenticationExceptionHandler {
		LdapContext ctx = null;

		try {
			Hashtable<String, String> env = new Hashtable<String, String>();
			env.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, userId);
			env.put(Context.SECURITY_CREDENTIALS, password);
			env.put(Context.PROVIDER_URL, ldapUrl);
			try {
				ctx = new InitialLdapContext(env, null);
				return ctx;
			} catch (NamingException nex) {
				throw new AuthenticationExceptionHandler(
						"Unable to create connetion");

			}
		} catch (Exception e) {
			throw new AuthenticationExceptionHandler(
					"Unable to create connection");
		}

	}

}
