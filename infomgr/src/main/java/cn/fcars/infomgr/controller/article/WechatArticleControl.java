package cn.fcars.infomgr.controller.article;

import cn.fcars.infomgr.entity.basic.WechatArticle;
import cn.fcars.infomgr.service.basic.WechatArticleService;
import cn.fcars.infomgr.util.HttpAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/wechat_article")
public class WechatArticleControl {
	private  static final String INDEX="admin/weixin/weixin_article";
	private  static final String ADD="admin/weixin/wechat_article_add";

	public WechatArticleService getWechatArticleService() {
		return wechatArticleService;
	}

	@Autowired
	WechatArticleService wechatArticleService;
	@RequestMapping(value="json/read/{id}",method=RequestMethod.GET)
	public void jsonRead(@PathVariable(value="id") String id,HttpServletRequest request,HttpServletResponse response){
		WechatArticle wechatArticle=new WechatArticle();
		try {
			wechatArticle=wechatArticleService.findByStringID(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpAdapter.printlnObject(response, wechatArticle);
	}


}
