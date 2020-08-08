package cn.fcars.infomgr.mapper.basic;

import cn.fcars.infomgr.common.BaseQuery;
import cn.fcars.infomgr.entity.basic.WechatArticle;
import cn.fcars.infomgr.entity.cart.VehicleOrder;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.Null;

public interface WechatArticleMapper extends BaseMapper<WechatArticle, BaseQuery> {
    WechatArticle findByStringID(@Param("id") String id);
}