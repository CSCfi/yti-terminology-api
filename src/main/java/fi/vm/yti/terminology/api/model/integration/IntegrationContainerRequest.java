package fi.vm.yti.terminology.api.model.integration;

import java.util.Date;
import java.util.Set;

public class IntegrationContainerRequest {

    private String searchTerm;
    private String language;
    private boolean includeIncomplete;
    private Set<String> includeIncompleteFrom;
    private Set<String> status;
    private Set<String> uri;
    private Date after;
    private Set<String> filter;
    private Integer pageSize;
    private Integer pageFrom;

    public IntegrationContainerRequest(){}

    public IntegrationContainerRequest(final String searchTerm,
                                      final String language,
                                      final boolean includeIncomplete,
                                      final Set<String> includeIncompleteFrom,                                      
                                      final Set<String> status,
                                      final Date after,
                                      final Set<String> filter,
                                      final Set<String> uri,
                                      final Integer pageSize,
                                      final Integer pageFrom) {
        this.searchTerm = searchTerm;
        this.language = language;
        this.includeIncomplete = includeIncomplete;
        this.includeIncompleteFrom = includeIncompleteFrom;
        this.status = status;
        this.uri = uri;
        this.after = after;
        this.filter = filter;
        this.pageSize = pageSize;
        this.pageFrom = pageFrom;
    }
    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(final String searchTerm) {
        this.searchTerm = searchTerm;
    }

    /**
     * @return String return the language
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return boolean return the includeIncomplete
     */
    public boolean getIncludeIncomplete() {
        return includeIncomplete;
    }

    public void setIncludeIncomplete(final boolean includeIncomplete) {
        this.includeIncomplete = includeIncomplete;
    }


    public Set<String> getIncludeIncompleteFrom() {
        return includeIncompleteFrom;
    }

    public void setIncludeIncompleteFrom(final Set<String> incompl) {
        this.includeIncompleteFrom = incompl;
    }

    public Set<String> getStatus() {
        return status;
    }

    public void setStatus(final Set<String> status) {
        this.status = status;
    }

    public Set<String> getUri() {
        return uri;
    }

    public void setUri(final Set<String> uri) {
        this.uri = uri;
    }

    public Date getAfter() {
        return after;
    }

    public void setAfter(final Date after) {
        this.after = after;
    }

    public Set<String> getFilter() {
        return filter;
    }

    public void setFilter(final Set<String> filter) {
        this.filter = filter;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(final Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageFrom() {
        return pageFrom;
    }

    public void setPageFrom(final Integer pageFrom) {
        this.pageFrom = pageFrom;
    }

    @Override
    public String toString() {
        return "IntegrationResourceRequest{" +
            "searchTerm='" + searchTerm + '\'' +
            ", language='" + language + '\'' +
            ", status='" + status + '\'' +
            ", after='" + after + '\'' +
            ", filter=" + filter +
            ", pageSize=" + pageSize +
            ", pageFrom=" + pageFrom +
            '}';
    }
}
