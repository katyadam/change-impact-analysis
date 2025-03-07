package org.adamkattan.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.adamkattan.analysis.SdgChangeImpactAnalysis;
import org.adamkattan.model.sdg.LinksInputDto;
import org.adamkattan.model.sdg.compare.ChangedServiceDependecyGraph;
import org.adamkattan.model.sdg.compare.ChangedLinksOutput;
import org.adamkattan.model.input.AnalysisInput;

import java.util.List;

@ApplicationScoped
public class GraphDifferenceService {

    @Inject
    AnalysisInputService analysisInputService;

    public ChangedServiceDependecyGraph getChangedGraph(Long id) {
        return ChangedServiceDependecyGraph.find("id", id).firstResult();
    }

    public List<ChangedServiceDependecyGraph> getChangedGraphs(Long analysisInputId) {
        return ChangedServiceDependecyGraph.find("analysisInput.id", analysisInputId).list();
    }

    public ChangedLinksOutput saveChangedGraphLinks(
            LinksInputDto linksInputDto,
            Long srcId
    ) {
        AnalysisInput analysisInput = analysisInputService.getAnalysisInputById(srcId);
        ChangedLinksOutput linksDifference = SdgChangeImpactAnalysis.getLinksDifference(
                analysisInput.graph.links(),
                linksInputDto.links()
        );
        var changedGraph = new ChangedServiceDependecyGraph();
        changedGraph.changedLinks = linksDifference.changedLinks();
        changedGraph.analysisInput = analysisInput;
        changedGraph.persist();
        return linksDifference;
    }

}
