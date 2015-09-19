package me.rbw.model;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;
import me.rbw.web.RbwAuth;

@Entity
public class Motorbike {

    @Id
    private String id;
    private String name;
    @Parent()
    private Key<User> owner;
    private String vin;

    private Motorbike() {
    }

    public Motorbike(String ownerId) {
        owner = Key.create(User.class, ownerId);
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

    public static MotorbikeBuilder create(String ownerId) {
        return new MotorbikeBuilder(ownerId);
    }

    public static class MotorbikeBuilder {
        private final Motorbike target;

        public MotorbikeBuilder(String ownerId) {
            target = new Motorbike(ownerId);
            target.id = RbwAuth.generateUUID();
        }

        public MotorbikeBuilder withFriendlyName(String friendlyName) {
            target.name = friendlyName;
            return this;
        }

        public MotorbikeBuilder withVIN(String vin) {
            target.vin = vin;
            return this;
        }

        public Motorbike build() {
            return target;
        }
    }
}
