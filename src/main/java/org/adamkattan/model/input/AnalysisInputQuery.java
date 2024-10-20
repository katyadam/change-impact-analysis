package org.adamkattan.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.QueryParam;

public class AnalysisInputQuery {
    @NotBlank(message = "appName is required")
    @QueryParam("appName")
    public String appName;
}
