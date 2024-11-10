package org.adamkattan.model.methods;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record MethodsInputDto(
        @JsonProperty("methods") List<MicroserviceNode> methods
) {
}
