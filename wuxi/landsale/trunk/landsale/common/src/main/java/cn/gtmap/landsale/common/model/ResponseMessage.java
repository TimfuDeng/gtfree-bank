package cn.gtmap.landsale.common.model;

import java.io.Serializable;

/**
 * 信息返回
 * @author zsj
 * @version v1.0, 2017/4/12
 */
public class ResponseMessage<E> implements Serializable {

    /**
     * 接搜银行信息返回时 判断用
     */
    private String code;

    private Boolean flag;

    private String message;

    private E empty;

    public ResponseMessage() {}

    public ResponseMessage(Boolean flag) {
        this(flag, flag ? "操作成功!" : "操作失败！");
    }

    public ResponseMessage(Boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }

    public ResponseMessage(Boolean flag, E empty) {
        this(flag, flag ? "操作成功!" : "操作失败！", empty);
    }

    public ResponseMessage(Boolean flag, String message, E empty) {
        this.flag = flag;
        this.message = message;
        this.empty = empty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getEmpty() {
        return empty;
    }

    public void setEmpty(E empty) {
        this.empty = empty;
    }

}
