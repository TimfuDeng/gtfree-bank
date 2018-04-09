package cn.gtmap.landsale.model;

import java.io.Serializable;
import java.util.Date;

/**出让公告和中止公告vo
 * Created by trr on 2016-11-30.
 */

public class CrggInfo implements Serializable{






    private String ggId;

    private String ggTitle;


    private Date ggTime;

    private String ggType;

    public CrggInfo() {

    }

    public CrggInfo(String ggId, String ggTitle, Date ggTime, String ggType) {
        this.ggId = ggId;
        this.ggTitle = ggTitle;
        this.ggTime = ggTime;
        this.ggType = ggType;
    }

    public String getGgId() {
        return ggId;
    }

    public void setGgId(String ggId) {
        this.ggId = ggId;
    }

    public String getGgTitle() {
        return ggTitle;
    }

    public void setGgTitle(String ggTitle) {
        this.ggTitle = ggTitle;
    }

    public Date getGgTime() {
        return ggTime;
    }

    public void setGgTime(Date ggTime) {
        this.ggTime = ggTime;
    }

    public String getGgType() {
        return ggType;
    }

    public void setGgType(String ggType) {
        this.ggType = ggType;
    }
}
