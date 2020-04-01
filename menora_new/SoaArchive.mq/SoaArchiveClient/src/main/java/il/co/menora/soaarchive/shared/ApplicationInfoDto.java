package il.co.menora.soaarchive.shared;

import java.io.Serializable;

public class ApplicationInfoDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String version;
	private String userName;

	@SuppressWarnings("unused")
	private ApplicationInfoDto() {
		// Constructor for serialization only
	}

	public ApplicationInfoDto(String version, String userName) {
		this.version = version;
		this.userName = userName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
