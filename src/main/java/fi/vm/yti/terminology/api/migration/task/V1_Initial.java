package fi.vm.yti.terminology.api.migration.task;

import fi.vm.yti.terminology.api.migration.AttributeIndex;
import fi.vm.yti.terminology.api.migration.MigrationService;
import fi.vm.yti.terminology.api.migration.PropertyIndex;
import fi.vm.yti.terminology.api.migration.ReferenceIndex;
import fi.vm.yti.terminology.api.migration.framework.MigrationTask;
import fi.vm.yti.terminology.api.model.termed.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static fi.vm.yti.terminology.api.migration.DomainIndex.*;
import static fi.vm.yti.terminology.api.migration.PropertyIndex.prefLabel;
import static fi.vm.yti.terminology.api.migration.PropertyIndex.type;
import static fi.vm.yti.terminology.api.migration.PropertyUtil.localizable;
import static fi.vm.yti.terminology.api.migration.PropertyUtil.merge;
import static java.util.Arrays.asList;
import static java.util.Collections.*;

@Component
public class V1_Initial implements MigrationTask {

    private final MigrationService migrationService;

    @Autowired
    V1_Initial(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @Override
    public void migrate() {
        initializeGroups();
        initializeOrganizations();
        initializeVocabularyTemplate();
        initializeTerminologicalVocabularyTemplate();
    }

    private void initializeGroups() {

        TypeId domain = GROUP_DOMAIN;

        Graph graph = new Graph(domain.getGraphId(), null, "http://urn.fi/URN:NBN:fi:au:ptvl:", emptyList(), emptyMap(), merge(
                PropertyIndex.prefLabel("Termed ryhmät"),
                localizable("note", "Termed ryhmäjaottelu vastaa Julkisten palvelujen luokitusta: http://finto.fi/ptvl")
        ));

        List<MetaNode> metaNodes = new ArrayList<>();

        metaNodes.add(new MetaNode(
                "Group",
                "http://www.w3.org/2004/02/skos/core#Concept",
                1L,
                domain.getGraph(),
                emptyMap(),
                prefLabel(
                        "Luokitus",
                        "Classification"
                ),
                asList(
                        AttributeIndex.prefLabelSkos(domain, 0),
                        AttributeIndex.definition(domain, 1, false),
                        AttributeIndex.order(domain, 2),
                        AttributeIndex.notation(domain, 3,
                                "Koodiarvo",
                                "Code value"
                        )
                ),
                singletonList(
                        ReferenceIndex.broader(GROUP_DOMAIN, 0,"Yläluokka", "Broader")
                )
        ));

        migrationService.createGraph(graph);
        migrationService.updateTypes(graph.getId(), metaNodes);
        migrationService.updateNodesWithJson(new ClassPathResource("migration/groupNodes.json"));
    }

    private void initializeOrganizations() {

        TypeId domain = ORGANIZATION_DOMAIN;

        Graph graph = new Graph(domain.getGraphId(), null, null, emptyList(), emptyMap(), prefLabel("Termed organisaatiot"));

        List<MetaNode> metaNodes = new ArrayList<>();

        metaNodes.add(new MetaNode(
                "Organization",
                null,
                1L,
                domain.getGraph(),
                emptyMap(),
                prefLabel(
                        "Organisaatio",
                        "Organization"
                ),
                singletonList(
                        AttributeIndex.prefLabelSkos(domain, 0)
                ),
                emptyList()
        ));

        migrationService.createGraph(graph);
        migrationService.updateTypes(graph.getId(), metaNodes);
    }

    private void initializeVocabularyTemplate() {

        Graph graph = new Graph(VOCABULARY_GRAPH_ID, null, null, emptyList(), emptyMap(), merge(
                prefLabel("Asiasanasto"),
                type("Metamodel")
        ));

        List<MetaNode> metaNodes = new ArrayList<>();

        metaNodes.add(createVocabularyMeta());
        metaNodes.add(createCollectionMeta());
        metaNodes.add(createConceptMeta());

        migrationService.createGraph(graph);
        migrationService.updateTypes(graph.getId(), metaNodes);
    }

    private void initializeTerminologicalVocabularyTemplate() {

        Graph graph = new Graph(TERMINOLOGICAL_VOCABULARY_GRAPH_ID, null, null, emptyList(), emptyMap(), merge(
                prefLabel("Terminologinen sanasto"),
                type("Metamodel")
        ));

        List<MetaNode> metaNodes = new ArrayList<>();

        metaNodes.add(createTerminologicalVocabularyMeta());
        metaNodes.add(createTerminologicalCollectionMeta());
        metaNodes.add(createTermMeta());
        metaNodes.add(createTerminologicalConceptMeta());
        metaNodes.add(createTerminologicalConceptLinkMeta());

        migrationService.createGraph(graph);
        migrationService.updateTypes(graph.getId(), metaNodes);
    }

    @NotNull
    private MetaNode createTerminologicalConceptLinkMeta() {

        TypeId domain = TERMINOLOGICAL_CONCEPT_LINK_DOMAIN;

        return new MetaNode(
                "ConceptLink",
                "http://www.w3.org/1999/02/22-rdf-syntax-ns#Resource",
                4L,
                domain.getGraph(),
                emptyMap(),
                merge(
                        prefLabel(
                                "Linkitetty käsite",
                                "Linked concept"
                        ),
                        type("abstract")
                ),
                asList(
                        AttributeIndex.prefLabelSkos(domain, 0),
                        new AttributeMeta(
                                "vocabularyLabel",
                                "http://iow.csc.fi/ns/st#vocabularyLabel",
                                1L,
                                domain,
                                emptyMap(),
                                merge(
                                        prefLabel(
                                                "Sanaston nimi",
                                                "Vocabulary label"
                                        ),
                                        type("localizable:single")
                                )

                        ),
                        new AttributeMeta(
                                "targetId",
                                "http://iow.csc.fi/ns/st#targetId",
                                2L,
                                domain,
                                emptyMap(),
                                prefLabel(
                                        "Viitatun käsitteen id",
                                        "Target identifier"
                                )

                        ),
                        new AttributeMeta(
                                "targetGraph",
                                "http://iow.csc.fi/ns/st#targetUri",
                                3L,
                                domain,
                                emptyMap(),
                                prefLabel(
                                        "Viitatun käsitteen uri",
                                        "Target uri"
                                )
                        )
                ),
                emptyList()
        );
    }

    @NotNull
    private MetaNode createTerminologicalConceptMeta() {

        TypeId domain = TERMINOLOGICAL_CONCEPT_DOMAIN;

        return new MetaNode(
                "Concept",
                "http://www.w3.org/2004/02/skos/core#Concept",
                3L,
                domain.getGraph(),
                emptyMap(),
                prefLabel(
                        "Käsite",
                        "Concept"
                ),
                asList(
                        AttributeIndex.definition(domain, 2, true),
                        AttributeIndex.note(domain, 3),
                        AttributeIndex.editorialNote(domain, 4),
                        AttributeIndex.example(domain, 5),
                        AttributeIndex.changeNote(domain, 6),
                        AttributeIndex.historyNote(domain, 7),
                        AttributeIndex.status(domain, 8,
                                "Käsitteen tila",
                                "Concept status"
                        ),
                        AttributeIndex.notation(domain, 9,
                                "Systemaattinen merkintätapa",
                                "Notatation"
                        ),
                        AttributeIndex.source(domain, 10)
                ),
                asList(
                        new ReferenceMeta(
                                TERM_DOMAIN,
                                "prefLabelXl",
                                "http://www.w3.org/2008/05/skos-xl#prefLabel",
                                0L,
                                domain,
                                emptyMap(),
                                prefLabel(
                                        "Suositettava nimi",
                                        "Preferred term"
                                )
                        ),
                        new ReferenceMeta(
                                TERM_DOMAIN,
                                "altLabelXl",
                                "http://iow.csc.fi/ns/st#synonym",
                                1L,
                                domain,
                                emptyMap(),
                                prefLabel(
                                        "Synonyymi",
                                        "Synonym"
                                )
                        ),
                        ReferenceIndex.broader(domain, 11,
                                "Hierarkkinen yläkäsite",
                                "Broader concept"
                        ),
                        ReferenceIndex.relatedConcept(domain, 12),
                        ReferenceIndex.partOfConcept(domain, 13),
                        ReferenceIndex.relatedMatch(domain, TERMINOLOGICAL_CONCEPT_LINK_DOMAIN,14),
                        ReferenceIndex.exactMatch(domain, TERMINOLOGICAL_CONCEPT_LINK_DOMAIN, 15),
                        ReferenceIndex.closeMatch(domain, TERMINOLOGICAL_CONCEPT_LINK_DOMAIN, 16)
                )
        );
    }

    @NotNull
    private MetaNode createConceptMeta() {

        TypeId domain = CONCEPT_DOMAIN;

        return new MetaNode(
                "Concept",
                "http://www.w3.org/2004/02/skos/core#Concept",
                1L,
                domain.getGraph(),
                emptyMap(),
                prefLabel(
                        "Käsite",
                        "Concept"
                ),
                asList(
                        AttributeIndex.prefLabelTerm(domain, 0),
                        AttributeIndex.altLabel(domain, 1),
                        AttributeIndex.hiddenLabel(domain, 2),
                        AttributeIndex.note(domain, 3),
                        AttributeIndex.scopeNote(domain, 4),
                        AttributeIndex.definition(domain, 5, false),
                        AttributeIndex.example(domain, 6),
                        AttributeIndex.historyNote(domain, 7),
                        AttributeIndex.editorialNote(domain, 8),
                        AttributeIndex.changeNote(domain, 9)
                ),
                asList(
                        ReferenceIndex.broader(domain, 11,
                                "Yläkäsite",
                                "Broader concept"
                        ),
                        ReferenceIndex.narrower(domain, 12,
                                "Alakäsite",
                                "Narrower concept"
                        ),
                        ReferenceIndex.relatedConcept(domain, 13)
                )
        );
    }

    @NotNull
    private MetaNode createTermMeta() {

        TypeId domain = TERM_DOMAIN;

        return new MetaNode(
                "Term",
                "http://www.w3.org/2008/05/skos-xl#Label",
                2L,
                domain.getGraph(),
                emptyMap(),
                prefLabel(
                        "Termi",
                        "Term"
                ),
                asList(
                        new AttributeMeta(
                                "prefLabel",
                                "http://www.w3.org/2008/05/skos-xl#literalForm",
                                0L,
                                domain,
                                emptyMap(),
                                prefLabel(
                                        "Termin arvo",
                                        "Term literal value"
                                )

                        ),
                        AttributeIndex.source(domain, 1),
                        AttributeIndex.scope(domain, 2),
                        AttributeIndex.editorialNote(domain, 3),
                        AttributeIndex.draftComment(domain, 4),
                        AttributeIndex.historyNote(domain, 5),
                        AttributeIndex.changeNote(domain, 6),
                        AttributeIndex.status(domain, 7,
                                "Termin tila",
                                "Term status"
                        )
                ),
                emptyList()
        );
    }

    @NotNull
    private MetaNode createTerminologicalCollectionMeta() {

        TypeId domain = TERMINOLOGICAL_COLLECTION_DOMAIN;

        return new MetaNode(
                "Collection",
                "http://www.w3.org/2004/02/skos/core#Collection",
                1L,
                domain.getGraph(),
                emptyMap(),
                prefLabel(
                        "Käsitevalikoima",
                        "Collection"
                ),
                asList(
                        AttributeIndex.prefLabelDcTitle(domain, 0),
                        AttributeIndex.definition(domain, 1, false)
                ),
                asList(
                        new ReferenceMeta(
                                TERMINOLOGICAL_CONCEPT_DOMAIN,
                                "broader",
                                "http://www.w3.org/2004/02/skos/core#broader",
                                0L,
                                domain,
                                emptyMap(),
                                merge(
                                        prefLabel(
                                                "Jaottelu yläkäsitteen mukaan",
                                                "Collection broader"
                                        ),
                                        type("reference:single")
                                )
                        ),
                        ReferenceIndex.member(domain, TERMINOLOGICAL_CONCEPT_DOMAIN, 1)
                )
        );
    }

    @NotNull
    private MetaNode createCollectionMeta() {

        TypeId domain = COLLECTION_DOMAIN;

        return new MetaNode(
                "Collection",
                "http://www.w3.org/2004/02/skos/core#Collection",
                2L,
                domain.getGraph(),
                emptyMap(),
                prefLabel(
                        "Käsitevalikoima",
                        "Collection"
                ),
                asList(
                        AttributeIndex.prefLabelTerm(domain, 1),
                        AttributeIndex.altLabel(domain, 1),
                        AttributeIndex.hiddenLabel(domain, 2),
                        AttributeIndex.note(domain, 3),
                        AttributeIndex.scopeNote(domain, 4),
                        AttributeIndex.definition(domain, 5, false),
                        AttributeIndex.example(domain, 6),
                        AttributeIndex.historyNote(domain, 7),
                        AttributeIndex.editorialNote(domain, 8),
                        AttributeIndex.changeNote(domain, 9)
                ),
                singletonList(
                        ReferenceIndex.member(domain, CONCEPT_DOMAIN, 10)
                )
        );
    }

    @NotNull
    private MetaNode createTerminologicalVocabularyMeta() {

        TypeId domain = TERMINOLOGICAL_VOCABULARY_DOMAIN;

        return new MetaNode(
                "TerminologicalVocabulary",
                "http://www.w3.org/2004/02/skos/core#ConceptScheme",
                0L,
                domain.getGraph(),
                emptyMap(),
                prefLabel(
                        "Terminologinen sanasto",
                        "Terminological Dictionary"
                ),
                asList(
                        AttributeIndex.prefLabelDcTitle(domain, 0),
                        AttributeIndex.language(domain, 1),
                        AttributeIndex.status(domain, 2,
                                "Sanaston tila",
                                "Terminology status"
                        ),
                        AttributeIndex.description(domain, 3)
                ),
                asList(
                        ReferenceIndex.publisher(domain, 0),
                        ReferenceIndex.group(domain, 1)
                )
        );
    }

    @NotNull
    private MetaNode createVocabularyMeta() {

        TypeId domain = VOCABULARY_DOMAIN;

        return new MetaNode(
                "Vocabulary",
                "http://www.yso.fi/onto/yso-meta/Thesaurus",
                0L,
                domain.getGraph(),
                emptyMap(),
                prefLabel(
                        "Asiasanasto",
                        "Thesaurus"
                ),
                asList(
                        AttributeIndex.prefLabelRdfSchema(domain, 0),
                        AttributeIndex.description(domain, 1),
                        AttributeIndex.language(domain, 2),
                        AttributeIndex.status(domain, 3,
                                "Sanaston tila",
                                "Terminology status"
                        )

                ),
                asList(
                        ReferenceIndex.publisher(domain, 0),
                        ReferenceIndex.group(domain, 1)
                )
        );
    }
}