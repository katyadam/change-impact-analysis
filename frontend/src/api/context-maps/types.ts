export type ContextMap = {
    nodes: Node[];
    links: Link[];
}

export type ContextMapDto = {
    id: number;
    projectId: number;
    version: string;
    commitHash: string;
    createdAt: string
}

export type CreateContextMapRequest = {
    projectId: string;
    version: string;
    commitHash: string;
    contextMap: ContextMap;
}

export type FieldAnnotation = {
    annotation: string;
}

export type Field = {
    fieldName: string;
    fieldFullName: string;
    fieldType: string;
    fieldAnnotations: FieldAnnotation[];
    fieldIsReference: boolean;
    fieldEntityRefName: string;
    isCollection: boolean;
}

export type Node = {
    msName: string;
    nodeName: string;
    nodeFullName: string;
    fields: Field[];
}

export type Link = {
    source: string;
    target: string;
    msSource: string;
    msTarget: string;
    sourceMultiplicity: string;
    targetMultiplicity: string;
}

export type ChangedLink = Link & {
    type: ChangeLinkType;
};

export enum ChangeLinkType {
    SAME, ADDED, REMOVED, MODIFIED
}

export type ContextMapWithChangedLinks = {
    nodes: Node[];
    links: ChangedLink[];
}

export type ChangedDto = {
    id: number;
    changedLinks: ChangedLink[];
    createdAt: string;
}
export type ChangedLinksResponse = {
    changedLinks: ChangedLink[]
}

export type Summary = {
    totalChangedContextMaps: number;
}