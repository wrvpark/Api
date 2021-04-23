package com.wrvpark.apiserver.repository.search;

import com.wrvpark.apiserver.domain.Item;

/**
 * @author Vahid Haghighat
 */
public class ItemSpecification <T extends Comparable<T>> extends EntitySpecification<Item, T> {
    public ItemSpecification(LogicalOperations chain) {
        super(chain);
    }
}