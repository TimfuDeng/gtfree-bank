package cn.gtmap.landsale.model;

import cn.gtmap.egovplat.core.annotation.Field;
import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by trr on 2016/1/27.
 */
@Entity
@Table(name = "trans_law")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransLaw implements Serializable{
    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String lawId;

    @Column(length = 64)
    private String title;

    @Column(columnDefinition ="CLOB")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date updateTime;

    @Column(nullable = false,columnDefinition = "number(1) default '0'")
    @Field(value = "0-未发布 1-发布")
    private int lawStauts;

    public String getLawId() {
        return lawId;
    }

    public void setLawId(String lawId) {
        this.lawId = lawId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getLawStauts() {
        return lawStauts;
    }

    public void setLawStauts(int lawStauts) {
        this.lawStauts = lawStauts;
    }
}
