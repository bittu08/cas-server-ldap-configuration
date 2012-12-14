package org.jasig.cas.hashedin;

/*
 * Model class For User data
 * accessKey  - Access Key Assign To User.
 */
public class UserData {

	private String accountName;
	private String displayName;
	private String firstName;
	private String lastName;
	private String initials;
	private String email;
	private String accessToken;

	/*
	 * Constructor to initialize the User attribute.
	 */
	public UserData(String accountName, String displayName, String firstName,
			String lastName, String initials, String email, String accessToken) {
		super();
		this.accountName = accountName;
		this.displayName = displayName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.initials = initials;
		this.email = email;
		this.accessToken = accessToken;
	}

}
