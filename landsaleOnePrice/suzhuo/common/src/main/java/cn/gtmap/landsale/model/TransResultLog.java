package cn.gtmap.landsale.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by trr on 2016/8/14.
 */

public class TransResultLog implements Serializable{


    private String PRICEUNIT;

    private BigDecimal PRICE;
    private Date PRICEDATE;
    private String TRANSUSERID;


    public String getPRICEUNIT() {
        return PRICEUNIT;
    }

    public void setPRICEUNIT(String PRICEUNIT) {
        this.PRICEUNIT = PRICEUNIT;
    }


    public BigDecimal getPRICE() {
        return PRICE;
    }

    public void setPRICE(BigDecimal PRICE) {
        this.PRICE = PRICE;
    }

    public Date getPRICEDATE() {
        return PRICEDATE;
    }

    public void setPRICEDATE(Date PRICEDATE) {
        this.PRICEDATE = PRICEDATE;
    }

    public String getTRANSUSERID() {
        return TRANSUSERID;
    }

    public void setTRANSUSERID(String TRANSUSERID) {
        this.TRANSUSERID = TRANSUSERID;
    }
}
