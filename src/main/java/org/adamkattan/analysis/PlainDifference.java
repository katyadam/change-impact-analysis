package org.adamkattan.analysis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.adamkattan.model.entities.Entities;
import org.adamkattan.model.graph.Graph;
import org.adamkattan.model.input.AnalysisInput;
import org.adamkattan.model.output.AnalysisOutput;

import java.util.Optional;

@ApplicationScoped
public class PlainDifference {

    ObjectMapper objectMapper = new ObjectMapper();

    public Optional<AnalysisOutput> getPlainDifference(AnalysisInput newInput, AnalysisInput oldInput) {
        if (!newInput.appName.equals(oldInput.appName)) {
            return Optional.empty();
        }

        boolean isDifferent = compareEntities(newInput.entities, oldInput.entities) &&
                compareGraphs(newInput.graph, oldInput.graph);
        var result = AnalysisOutput.builder()
                .appName(newInput.appName)
                .newVersion(newInput.version)
                .oldVersion(oldInput.version)
                .newCommitHash(newInput.commitHash)
                .oldCommitHash(oldInput.commitHash)
                .isDifferent(isDifferent)
                .build();

        return Optional.of(result);
    }

    private boolean compareEntities(Entities newEntities, Entities oldEntities) {
        try {
            String newJson = objectMapper.writeValueAsString(newEntities);
            String oldJson = objectMapper.writeValueAsString(oldEntities);
            return newJson.equals(oldJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean compareGraphs(Graph newGraph, Graph oldGraph) {
        try {
            String newJson = objectMapper.writeValueAsString(newGraph);
            String oldJson = objectMapper.writeValueAsString(oldGraph);
            return newJson.equals(oldJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}