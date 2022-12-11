package tech.guen.dto;


import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Website> websiteList = new ArrayList<>();

    public List<Website> getWebsiteList() {
        return websiteList;
    }

    public void setWebsiteList(List<Website> websiteList) {
        this.websiteList = websiteList;
    }
}
