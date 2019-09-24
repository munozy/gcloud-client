package com.palico.gcloudclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Access properties from "config.yml" file.
 */
@Slf4j
public class Config {
    public static final String CONFIG_FILE = "config.yml";

    public class GCloud {
        @JsonProperty
        public String project;

        @JsonProperty
        public String scopes;

        @JsonProperty
        public String bucket;

        @JsonProperty
        public String blob;

        @JsonProperty
        public String mimetype;

        @JsonProperty
        public String file;
    }

    @JsonProperty
    public GCloud gcloud;

    private static Config config = null;

    private Config() {
        // Non inititiable
        new AssertionError();
    }

    public static Config getConfig() throws IOException {
            if (config == null) {
                config = parseFile(Paths.get("src","main","resources", CONFIG_FILE));
            }
            return config;
    }

    private static Config parseFile(Path path) throws IOException {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            final String yamlSource = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

            return mapper.readValue(yamlSource, Config.class);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }


}
