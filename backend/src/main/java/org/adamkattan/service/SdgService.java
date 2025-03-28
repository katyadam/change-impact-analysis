package org.adamkattan.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import org.adamkattan.model.sdg.CreateServiceDependencyGraph;
import org.adamkattan.model.sdg.ServiceDependencyGraphEntity;
import org.adamkattan.model.sdg.ServiceDependencyGraphFullDto;
import org.adamkattan.model.sdg.ServiceDependencyGraphSummary;
import org.adamkattan.model.sdg.compare.ChangedServiceDependecyGraph;

import java.util.List;

@ApplicationScoped
public class SdgService {

    @Inject
    ProjectService projectService;

    public ServiceDependencyGraphEntity getSdgById(Long id) {
        return ServiceDependencyGraphEntity.find("id", id).firstResult();
    }

    public List<ServiceDependencyGraphEntity> getProjectSdgs(Long projectId) {
        return ServiceDependencyGraphEntity.find("project.id", projectId).list();
    }

    public ServiceDependencyGraphSummary getSdgSummary(Long id) {
        ServiceDependencyGraphEntity sdg = ServiceDependencyGraphEntity.find("id", id).firstResult();
        if (sdg != null) {
            return new ServiceDependencyGraphSummary(sdg.changedSdgs.size());
        }
        throw new EntityNotFoundException("SDG not found");
    }

    public ServiceDependencyGraphFullDto addSdgToProject(CreateServiceDependencyGraph sdgFullDto) {
        var project = projectService.getProjectById(sdgFullDto.projectId());
        var serviceDependencyGraphEntity = new ServiceDependencyGraphEntity();
        serviceDependencyGraphEntity.project = project;
        serviceDependencyGraphEntity.version = sdgFullDto.version();
        serviceDependencyGraphEntity.commitHash = sdgFullDto.commitHash();
        serviceDependencyGraphEntity.sdg = sdgFullDto.sdg();
        serviceDependencyGraphEntity.persist();
        return ServiceDependencyGraphEntity.toFullDto(serviceDependencyGraphEntity);

    }

    public List<ChangedServiceDependecyGraph> getSdgChanges(Long id) {
        ServiceDependencyGraphEntity sdg = ServiceDependencyGraphEntity.find("id", id).firstResult();
        return sdg.changedSdgs;
    }

    public Long deleteSdgById(Long id) {
        ServiceDependencyGraphEntity sdgEntity = ServiceDependencyGraphEntity.find("id", id).firstResult();
        if (sdgEntity != null) {
            sdgEntity.changedSdgs.clear();
            sdgEntity.project.sdgs.remove(sdgEntity);
            sdgEntity.persist();
            sdgEntity.delete();
            return sdgEntity.id;
        }
        throw new EntityNotFoundException("SDG with id: " + id + " was not found");
    }

}
