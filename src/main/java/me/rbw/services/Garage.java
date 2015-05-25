package me.rbw.services;

import me.rbw.model.Motorbike;
import me.rbw.model.User;

import java.util.ArrayList;
import java.util.List;

public class Garage {

    public List<Motorbike> getMotorbikes(final User owner) {
        return new ArrayList<Motorbike>() {
            {
                add(new Motorbike("1234", "M1", owner));
                add(new Motorbike("12345", "M2", owner));
            }
        };
    }

}
