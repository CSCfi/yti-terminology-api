package fi.vm.yti.terminology.api.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReindexController {

    private final IndexElasticSearchService elasticSearchService;

    @Autowired
    public ReindexController(IndexElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    @RequestMapping("/reindex")
    public String reindex() {
        this.elasticSearchService.reindex();
        return "OK!";
    }
}
