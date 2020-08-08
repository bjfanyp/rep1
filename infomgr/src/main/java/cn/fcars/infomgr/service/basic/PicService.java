package cn.fcars.infomgr.service.basic;

import cn.fcars.infomgr.entity.basic.Pic;
import cn.fcars.infomgr.service.BaseService;
import org.apache.ibatis.jdbc.Null;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * Created by fanyp on 2018/11/20.
 */
public interface PicService extends BaseService<Pic, Null> {
    void saveOrUpdate(Pic pic) throws Exception;
    String uploadImage(CommonsMultipartFile file, String uploadPath, String realUploadPath, String filename);
    String uploadThumbnail(CommonsMultipartFile file, String uploadPath, String realUploadPath,String filename);
}
