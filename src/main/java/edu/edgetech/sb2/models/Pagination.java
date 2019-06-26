package edu.edgetech.sb2.models;

import java.util.Map;

public class Pagination {
    private Integer count_per_page;
    private Integer total_count;
    private Integer current_page;
    private Integer total_pages;
    private Map<String, Map<String, String>> _links;

    public Integer getCount_per_page() {
        return count_per_page;
    }

    public void setCount_per_page(Integer count_per_page) {
        this.count_per_page = count_per_page;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Map<String, Map<String, String>> get_links() {
        return _links;
    }

    public void set_links(Map<String, Map<String, String>> _links) {
        this._links = _links;
    }
}
