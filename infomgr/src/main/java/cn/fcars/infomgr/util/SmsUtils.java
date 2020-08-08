package cn.fcars.infomgr.util;

import cn.fcars.infomgr.controller.alipay.AliPayControl;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.alipay.api.internal.util.AlipaySignature;

public class SmsUtils {
	private static Logger logger = Logger.getLogger(SmsUtils.class.getName());
	
	public static final String ORDER_SUCCESS="尊敬的用户USER,我们已经收到您购买商品的订单(ORDERID)，详情请登录五车官网进行查看，账号默认为你手机号，密码是手机号+123。";
	public static final String ORDER_CONFIRM="恭喜您，在五车网电子商务平台上购买商品下单成功，请单击链接：http://5cars.cn/5cars-info/product/order/json/loadConfirm/ORDERID.html，尽快核实信息!";
	public static final String OOS_MESSAFE="店铺管理员USERLOGIN您好,请您将产品PRODUCT下所有产品进行上架，录入完成后请确认:BASEPATH/productOos/json/loadConfirm/ID.html，谢谢！经纪人AGENTUSER";
	public static final String CHECK_AUTH_MESSAFE="尊敬的USERNAME您好，欢迎您成为本公司的经济人，您提交的审核结果:MESSAGE1，请登录APP进行查看，谢谢";
	public static final String IMMEDIATE_INQUIRY_CONFIRM="经济人AGENTUSER向您发出产品询价，请单击此链接：http://5cars.cn/5cars-info/product/order/json/immediateInquiryConfirm/ORDERID.html，进行填写最低价格及定金";
	public static Map<String, Object> sendSMS(String mobile, String content){
		 Map<String, Object> map=new HashMap<String, Object>();
		 boolean flag=false;
		String postUrl = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
		String account = ConfigProperties.getValue("ACCOUNT");
		String password = ConfigProperties.getValue("PASSWORD");
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
					flag=true;
				}
                map.put("msg", msg);
                System.out.println(msg+":"+mobile+":"+content);
                logger.info(msg+":接收者【"+mobile+"】,内容【"+content+"】");
			}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("msg", e.getLocalizedMessage());
		}
		map.put("flag", flag);
		return map;
	}
	public static void main(String[] args) throws Exception{
//		int mobileCode = (int)((Math.random()*9+1)*100000);
//      String content = new String("恭喜您，在五车网电子商务平台上购买的商品(一汽-大众奥迪 奥迪A3 2017款 Sportback 35 TFSI 进取型 外观冰川白 内饰黑色),下单成功，请单击链接：http://5cars.cn/5cars-info/product/order/json/loadConfirm/ORDERID.html，尽快核实信息!");
//		String content = new String("您的验证码是：156778。请不要把验证码泄露给其他人。");
//		System.out.println(content.replaceAll(" ", ""));
//		SmsUtils.sendSMS("18611366769", content.replaceAll(" ", ""));
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayControl.URL, AliPayControl.APPID, AliPayControl.RSA_PRIVATE_KEY, AliPayControl.FORMAT, AliPayControl.CHARSET, AliPayControl.ALIPAY_PUBLIC_KEY,AliPayControl.SIGNTYPE);
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("我是测试数据");
		model.setSubject("App支付测试Java");
		model.setOutTradeNo(String.valueOf(new Date().getTime()));
		model.setTimeoutExpress("30m");
		model.setTotalAmount("0.01");
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl(AliPayControl.notify_url);
		try {
			//这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			System.out.println(response.getBody());//就是orderString 可以直接给客户端请求，无需再做处理。
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}
}
