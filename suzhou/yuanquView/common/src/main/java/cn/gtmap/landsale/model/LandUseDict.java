
package cn.gtmap.landsale.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 土地用途字典表
 * Created by liushaoshuai on 2017/8/24.
 */

@Entity
@Table(name = "dict_land_use_muli")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LandUseDict implements Serializable{

    @Id
    @Column(columnDefinition ="number(20) default '0'")
    private Integer id;

    @Column(length = 9)
    private String code;

    @Column(length = 50)
    private String name;

    @Column(columnDefinition ="number(20) default '0'")
    private Integer grade;

    @Column(length = 9)
    private String parent;

    @Column(columnDefinition ="number(20) default '0'")
    private Integer isValid;

    @Column(length = 1)
    private String type;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
