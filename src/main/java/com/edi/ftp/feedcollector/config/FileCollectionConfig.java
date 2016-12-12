package com.edi.ftp.feedcollector.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="filecollection")
public class FileCollectionConfig {

	Map<String, Map<String, String>> ftpconfiguration = new HashMap<>();

	public Map<String, Map<String, String>> getFtpconfiguration() {
		return ftpconfiguration;
	}

	public void setFtpconfiguration(
			Map<String, Map<String, String>> ftpconfiguration) {
		this.ftpconfiguration = ftpconfiguration;
	}
}
