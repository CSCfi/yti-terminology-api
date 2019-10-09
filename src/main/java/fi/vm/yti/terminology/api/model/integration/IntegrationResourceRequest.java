package fi.vm.yti.terminology.api.model.integration;

import java.util.Date;
import java.util.Set;

public class IntegrationResourceRequest {
/**
 * {
   "searchTerm":"string",
   "language":"string",
   "container":"string",
   "status": [
       "string"    
   ],
   "after":"2019-09-11T09:27:29.964Z",
   "filter":[
      "string"
   ],
   "pageSize":0,
   "pageFrom":0
}
 */
    private String searchTerm;
    private String language;
    private String container;
    private Set<String> status;
    private Date after;
    private Set<String> filter;
    private Set<String> uri;
    private boolean includeIncomplete;
    private Set<String> includeIncompleteFrom;                                      

    private Integer pageSize;
    private Integer pageFrom;

    public IntegrationResourceRequest(){}

    public IntegrationResourceRequest(final String container,
                                      final String searchTerm,
                                      final Set<String> uri,
                                      final String language,
                                      final Set<String> status,
                                      final Date after,
                                      final Set<String> filter,
                                      final boolean includeIncomplete,
                                      final Set<String> includeIncompleteFrom,                                      
                                      final Integer pageSize,
                                      final Integer pageFrom) {
        this.searchTerm = searchTerm;
        this.language = language;
        this.container = container;
        this.status = status;
        this.uri = uri;
        this.after = after;
        this.filter = filter;
        this.includeIncomplete = includeIncomplete;
        this.includeIncompleteFrom = includeIncompleteFrom;
        this.pageSize = pageSize;
        this.pageFrom = pageFrom;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(final String container) {
        this.container = container;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(final String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(final String language) {
        this.language = language;
    }

    public Set<String> getStatus() {
        return status;
    }

    public void setUri(final Set<String> uri) {
        this.uri = uri;
    }

    public Set<String> getUri() {
        return uri;
    }

    public void setStatus(final Set<String> status) {
        this.status = status;
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
            ", container='" + container + '\'' +
            ", uri='" + uri + '\'' +
            ", status='" + status + '\'' +
            ", after='" + after + '\'' +
            ", filter=" + filter +
            ", includeIncomplete=" + includeIncomplete +
            ", includeIncompleteFrom=" + includeIncompleteFrom +
            ", pageSize=" + pageSize +
            ", pageFrom=" + pageFrom +
            '}';
    }
}
