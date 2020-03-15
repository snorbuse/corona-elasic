package se.snorbuse.coronaimporter.model;

import se.snorbuse.coronaimporter.util.Logger;

import java.time.LocalDate;

public class CombinedDatapoint {
    private String id;
    private String province;
    private String country;
    private int confirmed;
    private int deaths;
    private int recovered;
    private LocalDate date;
    private Location location;

    public CombinedDatapoint(Datapoint datapoint, int confirmed, int deaths, int recovered) {
        this.id = datapoint.getId();
        this.province = datapoint.getProvince();
        this.country = datapoint.getCountry();
        this.date = datapoint.getDate();
        this.location = datapoint.getLocation();

        this.confirmed = confirmed;
        this.deaths = deaths;
        this.recovered = recovered;
    }

    public String getId() {
        return id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String toString() {
        return String.format(
                "%-12s Confirmed: %-5d Deaths: %-4d Recovered: %-4d %s",
                date.toString(),
                confirmed,
                deaths,
                recovered,
                country
        );
    }
}
