package org.mohaan.services;

import com.google.cloud.bigquery.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BigQueryService {

    private final BigQuery bigQuery;

    public BigQueryService() {
        this.bigQuery = BigQueryOptions.getDefaultInstance().getService();
    }

    public List<String> runQuery(String queryString) {
        List<String> results = new ArrayList<>();

        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder("SELECT * FROM `test-pubsub-463217.test.user`").build();

        try {
            JobId jobId = JobId.newBuilder()
                    .setRandomJob()
                    .setLocation("us-central1") // Replace with your region
                    .build();

            Job queryJob = bigQuery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

// Wait for the query to complete
            queryJob = queryJob.waitFor();

            TableResult result = queryJob.getQueryResults();;

            for (FieldValueList row : result.iterateAll()) {
                // Adjust this to match your schema
                String rowData = row.get(0).getStringValue();
                results.add(rowData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Query interrupted", e);
        }

        return results;
    }
}
