package se.snorbuse.coronaimporter.util;

import se.snorbuse.coronaimporter.model.CombinedDatapoint;
import se.snorbuse.coronaimporter.model.RestResponse;

import java.util.ArrayList;
import java.util.List;

public class ElasticSearch {

    private final RestClient restClient;
    private static final int LOG_EVERY = 500;
    private static int rowsInserted = 0;

    public ElasticSearch() {
        this.restClient = new RestClient();
    }

    public void resetIndex() {
        restClient.delete("http://localhost:9200/corona-2020/");
        createIndex();
    }

    public void createIndex() {
        String mappings = "{\"mappings\": {\"properties\": {" +
                "\"confirmed\": {\"type\": \"long\"}, " +
                "\"country\": {\"type\": \"text\", \"fields\": {\"keyword\": {\"type\": \"keyword\", \"ignore_above\": 256}}}, " +
                "\"date\": {\"type\": \"date\"}, " +
                "\"deaths\": {\"type\": \"long\"}, " +
                "\"id\": {\"type\": \"text\", \"fields\": {\"keyword\": {\"type\": \"keyword\", \"ignore_above\": 256}}}, " +
                "\"location\": {\"type\": \"geo_point\"}, " +
                "\"province\": {\"type\": \"text\", \"fields\": {\"keyword\": {\"type\": \"keyword\", \"ignore_above\": 256}}}, " +
                "\"recovered\": {\"type\": \"long\"}}}}";

        RestResponse response = restClient.put("http://localhost:9200/corona-2020/", mappings);
        Logger.info("Created index, HttpStatus: %d, Message: %s", response.getHttpStatus(), response.getResponseMessage());
    }

    public void importData(List<CombinedDatapoint> combinedDatapointList) {
        combinedDatapointList
                .forEach(this::insert);
    }

    private void insert(CombinedDatapoint data) {
        RestResponse response = restClient.post(
                "http://localhost:9200/corona-2020/_doc/" + data.getId(),
                createJsonString(data)
        );

        Logger.debug("Response code: %d, Message: %s", response.getHttpStatus(), response.getResponseMessage());

        rowsInserted++;
        if (rowsInserted % LOG_EVERY == 0) {
            Logger.info("Added totally %,d documents to Elastic Search", rowsInserted);
        }
    }

    private String createJsonString(CombinedDatapoint data) {
        List<String> jsonElements = new ArrayList<>();

        jsonElements.add(String.format("\"id\":\"%s\"", data.getId()));
        jsonElements.add(String.format("\"province\":\"%s\"", data.getProvince()));
        jsonElements.add(String.format("\"country\":\"%s\"", data.getCountry()));
        jsonElements.add(String.format("\"date\":\"%s\"", data.getDate()));
        jsonElements.add(String.format("\"confirmed\":%d", data.getConfirmed()));
        jsonElements.add(String.format("\"deaths\":%d", data.getDeaths()));
        jsonElements.add(String.format("\"recovered\":%d", data.getRecovered()));
        jsonElements.add(
                String.format(
                        "\"location\": \"POINT (%.2f %.2f)\"",
                        data.getLocation().getLon(),
                        data.getLocation().getLat()
                )
        );

        return String.format("{%s}", String.join(",", jsonElements));
    }
}
