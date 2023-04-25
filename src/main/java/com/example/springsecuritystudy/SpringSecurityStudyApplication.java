package com.example.springsecuritystudy;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@SpringBootApplication
public class SpringSecurityStudyApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(SpringSecurityStudyApplication.class, args);

        WireMockConfiguration wireMockConfiguration = new WireMockConfiguration()
                .port(9876);
//                .withRootDirectory("src/main/resources/mapping");

        WireMockServer wireMockServer = new WireMockServer(wireMockConfiguration);
        File file = new File("src/main/resources/mappings/response.json");
        InputStream resourceAsStream = SpringSecurityStudyApplication.class.getClassLoader().getResourceAsStream("response.json");

        System.out.println(new String(resourceAsStream.readAllBytes(), StandardCharsets.UTF_8));

        String absolutePath = file.getAbsolutePath();
        String fileString = Files.readString(file.toPath().toAbsolutePath());
        System.out.println(absolutePath);
        System.out.println(fileString);

        wireMockServer.stubFor(
                WireMock.request("GET", WireMock.urlMatching("/test/wiremock/.*"))
                        .willReturn(WireMock.aResponse().withBody(fileString.getBytes())));


        wireMockServer.start();

    }

}
