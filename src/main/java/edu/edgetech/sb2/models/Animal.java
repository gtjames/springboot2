package edu.edgetech.sb2.models;

import java.util.Map;

public class Animal {
    private String id;
    private String organization_id;
    private String url;
    private String type;
    private String species;
    private Map<String, String> breeds;
    private Map<String, String> colors;
    private String age;
    private String gender;
    private String size;
    private String coat;
    private Map<String, String> attributes;
    private Map<String, String> environment;
    private String[] tags;
    private String name;
    private String description;
    private Map<String, String>[] photos;
    private String status;
    private String published_at;
    private Contact contact;
    private Map<String, Map<String, String>> _links;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganization_id() {
        return organization_id;
    }

    public void setOrganization_id(String organization_id) {
        this.organization_id = organization_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public Map<String, String> getBreeds() {
        return breeds;
    }

    public void setBreeds(Map<String, String> breeds) {
        this.breeds = breeds;
    }

    public Map<String, String> getColors() {
        return colors;
    }

    public void setColors(Map<String, String> colors) {
        this.colors = colors;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCoat() {
        return coat;
    }

    public void setCoat(String coat) {
        this.coat = coat;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public Map<String, String> getEnvironment() {
        return environment;
    }

    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String>[] getPhotos() {
        return photos;
    }

    public void setPhotos(Map<String, String>[] photos) {
        this.photos = photos;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublished_at() {
        return published_at;
    }

    public void setPublished_at(String published_at) {
        this.published_at = published_at;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Map<String, Map<String, String>> get_links() {
        return _links;
    }

    public void set_links(Map<String, Map<String, String>> _links) {
        this._links = _links;
    }
}
