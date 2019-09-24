package com.palico.gcloudclient;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.palico.gcloudclient.config.Config;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Google Cloud client for uploading file.
 */
@Slf4j
public class UploadFileGCloudClient {

    public static void main(String[] args) throws IOException {
        Config config = null;

        try {
            config = Config.getConfig();
            Objects.requireNonNull(config);

            String[] scopesArray = config.gcloud.scopes.split(",");
            List<String> scopes = Arrays.asList(scopesArray).stream().map(s -> s.trim()).collect(Collectors.toList());

            // Connect to storage of project on GCP
            Credentials credentials = GoogleCredentials.getApplicationDefault().createScoped(scopes);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(config.gcloud.project).build().getService();

            // Read content from the file and convert to bytes array
            Path path = Paths.get(config.gcloud.file);
            byte[] fileContent = Files.readAllBytes(path);

            // Create blob object with the file content
            BlobId blobId = BlobId.of(config.gcloud.bucket, config.gcloud.blob);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(config.gcloud.mimetype).build();
            storage.create(blobInfo, fileContent);

        } catch (NoSuchFileException e) {
            log.error("File not found : " + config.gcloud.file);
            throw new IOException(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IOException(e.getMessage());
        }
    }
}
