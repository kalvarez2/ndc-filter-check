package net.atpco.ndc.filtercheck;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SuspectVerifier {
	private final String url;

	private final PebbleEngine pebbleEngine = new PebbleEngine.Builder().build();
	private final PebbleTemplate pebbleTemplate;
	private final HttpHeaders headers;

	private RestTemplate resTemplate;

	public SuspectVerifier(String apiKey, String url, String requestTemplateStr) {
		this.url = url;
		resTemplate = new RestTemplate();
		pebbleTemplate = pebbleEngine.getLiteralTemplate(requestTemplateStr);
		//
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.add("X-apiKey", apiKey);

	}

	public boolean hasFlights(Suspect suspect) {
		Map<String, Object> supectMap = new HashMap<String, Object>();
		supectMap.put("carrier", suspect.getCarrier());
		supectMap.put("origin", suspect.getOrigin());
		supectMap.put("destination", suspect.getDestination());

		final String requestBody = evalTemplate(supectMap);
		log.info("Sending request");

		HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
		try {
			ResponseEntity<String> response = resTemplate.postForEntity(url, request, String.class);
			if (response.getStatusCodeValue() == 200 && response.hasBody()) {
				String body = response.getBody();
				log.info("got response");
				//log.info(body);
				if (body.indexOf("<NDCMSG_Fault>") == -1 && body.indexOf("<OriginDestID>") > 0) {
					return true;
				}
			}
		} catch (Exception x) {
			log.warn("Calling api gave error", x);
		}

		return false;
	}

	private String evalTemplate(Map<String, Object> templateData) {
		//
		StringWriter writer = new StringWriter();
		try {
			pebbleTemplate.evaluate(writer, templateData);
			writer.flush();
		} catch (IOException e) {
			log.error("Error evaluating template", e);
		}
		return writer.getBuffer().toString();
	}

}
