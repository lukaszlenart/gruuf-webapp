package com.gruuf.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.gruuf.web.GruufAuth;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BikeParameter {

    @Id
    private String id;
    @Index
    private Ref<BikeMetadata> bikeMetadata;

    private Markdown description;
    private String value;
    private String partNumber;

    private Ref<User> requestedBy;
    private Date registered;
    private boolean approved;

    public static Input create(BikeMetadata bikeMetadata) {
        return new Input(bikeMetadata);
    }

    public static class Input {

        private final BikeParameter parameter;

        public Input(BikeMetadata bikeMetadata) {
            parameter = new BikeParameter();
            parameter.id = GruufAuth.generateUUID();
            parameter.approved = false;
            parameter.registered = new Date();
            parameter.bikeMetadata = Ref.create(bikeMetadata);
        }

        public Input withDescription(Markdown description) {
            parameter.description = description;
            return this;
        }

        public Input withValue(String value) {
            parameter.value = value;
            return this;
        }

        public Input withPartNumber(String partNumber) {
            parameter.partNumber = partNumber;
            return this;
        }

        public Input withRequestedBy(User user) {
            parameter.requestedBy = Ref.create(user);
            return this;
        }

        public BikeParameter build() {
            return parameter;
        }
    }
}
