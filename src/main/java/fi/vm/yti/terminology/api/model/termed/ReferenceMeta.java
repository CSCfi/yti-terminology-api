package fi.vm.yti.terminology.api.model.termed;

import fi.vm.yti.terminology.api.migration.PropertyUtil;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static fi.vm.yti.terminology.api.migration.PropertyUtil.prefLabel;
import static java.util.Collections.emptyMap;

public final class ReferenceMeta {

    private final TypeId range;
    private final String id;
    private final String uri;
    private final Long index;
    private final TypeId domain;
    private final Map<String, List<Permission>> permissions;
    private final Map<String, List<Property>> properties;

    // Jackson constructor
    private ReferenceMeta() {
        this(TypeId.placeholder(), "", "", 0L, TypeId.placeholder(), emptyMap(), emptyMap());
    }

    public ReferenceMeta(TypeId range,
                         String id,
                         String uri,
                         Long index,
                         TypeId domain,
                         Map<String, List<Permission>> permissions,
                         Map<String, List<Property>> properties) {
        this.range = range;
        this.id = id;
        this.uri = uri;
        this.index = index;
        this.domain = domain;
        this.permissions = permissions;
        this.properties = properties;
    }

    public TypeId getRange() {
        return range;
    }

    public String getId() {
        return id;
    }

    public String getUri() {
        return uri;
    }

    public Long getIndex() {
        return index;
    }

    public TypeId getDomain() {
        return domain;
    }

    public Map<String, List<Permission>> getPermissions() {
        return permissions;
    }

    public Map<String, List<Property>> getProperties() {
        return properties;
    }

    public void updateLabel(String fi, String en) {
        updateProperties(prefLabel(fi, en));
    }

    public void updateProperties(Map<String, List<Property>> updatedProperties) {

        Map<String, List<Property>> newProperties = PropertyUtil.merge(this.properties, updatedProperties);

        properties.clear();
        properties.putAll(newProperties);
    }

    public ReferenceMeta copyToGraph(UUID graphId) {

        TypeId newDomain = domain.copyToGraph(graphId);
        TypeId newRange = domain.getGraph().equals(range.getGraph()) ? range.copyToGraph(graphId) : range;

        return new ReferenceMeta(newRange, id, uri, index, newDomain, permissions, properties);
    }
}
