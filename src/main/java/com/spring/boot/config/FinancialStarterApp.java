package com.spring.boot.config;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@RestController
@RequestMapping("/api/")
public class FinancialStarterApp implements CommandLineRunner {

	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FinancialStarterApp.class);
	@Autowired
	IFinancialRepository repository;

	public static final String SAMPLE_JSON_PATH = "sample_json_data.txt";

	public static void main(String[] args) {
		SpringApplication.run(FinancialStarterApp.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@GetMapping("/")
	@ResponseBody
	public String sampleJSONResponse() throws IOException, URISyntaxException {
		String path = Paths.get(SAMPLE_JSON_PATH).toAbsolutePath().toString();
		String response = new String(
				Files.readAllBytes(Paths.get(ClassLoader.getSystemResource(SAMPLE_JSON_PATH).toURI())));
		return response;
	}

	@GetMapping("/import-test/")
	public String importFinancialDataTest() throws JSONException {

		String message = "";
		try {
			StringBuffer output = new StringBuffer();
			doImportTest(output);
			message = output.toString();
		} catch (Exception e) {
			message = "Failed: " + e.getMessage();
		}

		return message;
	}

	@GetMapping("/import/")
	public String importFinancialData(@RequestParam("email") String email,
			@RequestParam("collection") String collection) throws JSONException {

		String message = "";
		try {
			StringBuffer output = new StringBuffer();
			doImport(email, collection, output);
			message = output.toString();
		} catch (Exception e) {
			message = "Failed: " + e.getMessage();
		}

		return message;
	}

	private synchronized void doImport(String email, String collection, StringBuffer output)
			throws JSONException, JsonProcessingException {
		String appId = "175f4dcf";
		String appKey = "6649c641a329f85660d563a2f8da7fdc";

		String url = "https://workbench.placeable.com/v2/collection/" + collection + "/items";

		HttpHeaders headers = new HttpHeaders();
		headers.add("X-UserEmail", email);

		RestTemplate template = new RestTemplate();
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("appId", appId)
				.queryParam("appKey", appKey);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<String> response = template.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		// response=template.exchange("http://localhost:8888/api/",HttpMethod.GET,null,
		// String.class);

		List<FinancialModel> list = FinancialJSONMapper.toObject(response);
		repository.deleteAll();
		repository.save(list);

		ObjectMapper mapper = new ObjectMapper();
		output.append(mapper.writeValueAsString(list));
	}

	private synchronized void doImportTest(StringBuffer output) throws JsonProcessingException, JSONException {
		RestTemplate template = new RestTemplate();
		ResponseEntity<String> response = null;

		response = template.exchange("http://localhost:8888/api/", HttpMethod.GET, null, String.class);

		List<FinancialModel> list = FinancialJSONMapper.toObject(response);
		repository.deleteAll();
		repository.save(list);

		ObjectMapper mapper = new ObjectMapper();
		output.append(mapper.writeValueAsString(list));
	}

}
