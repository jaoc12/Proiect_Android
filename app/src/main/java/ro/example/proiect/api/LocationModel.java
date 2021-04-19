package ro.example.proiect.api;

import java.util.List;

public class LocationModel {
    String group;
    List<EntityLocation> entities;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<EntityLocation> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityLocation> entities) {
        this.entities = entities;
    }
}
