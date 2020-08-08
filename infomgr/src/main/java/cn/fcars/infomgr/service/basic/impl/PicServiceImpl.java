package cn.fcars.infomgr.service.basic.impl;

import cn.fcars.infomgr.entity.basic.Pic;
import cn.fcars.infomgr.mapper.basic.PicMapper;
import cn.fcars.infomgr.service.basic.PicService;
import com.github.pagehelper.PageInfo;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by fanyp on 2018/11/27.
 */
@Service
public class PicServiceImpl implements PicService {

    @Autowired
    PicMapper picMapper;

    public String uploadImage(CommonsMultipartFile file, String uploadPath, String realUploadPath,String filename) {
        InputStream is = null ;
        OutputStream os = null ;
        try {
            is = file.getInputStream() ;
            String des = realUploadPath + "\\" + filename ;
            os = new FileOutputStream(des) ;
            byte[] buffer = new byte[1024] ;
            int len = 0 ;
            while((len=is.read(buffer))>0) {
                os.write(buffer);
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if(os!=null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return uploadPath + "/" + filename ;
    }
    public static final int WIDTH = 200 ;
    public static final int HEIGHT = 200 ;

    public String uploadThumbnail(CommonsMultipartFile file, String uploadPath, String realUploadPath,String filename) {

        try {
            String des = realUploadPath +"\\thum_" +filename;
            Thumbnails.of(file.getInputStream()).size(WIDTH, HEIGHT).toFile(des);

        } catch(Exception e) {
            e.printStackTrace() ;
        }
        return uploadPath + "/thum_" + filename ;
    }

    public Pic findByID(Integer id)
    {
        return picMapper.findByID(id);
    }

    @Override
    public Pic findByStringID(String id) {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(Pic pic) throws RuntimeException
    {
        try {
            if (pic.getPicID() == null) {
                picMapper.add(pic);
            } else {
                picMapper.update(pic);
            }
        }
        catch (Exception e)
        {
            throw  new RuntimeException(e.getMessage());
        }
    }
    @Override
    public Pic findByName(String name) {
        return null;
    }

    @Override
    public List<Pic> findAll() {
        return null;
    }

    @Override
    public List<Pic> findByQuery(Null data) {
        return null;
    }

    @Override
    public PageInfo<Pic> findByPage(Null data) {
        return null;
    }

    @Override
    public Integer check(String name) {
        return null;
    }

    @Override
    public boolean checkExist(String id, String name) {
        return false;
    }

    @Override
    public void add(Pic data) throws Exception {

    }

    @Override
    public void update(Pic data) throws Exception {

    }

    @Override
    public void deleteByID(Integer id) throws Exception {

    }

    @Override
    public int delete(Pic data) throws Exception {
        return 0;
    }


}
