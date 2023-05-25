package com.example.planzy;

import java.util.ArrayList;
import java.util.List;

public class PlanModel {
    String ID;
    String name;
    String location;
    List<String> locations;
    String date;
    String time;

    List<String> participants;

    String createdBy;

    public PlanModel() {

    }

    public PlanModel(String name, List<String> locations, String date, String time, String createdBy, List<String> participants) {
        this.name = name;
        this.locations = locations;
        this.location = "TBD";
        this.date = date;
        this.time = time;
        this.createdBy = createdBy;
        this.participants = participants;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreatedBy() { return createdBy; }

    public void setCreatedBy(String createdBy){ this.createdBy = createdBy; }

    public List<String> getParticipants(){ return this.participants; }

    public void setParticipants(List<String> participants){ this.participants = participants; }

    public void setDocumentID(String id){ this.ID = id; }

    public String getDocumentID(){ return this.ID; }
}
