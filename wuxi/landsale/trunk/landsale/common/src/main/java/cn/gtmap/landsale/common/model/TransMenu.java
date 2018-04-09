package cn.gtmap.landsale.common.model;


import cn.gtmap.egovplat.core.support.hibernate.UUIDHexGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单表实体对象
 * @author zsj
 * @version v1.0, 2017/9/6
 */
@Entity
@Table(name = "trans_menu")
/*@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)*/
public class TransMenu implements Serializable, Comparable<TransMenu> {

    @Id
    @GeneratedValue(generator = "sort-uuid")
    @GenericGenerator(name = "sort-uuid", strategy = UUIDHexGenerator.TYPE)
    @Column(length = 32)
    private String menuId;

    @Column(length = 50)
    private String menuName;

    @Column(length = 100)
    private String menuUrl;

    /**
     * 是否有效
     */
    @Column(columnDefinition ="number(1) default '1'")
    private Integer menuValid;

    @Column(length = 50)
    private String menuIcon;

    @Column(length = 32)
    private String menuParentId;

    @Column
    private Integer menuOrder;

    @Column
    private Integer menuType;

    @Column
    private Integer menuLeave;

    @Transient
    private List<TransMenu> subMenuList;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getMenuValid() {
        return menuValid;
    }

    public void setMenuValid(Integer menuValid) {
        this.menuValid = menuValid;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Integer getMenuLeave() {
        return menuLeave;
    }

    public void setMenuLeave(Integer menuLeave) {
        this.menuLeave = menuLeave;
    }

    public List<TransMenu> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<TransMenu> subMenuList) {
        this.subMenuList = subMenuList;
    }

    @Override
    public int compareTo(TransMenu transMenu) {
        return this.menuOrder - transMenu.getMenuOrder();
    }
}
