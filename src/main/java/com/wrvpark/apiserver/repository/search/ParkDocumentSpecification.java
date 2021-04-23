package com.wrvpark.apiserver.repository.search;

import com.wrvpark.apiserver.domain.ParkDocument;

import java.util.Date;

/**
 * @author Vahid Haghighat
 */
public class ParkDocumentSpecification<T extends Comparable<T>> extends EntitySpecification<ParkDocument, T> {
    public ParkDocumentSpecification(LogicalOperations chain) {
        super(chain);
    }
}
