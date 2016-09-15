package com.gruuf.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.web.RbwAuth;

@Entity
public class Bike {

    @Id
    private String id;
    private String name;
    @Index
    private Ref<User> owner;
    @Index
    private String vin;

    private Bike() {
    }

    public Bike(User owner) {
        this.owner = Ref.create(owner);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVin() {
        return vin;
    }

    public User getOwner() {
        return owner.get();
    }

    @Override
    public String toString() {
        return "Bike{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                ", vin='" + vin + '\'' +
                '}';
    }

    public static BikeBuilder create(User owner) {
        return new BikeBuilder(owner);
    }

    public static class BikeBuilder {
        private final Bike target;

        public BikeBuilder(User owner) {
            target = new Bike(owner);
            target.id = RbwAuth.generateUUID();
        }

        public BikeBuilder withFriendlyName(String friendlyName) {
            target.name = friendlyName;
            return this;
        }

        public BikeBuilder withVIN(String vin) {
            target.vin = vin;
            return this;
        }

        public Bike build() {
            return target;
        }
    }
}
