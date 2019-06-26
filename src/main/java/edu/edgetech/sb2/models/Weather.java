package edu.edgetech.sb2.models;

import java.util.Arrays;
import java.util.Map;

public class Weather {
    private Map<String, Float> coord;
    private Map<String, String>[] weather;
    private String base;
    private Map<String, Float> main;
    private int visibility;
    private Map<String, Float> wind;
    private Map<String, Float> clouds;
    private String dt;
    private Map<String, String> sys;
    private int timezone;
    private long id;
    private String name;
    private int cod;

    public Weather() {
    }

    @Override
    public String toString() {
        return "{Weather:{" +
                "coord:" + coord +
                ", weather:" + Arrays.toString(weather) +
                ", base:'" + base + '\'' +
                ", main:" + main +
                ", visibility:" + visibility +
                ", wind:" + wind +
                ", clouds:" + clouds +
                ", dt:'" + dt + '\'' +
                ", sys:" + sys +
                ", timezone:" + timezone +
                ", id:" + id +
                ", name:'" + name + '\'' +
                ", cod:" + cod +
                "}}";
    }

    public Map<String, Float> getCoord() {
        return coord;
    }

    public void setCoord(Map<String, Float> coord) {
        this.coord = coord;
    }

    public Map<String, String>[] getWeather() {
        return weather;
    }

    public void setWeather(Map<String, String>[] weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Float> getMain() {
        return main;
    }

    public void setMain(Map<String, Float> main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Map<String, Float> getWind() {
        return wind;
    }

    public void setWind(Map<String, Float> wind) {
        this.wind = wind;
    }

    public Map<String, Float> getClouds() {
        return clouds;
    }

    public void setClouds(Map<String, Float> clouds) {
        this.clouds = clouds;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public Map<String, String> getSys() {
        return sys;
    }

    public void setSys(Map<String, String> sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
}
