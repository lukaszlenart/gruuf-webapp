package com.gruuf.model;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
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

}
