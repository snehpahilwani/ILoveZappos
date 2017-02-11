package com.zappos.ilovezappos;

import java.util.List;

public class Product {

    private String originalTerm;
    private String currentResultCount;
    private String totalResultCount;
    private String term;
    private List<Result> results = null;
    private String statusCode;

    public String getOriginalTerm() {
        return originalTerm;
    }

    public void setOriginalTerm(String originalTerm) {
        this.originalTerm = originalTerm;
    }

    public String getCurrentResultCount() {
        return currentResultCount;
    }

    public void setCurrentResultCount(String currentResultCount) {
        this.currentResultCount = currentResultCount;
    }

    public String getTotalResultCount() {
        return totalResultCount;
    }

    public void setTotalResultCount(String totalResultCount) {
        this.totalResultCount = totalResultCount;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}