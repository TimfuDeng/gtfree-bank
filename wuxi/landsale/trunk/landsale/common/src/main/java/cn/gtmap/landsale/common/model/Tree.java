package cn.gtmap.landsale.common.model;

import java.io.Serializable;

/**
 * 行政区树
 * @author zsj
 * @version 1.0
 */
public class Tree implements Serializable {

	private static final long serialVersionUID = 6167064838183350627L;

	/**
	 * 业务ID
	 */
	private String id;
	
	/**
	 * 行政区名称
	 */
	private  String name;

	/**
	 * 行政区级别
	 */
	private Integer regionLevel;

	/**
	 * 行政区父cid
	 */
	private String pId;

	/**
	 * 是否展开
	 */
	private boolean open = false;
	
	/**
	 * 是否选中
	 */
	private boolean checked = false;
	
	/**
	 * 是否允许拖拽
	 */
	private boolean drag = false;
	
	/**
	 * 是否允许 拖拽到自己
	 */
	private boolean drop = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRegionLevel() {
		return regionLevel;
	}

	public void setRegionLevel(Integer regionLevel) {
		this.regionLevel = regionLevel;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDrag() {
		return drag;
	}

	public void setDrag(boolean drag) {
		this.drag = drag;
	}

	public boolean isDrop() {
		return drop;
	}

	public void setDrop(boolean drop) {
		this.drop = drop;
	}
	
}
