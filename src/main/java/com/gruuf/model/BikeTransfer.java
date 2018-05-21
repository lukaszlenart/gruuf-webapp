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
public class BikeTransfer {

    @Id
    private String id;

    private Ref<Bike> bike;
    @Index
    private String emailAddress;
    private Date registerDate;
    private Date transferDate;

    public static BikeTransfer create(Bike bike, String emailAddress) {
        BikeTransfer bikeTransfer = new BikeTransfer();
        bikeTransfer.id = GruufAuth.generateUUID();
        bikeTransfer.bike = Ref.create(bike);
        bikeTransfer.emailAddress = emailAddress;
        bikeTransfer.registerDate = new Date();
        return bikeTransfer;
    }

    public BikeTransfer markAsDone() {
        this.transferDate = new Date();
        return this;
    }
}
