# GCloud-Client

This application has the goal of upload file in a specific bucket hosted at Google Cloud Platform.

## Get started

## Setup
Before running the application, verify if the properties have been set in *config.yml* file (in */gcloud-client/src/main/resources*):

```
gcloud:
  project: palico-technical-test # project name
  scopes: https://www.googleapis.com/auth/cloud-platform # scope list
  bucket: palico-technical-test # bucket name
  blob: palico-meetup/yupanqui/attendees.csv # blob name
  mimetype: text/csv # MIME Type
  file: /Users/munoz/Workspace/palico-meetup/attendees.csv # file path to upload
```
## Run
For running, execute:
```
mvn compile exec:java -Dexec.mainClass="com.palico.gcloudclient.UploadFileGCloudClient"
```