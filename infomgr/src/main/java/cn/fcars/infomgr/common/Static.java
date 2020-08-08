package cn.fcars.infomgr.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by fanyp on 2018/11/25.
 */
@Component
public class Static {

    @Value("#{configProperties['pageSize']}")
    public  Integer pageSize;

    @Value("#{configProperties['logonerrornum']}")
    public  Integer logonerrornum;

    @Value("#{configProperties['sdtime']}")
    public  Integer sdtime;


    public static final int WIDTH = 100 ;
    public static final int HEIGHT = 100 ;

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getLogonerrornum() {
        return logonerrornum;
    }

    public Integer getSdtime() {
        return sdtime;
    }
}
