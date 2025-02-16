package org.adamkattan.analysis;

import org.adamkattan.model.entities.compare.ChangedEntityLink;
import org.adamkattan.model.entities.compare.ChangedEntityLinkType;
import org.adamkattan.model.entities.EntityLink;
import org.adamkattan.model.entities.compare.ChangedEntitiesLinksOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EntitiesDifferenceAnalysis {
    private record LinkKey(String source, String target, String msSource, String msTarget) {
    }

    public static ChangedEntitiesLinksOutput getLinksDifference(List<EntityLink> src, List<EntityLink> dest) {
        Map<LinkKey, EntityLink> srcMap = transformToMap(src);
        Map<LinkKey, EntityLink> destMap = transformToMap(dest);
        List<ChangedEntityLink> changedLinks = new ArrayList<>();
        for (EntityLink link : src) {
            var key = new LinkKey(
                    link.source(),
                    link.target(),
                    link.msSource(),
                    link.msTarget()
            );
            if (!destMap.containsKey(key)) {
                changedLinks.add(new ChangedEntityLink(link, ChangedEntityLinkType.REMOVED));
                continue;
            }
            var destVal = destMap.get(key);
            if (!destVal.equals(link)) {
                changedLinks.add(new ChangedEntityLink(destVal, ChangedEntityLinkType.MODIFIED));
            }
        }
        for (EntityLink link : dest) {
            var key = new LinkKey(
                    link.source(),
                    link.target(),
                    link.msSource(),
                    link.msTarget()
            );
            if (!srcMap.containsKey(key)) {
                changedLinks.add(new ChangedEntityLink(link, ChangedEntityLinkType.ADDED));
                continue;
            }
            var srcVal = srcMap.get(key);
            if (srcVal.equals(link)) {
                changedLinks.add(new ChangedEntityLink(link, ChangedEntityLinkType.SAME));
            }
        }
        return new ChangedEntitiesLinksOutput(changedLinks);
    }

    private static Map<LinkKey, EntityLink> transformToMap(List<EntityLink> links) {
        return links.stream()
                .collect(Collectors.toMap(
                        link -> new LinkKey(
                                link.source(),
                                link.target(),
                                link.msSource(),
                                link.msTarget()
                        ),
                        link -> link
                ));
    }

}
