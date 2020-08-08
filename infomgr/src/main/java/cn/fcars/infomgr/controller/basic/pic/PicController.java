package cn.fcars.infomgr.controller.basic.pic;

import cn.fcars.infomgr.common.PicPara;
import cn.fcars.infomgr.common.ResultData;
import cn.fcars.infomgr.entity.basic.Pic;
import cn.fcars.infomgr.service.basic.PicService;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@Controller
public class PicController {

    @Autowired
    PicService picService;

    @RequestMapping("/picDetail")
    public String picDetail(Model model, @RequestBody Map image)
    {
        String picUrl =image.get("url").toString();
        model.addAttribute("picUrl",picUrl);
        return "picDetail";
    }

    @RequestMapping(value="/uploadPic/{id}")
    @ResponseBody
    public String uploadPic(@PathVariable Integer id, HttpServletRequest request) throws Exception {
        try {
            Pic pic=picService.findByID(id);
            if(pic==null)
            {
                JSONObject json =(JSONObject)JSONObject.toJSON(new ResultData(false,null,"上传文件失败"));//将java对象转换为json对象
                return json.toJSONString()  ;
            }
            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
            if(multipartResolver.isMultipart(request))
            {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iter=multiRequest.getFileNames();
                while(iter.hasNext())
                {
                    //一次遍历所有文件
                    CommonsMultipartFile file= (CommonsMultipartFile) multiRequest.getFile(iter.next().toString());
                    if(file!=null)
                    {
                        if(file.getSize()>2097152)
                        {
                            throw new Exception("不能上传超过2MB的文件");
                        }
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                        String datePath = dateFormat.format(new Date());
                        String uploadPath = "static/image/upload/" + datePath;
                        String realPath = request.getSession().getServletContext().getRealPath(uploadPath);
                        File f = new File(realPath);
                        if (!f.exists()) {
                            f.mkdirs();
                        }
                        String originalFile = file.getOriginalFilename();
                        String originalFileSuffix = originalFile.substring(originalFile.indexOf("."));
                        String realFileName= UUID.randomUUID().toString();
                        String realFile = realFileName + originalFileSuffix;
                        String imageUrl = picService.uploadImage(file, uploadPath, realPath, realFile);
                        String thumbImageUrl = picService.uploadThumbnail(file, uploadPath, realPath, realFile);
                        pic.setPicUrl(imageUrl);
                        pic.setPicThumbUrl(thumbImageUrl);
                        picService.saveOrUpdate(pic);
                        //return new ResultData(true,pic,"上传成功");
                        JSONObject json =(JSONObject)JSONObject.toJSON(new  ResultData(true,pic,"上传成功"));//将java对象转换为json对象
                        return json.toJSONString()  ;

                    }
                }
            }
            //return new ResultData(false,null,"上传文件失败");
            JSONObject json =(JSONObject)JSONObject.toJSON(new ResultData(false,null,"上传文件失败"));//将java对象转换为json对象
            return json.toJSONString()  ;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //return new ResultData(false,null,ex.getMessage());
            JSONObject json =(JSONObject)JSONObject.toJSON(new ResultData(false,null,ex.getMessage()));//将java对象转换为json对象
            return json.toJSONString()  ;
        }
    }

    @RequestMapping(value="/uploadPic")
    @ResponseBody
    public String uploadPhoto(HttpServletRequest request) throws Exception {
        try {
            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
            if(multipartResolver.isMultipart(request))
            {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iter=multiRequest.getFileNames();
                while(iter.hasNext())
                {
                    //一次遍历所有文件
                    CommonsMultipartFile file= (CommonsMultipartFile) multiRequest.getFile(iter.next().toString());
                    if(file!=null)
                    {
                        if(file.getSize()>2097152)
                        {
                            throw new Exception("不能上传超过2MB的文件");
                        }
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                        String datePath = dateFormat.format(new Date());
                        String uploadPath = "static/image/upload/" + datePath;
                        String realPath = request.getSession().getServletContext().getRealPath(uploadPath);
                        File f = new File(realPath);
                        if (!f.exists()) {
                            f.mkdirs();
                        }

                        String originalFile = file.getOriginalFilename();

                        String originalFileSuffix = originalFile.substring(originalFile.indexOf("."));

                        String realFileName= UUID.randomUUID().toString();

                        String realFile = realFileName + originalFileSuffix;

                        String imageUrl = picService.uploadImage(file, uploadPath, realPath, realFile);

                        String thumbImageUrl = picService.uploadThumbnail(file, uploadPath, realPath, realFile);

                        PicPara pic =new PicPara();
                        pic.setPhotoUrl(imageUrl);
                        pic.setThumbUrl(thumbImageUrl);

                        JSONObject json =(JSONObject)JSONObject.toJSON(new  ResultData(true,pic,"上传成功"));//将java对象转换为json对象
                        return json.toJSONString()  ;

                    }
                }
            }
            //return new ResultData(false,null,"上传文件失败");
            JSONObject json =(JSONObject)JSONObject.toJSON(new ResultData(false,null,"上传文件失败"));//将java对象转换为json对象
            return json.toJSONString()  ;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //return new ResultData(false,null,ex.getMessage());
            JSONObject json =(JSONObject)JSONObject.toJSON(new ResultData(false,null,ex.getMessage()));//将java对象转换为json对象
            return json.toJSONString()  ;
        }
    }

    @RequestMapping(value="/uploadIncon")
    @ResponseBody
    public String uploadIncon(HttpServletRequest request) throws Exception {
        try {
            CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(request.getSession().getServletContext());
            if(multipartResolver.isMultipart(request))
            {
                //将request变成多部分request
                MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
                //获取multiRequest 中所有的文件名
                Iterator iter=multiRequest.getFileNames();
                while(iter.hasNext())
                {
                    //一次遍历所有文件
                    CommonsMultipartFile file= (CommonsMultipartFile) multiRequest.getFile(iter.next().toString());
                    if(file!=null)
                    {
                        if(file.getSize()>2097152)
                        {
                            throw new Exception("不能上传超过2MB的文件");
                        }
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                        String datePath = dateFormat.format(new Date());
                        String uploadPath = "static/image/upload/" + datePath;
                        String realPath = request.getSession().getServletContext().getRealPath(uploadPath);
                        File f = new File(realPath);
                        if (!f.exists()) {
                            f.mkdirs();
                        }

                        String originalFile = file.getOriginalFilename();

                        String originalFileSuffix = originalFile.substring(originalFile.indexOf("."));

                        String realFileName= UUID.randomUUID().toString();

                        String realFile = realFileName + originalFileSuffix;

                        String imageUrl = picService.uploadImage(file, uploadPath, realPath, realFile);

                        PicPara pic =new PicPara();
                        pic.setPhotoUrl(imageUrl);
                        pic.setThumbUrl("");
                        JSONObject json =(JSONObject)JSONObject.toJSON(new  ResultData(true,pic,"上传成功"));//将java对象转换为json对象
                        return json.toJSONString()  ;

                    }
                }
            }
            //return new ResultData(false,null,"上传文件失败");
            JSONObject json =(JSONObject)JSONObject.toJSON(new ResultData(false,null,"上传文件失败"));//将java对象转换为json对象
            return json.toJSONString()  ;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            //return new ResultData(false,null,ex.getMessage());
            JSONObject json =(JSONObject)JSONObject.toJSON(new ResultData(false,null,ex.getMessage()));//将java对象转换为json对象
            return json.toJSONString()  ;
        }
    }
}
