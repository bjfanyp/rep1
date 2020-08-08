package cn.fcars.infomgr.controller.sms;

import cn.fcars.infomgr.common.CustomException;
import cn.fcars.infomgr.util.ConfigProperties;
import cn.fcars.infomgr.util.HttpAdapter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("sms")
public class SmsControl {
	
	private  static final String INDEX="admin/system_manager/batch_send";
	@RequestMapping(value="/send")
	public void send(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String postUrl = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
		int mobileCode = (int)((Math.random()*9+1)*100000);
		String account = ConfigProperties.getValue("ACCOUNT");
		String password = ConfigProperties.getValue("PASSWORD");
		String mobile = request.getParameter("mobile");
		String content = new String("您的验证码是：" + mobileCode + "。请不要把验证码泄露给其他人。");
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(postUrl+"&mobile="+mobile+"&account="+account+"&password="+password+"&content="+content);
			HttpResponse httpResponse = client.execute(httpGet);
			HttpEntity entity = httpResponse.getEntity();
			if(entity != null){
				String result = EntityUtils.toString(entity,"UTF-8");
				Document doc = DocumentHelper.parseText(result);
                Element root = doc.getRootElement();
                String code = root.elementText("code");
                String msg = root.elementText("msg");
                if ("2".equals(code)) {
					session.setAttribute(mobile,mobileCode);
				}
                Map<String, Object> map=new HashMap<String, Object>();
                map.put("code",code);
                map.put("msg", msg);
                map.put("mobile_code", mobileCode);
                HttpAdapter.printlnObject(response, map);
			}
            session.setAttribute(mobile,mobileCode);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@RequestMapping(value="/check")
	public void check(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			boolean flag=false;
			String mobile = request.getParameter("mobile");
			String identifyCode=request.getParameter("identify_code");
			System.out.println(session.getAttribute(mobile));

			if (identifyCode.equals(session.getAttribute(mobile).toString())) {
				flag=true;
			} 
			Map<String, Object> map=new HashMap<String, Object>();
            map.put("flag",flag);
            HttpAdapter.printlnObject(response, map);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}
	@RequestMapping(value="/batchsendindex")
	public ModelAndView  batchSendindex(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView=new ModelAndView(INDEX);
		return modelAndView;
	}
	
	@RequestMapping(value="/batchsend")
	public ModelAndView batchSend(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView=new ModelAndView(INDEX);
		String postUrl = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
		String account = ConfigProperties.getValue("ACCOUNT");
		String password = ConfigProperties.getValue("PASSWORD");
		String mobilestr = request.getParameter("mobile");
		String content =  request.getParameter("content");
		String[] mobiles = mobilestr.split(",");
		String errormobiles="";
		boolean flag =false;
		for (int i = 0; i < mobiles.length; i++) {
			String mobile = mobiles[i];
			try {
				DefaultHttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(postUrl+"&mobile="+mobile+"&account="+account+"&password="+password+"&content="+content);
				HttpResponse httpResponse = client.execute(httpGet);
				HttpEntity entity = httpResponse.getEntity();
				if(entity != null){
					String result = EntityUtils.toString(entity,"UTF-8");
					Document doc = DocumentHelper.parseText(result);
	                Element root = doc.getRootElement();
	                String code = root.elementText("code");
	                String msg = root.elementText("msg");
	                Map<String, Object> map=new HashMap<String, Object>();
	                map.put("code",code);
	                if(!"2".equals(code)){
	                	errormobiles+=mobiles[i]+",错误原因："+msg+";";
	                }
	                request.setAttribute("errormobiles", errormobiles);
				}
			} catch (Exception e) {
				throw new CustomException(e.getMessage());
			}
		}
		if(errormobiles.length()<0){
			flag=true;
		}
		request.setAttribute("flag", flag);
		return modelAndView;
	}

}
