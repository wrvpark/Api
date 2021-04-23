package com.wrvpark.apiserver.repository.search;

/**
 * @author Vahid Haghighat
 */
public class SearchCriteria<T extends Comparable<T>> {
    private String key;
    private T value;
    private SearchOperation operation;

    public SearchCriteria() {}

    public SearchCriteria(String key, T value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }
}