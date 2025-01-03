export type FieldAnnotation = {
    annotation: string;
}

export type EntityField = {
    fieldName: string;
    fieldFullName: string;
    fieldType: string;
    fieldAnnotations: FieldAnnotation[];
    fieldIsReference: boolean;
    fieldEntityRefName: string;
    isCollection: boolean;
}

export type EntityNode = {
    msName: string;
    nodeName: string;
    nodeFullName: string;
    fields: EntityField[];
}

export type EntityLink = {
    source: string;
    target: string;
    msSource: string;
    msTarget: string;
    sourceMultiplicity: string;
    targetMultiplicity: string;
}

export type GraphData = {
    nodes: EntityNode[];
    links: EntityLink[];
}

export type ChangedEntityLink = EntityLink & {
    type: ChangedEntityLinkType;
};

export enum ChangedEntityLinkType {
    SAME, ADDED, REMOVED, MODIFIED
}

export type GraphDataChangedLinks = {
    nodes: EntityNode[];
    links: ChangedEntityLink[];
}

export type EntitiesDiff = {
    id: number;
    changedLinks: ChangedEntityLink[];
    createdAt: string;
}
export type CompareEntitiesLinksResponse = {
    changedLinks: ChangedEntityLink[]
}
