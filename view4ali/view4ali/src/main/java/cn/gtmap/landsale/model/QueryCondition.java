package cn.gtmap.landsale.model;

import java.io.Serializable;

/**
 * Created by JIFF on 2016/10/18.
 */
public class QueryCondition implements Serializable {
    String region;
    int status;
    int use;
    String name;
    int page;   //多少页

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getUse() {
        return use;
    }

    public void setUse(int use) {
        this.use = use;
    }
}
