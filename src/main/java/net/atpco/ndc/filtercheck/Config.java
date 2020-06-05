package net.atpco.ndc.filtercheck;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	@Bean
	public SuspectFinder buildSuspectFinder() {
		return new SuspectFinder();
	}

	@Bean
	public SuspectVerifier buildSuspectVerifier() {
		return new SuspectVerifier();
	}
}
