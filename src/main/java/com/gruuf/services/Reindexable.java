package com.gruuf.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class Reindexable<E> extends Storable<E> {

    private static final Logger LOG = LogManager.getLogger(Reindexable.class);

    public Reindexable(Class<E> type) {
        super(type);
    }

    public void reindex() {
        LOG.debug("Reindexing {}", getClass().getSimpleName());

        List<E> entities = list();
        for (E entity : entities) {
            put(entity);
        }
    }

}
