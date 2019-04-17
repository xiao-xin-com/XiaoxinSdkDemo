package com.example.myapplication;

import com.xiaoxin.sdk.data.Person;

public class Device {
    private String guardian;
    private Person unGuardian;

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public Person getUnGuardian() {
        return unGuardian;
    }

    public void setUnGuardian(Person unGuardian) {
        this.unGuardian = unGuardian;
    }
}
