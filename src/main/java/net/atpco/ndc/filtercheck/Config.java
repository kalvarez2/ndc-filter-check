package net.atpco.ndc.filtercheck;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class Config {
	@Value("${apiUrl}")
	private String apiUrl;

	@Value("${apiKey}")
	private String apiKey;

	@Bean
	public SuspectFinder buildSuspectFinder() {
		return new SuspectFinder();
	}

	@Bean
	public SuspectVerifier buildSuspectVerifier() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("request-template.pbl").getFile());
		// File is found
		log.info("Found request-template.pbl: " + file.exists());
		// Read File Content
		String content = new String(Files.readAllBytes(file.toPath()));
		return new SuspectVerifier(apiKey, apiUrl, content);
	}
}
