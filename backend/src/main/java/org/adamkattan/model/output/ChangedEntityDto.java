package org.adamkattan.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.adamkattan.model.entities.ChangedEntityLink;

import java.time.LocalDateTime;
import java.util.List;

public record ChangedEntityDto(
        @JsonProperty("analysisInputId") Long analysisInputId,
        @JsonProperty("changedLinks") List<ChangedEntityLink> changedLinks,
        @JsonProperty("createdAt") LocalDateTime createdAt
) {
}
