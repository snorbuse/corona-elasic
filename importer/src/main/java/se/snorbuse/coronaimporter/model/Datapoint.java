package se.snorbuse.coronaimporter.model;

import se.snorbuse.coronaimporter.util.HashHelper;

import java.time.LocalDate;

public class Datapoint {
    private String province;
    private String country;
    private int count;
    private LocalDate date;
    private Location location;

    public Datapoint(String province, String country, int count, LocalDate date, Location location) {
        this.province = province;
        this.country = country;
        this.count = count;
        this.date = date;
        this.location = location;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public int getCount() {
        return count;
    }

    public LocalDate getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public String getId() {
        String id = String.format(
                "%s-%s-%s",
                country,
                province,
                date.toString()
        );

        return HashHelper.sha256(id);
    }
}
