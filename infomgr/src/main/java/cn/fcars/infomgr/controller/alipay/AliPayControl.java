package cn.fcars.infomgr.controller.alipay;

import cn.fcars.infomgr.entity.basic.WechatArticle;
import cn.fcars.infomgr.entity.cart.VehicleOrder;
import cn.fcars.infomgr.entity.tuangou.VehicleTuanGou;
import cn.fcars.infomgr.service.basic.WechatArticleService;
import cn.fcars.infomgr.service.cart.VehicleOrderService;
import cn.fcars.infomgr.service.tuangou.VehicleTuanGouService;
import cn.fcars.infomgr.util.HttpAdapter;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.DefaultSigner;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/aliPay")
public class AliPayControl {
	public static String APPID = "2021001160629737";
	public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCdevMl25DF810WXI2ZGN8n4MXybzy6r9vD9SHsz+OsPlRqD0IihgKrnnCLbIREZeds0QZBRxA9VTTcOFSTXQavga4KX+mNb7RoeKQVh1lJGpaFrTWihR2jXA42nOl/Btl/4F3xaymWE282fDBqBzB+uXmFZFLqKnDa4CBzxYUOMdA+jAGKvGlgH3bzbZITH3sbQFrPx5JJjGNuwtA6LlWqcB9eBh2NMnwMtY52Twmx6v5IcFLUcKVL7zokx2r7/OBNuXgXX2fASi+Wlp8aWlcBRiWIX9UG18b5eL6XvnaZcku2UjwIhy19G5VvoebMtsTUJdBCYr54aTprTb4F0UJBAgMBAAECggEACplq4iJsFRazLj0efq5w3qM/T3jzMnGBrifCl8jKPx6W2eia/+3bIifw1UGBiYT+VZGgBSa+ThJ9E1FrR9vcBlYJG8Om2ZEqKFnuWVCZztTOSsMEuCKTgxpADqbzBEy2uOemCwi2bNZ0BsvHCmvfdLgRGMxO1Fn07JZnOMOTmAfg/vWEkuTBsb6yG2jMD5VfrmzCvWXburvl4Nzi7gavyHsulZ7webl6Kqi1ml8y5Qb3FBEmMZg25s9ASc5NuaofhUgnB8VoinDASFWvU7rRSnpQuqaoTw3tgFA4tSZD19OrsiTELV3FPrkLpb/r3LfA8WS3f8mSvOAjXuR1poRoEQKBgQDmRK2cNpt/UNGtCVMwf7ZLvLU7baQ1DrvJbNMXPBtEgiepNs8R7LFmA/6IylL5kLilf9wjTBxvcbg9D33ak4DKQU1u/viPJLqh+A/bg6qcewB8X1Ib0ZV53MUfLQ3oKQPacOUXjK7ZSs9sACPzVVK3K9umAFSA5nqH6Fi6atPdLQKBgQCvFANzSqY/ooU47sBlncYz2Y4/PH7hdgiwBHIVzFv442U+cwnjIwgV99VcMynlhIFx2n+92nw6FnKz5UGyxIotpV4lian5/NA44XHVF3YnrKhAz/HeQI/66wnOWNDiH3C7zRbhniXUmmYGVc6HTo4mUH/4qlfqquU5GNFZGEmt5QKBgQCNViZBtfrnwJGSZiDbqegYwLF00q1xq6Nz1QoUPt51xHXXlT6wP9n7RgRbreQULtJHMx3JxJPHi8OMJxAWs2bfvglrUbD7G446kGobUMvN1GnN7SwWyyO2ct3DbwIUN+iXkafsNnu0AAkkv0lRuFNW/uKJF7bGj7Ex7llXwu5b2QKBgA1kVYM7IY5aiyA4uwTK1b9fk87ofLgYRD/ahzN9p0dZdYQaXpY+Nr8fbvWXgEvrH8+qq/zEup3i27FBBw56lCs7rADpD9Rsbyz6qa9oFQSh41jZzyF2BM1nrx9WkdvKFeB3pplpdHFXwhgX9i1JZ/wsSi2mMgVkPxZn/JN5FSG1AoGADR56kC5CEWR9ZIRpbeEM7eexCHwuEvQ4XixbHGd/HujkHzVWSy9keXHr5B/6AMXFeT69z9BhA2chjyhl6h4kf+CJYqpjZfPqkOoO6sVrsCXxo62C3QUWTOxJzvJ+UBtAi/48a+4aMgEQhOuriaSV9RrKFD+LV0A69DUvNCwhsHE=";
	public static String notify_url = "http://www.5cars.cn:8080/carsinfo/aliPay/notify_url";
	public static String URL = "https://openapi.alipay.com/gateway.do";
	public static String CHARSET = "UTF-8";
	public static String FORMAT = "json";
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjcE1zf81fVRKg+BALl4EnF50jM13TxFlV/VUVrV7vS0s5s73deDBhoJoWhSQyK4BQWNNwlX8tP4BAdvfXVjNG66XzIe7TkkIHzNDv9umHi+wZPIQwHsWpxyidggVF92qodtuAVDbSdf4oj/I8zQkrsecLy/x4+VEVryxXKl4t9LnhhBH81NrjSRl/KXFo+3ScXQsWPq0pezCko7Tg14OpgXoIQFpfoyK/WP4uoKAXpXkoCw75y/JNCJ/nlaxsPxcvxBnkVFJ9I6ekSNH+EIcxnGG++EdAXT+J8ujBTSCl3JwXAGGR1S0aTSycJWj9S2KK0JpMpja/GBt503tue+AhwIDAQAB";
	public static String SIGNTYPE = "RSA2";
	public static String timeout_express="2m";
	public static String product_code="QUICK_WAP_WAY";

	@Autowired
	VehicleOrderService orderService;
	@Autowired
	VehicleTuanGouService tuanGouService;

	@RequestMapping(value="json/pay",method=RequestMethod.POST)
	public void aliPay(HttpServletRequest request,HttpServletResponse response){
		try {
			String out_trade_no= request.getParameter("out_trade_no");
			String subject= request.getParameter("subject");
//			String total_amount=request.getParameter("total_amount");
			String total_amount="0.01";
			String body= request.getParameter("body");
			AlipayClient client = new DefaultAlipayClient(URL, APPID, RSA_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY,SIGNTYPE);
			AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
			AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
			model.setOutTradeNo(out_trade_no);
			model.setSubject(subject);
			model.setTotalAmount(total_amount);
			model.setBody(body);
			model.setTimeoutExpress(timeout_express);
			model.setProductCode(product_code);
			alipay_request.setBizModel(model);
			alipay_request.setNotifyUrl(notify_url);
			String form = client.pageExecute(alipay_request).getBody();
			HttpAdapter.printlnAliPayObject(response,form);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="json/AppPay",method=RequestMethod.POST)
	public void aliAppPay(HttpServletRequest request,HttpServletResponse response){
		try {
			String out_trade_no= request.getParameter("out_trade_no");
			String subject= request.getParameter("subject");
			String total_amount="0.01";
			String body= request.getParameter("body");
			AlipayClient alipayClient = new DefaultAlipayClient(URL,APPID, RSA_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY,SIGNTYPE);
			AlipayTradeAppPayRequest alipay_request = new AlipayTradeAppPayRequest();
			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
			model.setBody(body);
			model.setSubject(subject);
			model.setOutTradeNo(out_trade_no);
			model.setTimeoutExpress("30m");
			model.setTotalAmount(total_amount);
			model.setProductCode("QUICK_MSECURITY_PAY");
			alipay_request.setBizModel(model);
			alipay_request.setNotifyUrl(AliPayControl.notify_url);
			AlipayTradeAppPayResponse responseObj = alipayClient.sdkExecute(alipay_request);
			HttpAdapter.printlnMessagge(response,true,responseObj.getBody());
		} catch (Exception e) {
			HttpAdapter.printlnMessagge(response,false,e.getMessage());
		}
	}
	@RequestMapping(value="json/PayA",method=RequestMethod.POST)
	public void aliPayA(HttpServletRequest request,HttpServletResponse response){
		try {
			String out_trade_no= "1588255333229";
			String subject= "1588255333229";
			String total_amount="0.01";
			String body= request.getParameter("body");

			AlipayClient client = new DefaultAlipayClient(URL, APPID, RSA_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY,SIGNTYPE);
			AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
			AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
			model.setOutTradeNo(out_trade_no);
			model.setSubject(subject);
			model.setTotalAmount(total_amount);
			model.setBody(body);
			model.setTimeoutExpress(timeout_express);
			model.setProductCode(product_code);
			alipay_request.setBizModel(model);
			alipay_request.setNotifyUrl(notify_url);
			String form = client.pageExecute(alipay_request).getBody();
			response.setContentType("text/html;charset=" + CHARSET);
			response.getWriter().write(form);//直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value="json/PayAll/{id}",method=RequestMethod.GET)
	public void PayAll(@PathVariable String id, HttpServletRequest request,HttpServletResponse response){
		try {
			VehicleOrder order = orderService.findByStringID(id);
			 if(order.getZt().equals("4")){
				order.setZt("5");
				orderService.update(order);
				List<VehicleOrder> orderList = orderService.findByTuanGouID(order.getTuanGou().getTgID());
				for(int j=0;j<orderList.size();j++){
					if(!orderList.get(j).getZt().equals("5"))
					{
						break;
					}
					if(j==orderList.size()-1){
						VehicleTuanGou tuanGou = order.getTuanGou();
						tuanGou.setTgZt("3");
						tuanGouService.update(tuanGou);
					}
				}
				 HttpAdapter.printlnMessagge(response,true,null);
			}
			 else {
				 HttpAdapter.printlnMessagge(response,false,"订单状态不正确");
			 }
		} catch (Exception e) {
			HttpAdapter.printlnMessagge(response,false,e.getMessage());
		}
	}
	@RequestMapping(value="notify_url",method=RequestMethod.POST)
	@ResponseBody
	public String  notify_url(HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			//获取支付宝POST过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
				params.put(name, valueStr);
			}
            //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
			//商户订单号
			String out_trade_no = new String(params.get("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号
			String trade_no = new String(params.get("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//交易状态
			String trade_status = new String(params.get("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			String total_amount = new String(params.get("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			String body = new String(params.get("body").getBytes("ISO-8859-1"),"UTF-8");

			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
			//计算得出通知验证结果
			boolean verify_result = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, "RSA2");
			if(verify_result){//验证成功
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				if(trade_status.equals("TRADE_FINISHED")){
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序

					//注意：
					//如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
					//如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
					//如果有做过处理，不执行商户的业务程序
					VehicleOrder order = orderService.findByStringID(out_trade_no);
					if(order.getZt().equals("1")){
					if(body.equals("advance")){
//						if((String.valueOf(order.getTotalAdvance())+".00").equals(total_amount)){
						if(total_amount.equals("0.01")){
							order.setZt("2");
							orderService.update(order);
							VehicleTuanGou tuanGou =order.getTuanGou();
							tuanGou.setTgYtCount(tuanGou.getTgYtCount()+order.getCount());
							tuanGouService.update(tuanGou);
							if(tuanGou.getTgYtCount()==tuanGou.getTgCount()){
								tuanGou.setTgZt("3");
								tuanGouService.update(tuanGou);
								List<VehicleOrder> orderList = orderService.findByTuanGouID(order.getTuanGou().getTgID());
								for(int j=0;j<orderList.size();j++){
									VehicleOrder vehicleOrder = orderList.get(j);
									vehicleOrder.setZt("4");
									orderService.update(vehicleOrder);
								}
							}
						}
						else {
							return "fail";
						}
						}
					}
//					else if(order.getZt().equals("4")){
//						if(body.equals("all")){
//	//						if((String.valueOf(order.getTotalPrice()-order.getTotalAdvance())+".00").equals(total_amount)){
//							if(total_amount.equals("0.01")){
//								order.setZt("5");
//								orderService.update(order);
//								List<VehicleOrder> orderList = orderService.findByTuanGouID(order.getTuanGou().getTgID());
//								for(int j=0;j<orderList.size();j++){
//									if(!orderList.get(j).getZt().equals("5"))
//									{
//										break;
//									}
//									if(j==orderList.size()-1){
//										VehicleTuanGou tuanGou = order.getTuanGou();
//										tuanGou.setTgZt("3");
//										tuanGouService.update(tuanGou);
//									}
//								}
//							}
//							else {
//								return "fail";
//							}
//						//注意：
//						//如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
//						}
//					}
					else {
						return "fail";
					}
				}
				return "success";
			}else{//验证失败
				return "fail";
			}
		} catch (Exception e) {
			return "fail";
		}
	}
	@RequestMapping(value="json/refund/{id}",method=RequestMethod.POST)
	public void refund(@PathVariable String id, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			VehicleOrder vehicleOrder =orderService.findByStringID(id);
			String out_trade_no=vehicleOrder.getId();
			String refund_amount="";
			if(!vehicleOrder.getZt().equals("2")){
				return;
			}
//			refund_amount=vehicleOrder.getTotalAdvance()+".00";
			refund_amount="0.01";
			AlipayClient client = new DefaultAlipayClient(URL, APPID, RSA_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY,SIGNTYPE);
			AlipayTradeRefundRequest alipay_request = new AlipayTradeRefundRequest();
			AlipayTradeRefundModel model=new AlipayTradeRefundModel();
			model.setOutTradeNo(out_trade_no);
			model.setRefundAmount(refund_amount);
			alipay_request.setBizModel(model);
			AlipayTradeRefundResponse alipay_response =client.execute(alipay_request);
			vehicleOrder.setZt("3");
			orderService.update(vehicleOrder);
		} catch (Exception e) {
			throw  new Exception(e.getMessage());
		}
	}
}