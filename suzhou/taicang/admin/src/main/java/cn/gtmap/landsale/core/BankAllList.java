package cn.gtmap.landsale.core;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Properties;

/**
 * Created by Jibo on 2015/5/11.
 */
public class BankAllList {
    List<String[]> bankList;

    public List<String[]> getBankList() {
        return bankList;
    }

    public void setBankList(List<String[]> bankList) {
        this.bankList = bankList;
    }

    public String getBankName(String code){
        for(String[] values:bankList){
            if(values[0].equals(code))
                return values[1];
        }
        return "";
    }
}
