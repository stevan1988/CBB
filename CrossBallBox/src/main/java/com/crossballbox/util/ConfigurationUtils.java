package com.crossballbox.util;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ConfigurationUtils {
	private Path fileDownloadPath;

	public Path getFileDownloadPath() {
		return fileDownloadPath;
	}

	@Autowired
	public void setFileDownloadPath(@Value("${fileDownloadPath}") Path fileDownloadPath) {
		this.fileDownloadPath = fileDownloadPath;
	}
	
//	mozda moze i ovako, pa posle autovioder
//	 public static ApplicationSettings getAppSettingsFromContext() {
//		 	        return (ApplicationSettings) getApplicationContext().getBean("applicationSettings");
//		    }
}
