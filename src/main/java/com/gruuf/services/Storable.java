package com.gruuf.services;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;
import com.gruuf.model.Attachment;
import com.gruuf.model.Bike;
import com.gruuf.model.BikeEvent;
import com.gruuf.model.BikeMetadata;
import com.gruuf.model.BikeParameter;
import com.gruuf.model.BikeRecommendation;
import com.gruuf.model.BikeTransfer;
import com.gruuf.model.EventType;
import com.gruuf.model.User;

import java.util.ArrayList;
import java.util.List;

public abstract class Storable<E> {

    static {
        ObjectifyService.register(User.class);
        ObjectifyService.register(EventType.class);
        ObjectifyService.register(BikeEvent.class);
        ObjectifyService.register(Bike.class);
        ObjectifyService.register(Attachment.class);
        ObjectifyService.register(BikeMetadata.class);
        ObjectifyService.register(BikeRecommendation.class);
        ObjectifyService.register(BikeParameter.class);
        ObjectifyService.register(BikeTransfer.class);
    }

    private final Class<E> type;

    public Storable(Class<E> type) {
        this.type = type;
    }

    public E get(String entityId) {
        return ObjectifyService
                .ofy()
                .load()
                .type(type)
                .id(entityId)
                .now();
    }

    public E put(E newEntity) {
        Key<E> entityKey = ObjectifyService
                .ofy()
                .save()
                .entity(newEntity)
                .now();

        return Ref.create(entityKey).get();
    }

    public List<E> list(String orderBy) {
        return ObjectifyService
                .ofy()
                .load()
                .type(type)
                .order(orderBy)
                .list();
    }

    public List<E> list() {
        return ObjectifyService
                .ofy()
                .load()
                .type(type)
                .list();
    }

    protected Query<E> filter(String condition, Object value) {
        return ObjectifyService
                .ofy()
                .load()
                .type(type)
                .filter(condition, value);
    }

    protected List<E> listIn(String fieldName, Object value, String inName, Object... ins) {
        List<E> result = new ArrayList<>();
        for (Object in : ins) {
            result.addAll(filter(fieldName + " =", value).filter(inName + " =", in).list());
        }
        return result;
    }

    public List<E> findBy(String property, Object value) {
        return filter(property, value).list();
    }

    public E findUniqueBy(String property, Object value) {
        return filter(property, value)
                .limit(1)
                .first()
                .now();
    }

    public Void drop(String entityId) {
        return ObjectifyService
                .ofy()
                .delete()
                .type(type)
                .id(entityId)
                .now();
    }
}
