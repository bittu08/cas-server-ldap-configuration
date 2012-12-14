
cas-server-ldap-configuration
=============================

Implemetation of ldap configuration on cas-server. In this project, the ldap configuration done by creating class, instead of changing the configuration in deployConfi.xml file, so, we can create dynamic authentication.

Ldap Configuration Class
========================
The class contain under, org.jasig.cas.hashedin, this package contain four classes - 

1. AuthenticationController.java - 
 
This class get the Username and Password from credential class, and send to LdapAuthenticationHandler class to authenticate with ldap server.

2. LdapAuthenticationHandler.java - 

This class contain, authenticate method, which first authenticate the user, by creating LdapContext connection, if user
is invalid then it throw exception, other wise , it return connetion object, and retrieve the User information such as Username, user first name,etc.

3. UserDataModel -

This class is used for model class,

4.AuthenticationExceptionHandler  -

This is custom exception class, which handle all the exception generate by LdapAuthenticationHandler class.

