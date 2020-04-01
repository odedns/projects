package il.co.menora.soaarchive.client.app;

import il.co.menora.soaarchive.shared.ApplicationInfoDto;

public class ApplicationStatus {
	private static ApplicationInfoDto applicationInfoDto = null;

	public static void setApplicationInfoDto(ApplicationInfoDto applicationInfoDto) {
		ApplicationStatus.applicationInfoDto = applicationInfoDto;
	}

	public static ApplicationInfoDto getApplicationInfoDto() {
		return applicationInfoDto;
	}
}
