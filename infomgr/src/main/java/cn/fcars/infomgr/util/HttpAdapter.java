package cn.fcars.infomgr.util;

import cn.fcars.infomgr.controller.alipay.AliPayControl;
import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 页面内容输出工具
 * @author lj
 *
 */
public class HttpAdapter {
	public static PrintWriter getWriter(HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer;
	}
	/**
	 * 打印字符串
	 * @param response
	 * @param msg
	 */
	public static void printlnStr(HttpServletResponse response, String msg){
		PrintWriter writer=getWriter(response);
		writer.println(msg);
	}
	/**
	 * 打印布尔
	 * @param response
	 * @param flag
	 */
	public static void printlnBool(HttpServletResponse response, boolean flag){
		PrintWriter writer=getWriter(response);
		writer.println(flag+"");
	}
	/**
	 * 打印对象
	 * @param response
	 * @param object
	 */
	public static void printlnAliPayObject(HttpServletResponse response, String object){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Max-Age", "1728000");
		response.setContentType("text/html;charset=" + AliPayControl.CHARSET);
		try {
			response.getWriter().write(object);//直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void printlnObject(HttpServletResponse response, Object object){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Max-Age", "1728000");
		PrintWriter writer=getWriter(response);
		writer.println(JSON.toJSONString(object));
	}
	/**
	 * 打印跨域对象
	 * @param response
	 * @param object
	 */
	public static void printlnJSONPObject(HttpServletResponse response, Object object, String callback){
		PrintWriter writer=getWriter(response);
		writer.println(callback+"("+ JSON.toJSONString(object)+")");
	}
	/**
	 * 打印跨域或非跨越对象
	 * @param response
	 * @param object
	 * @param callback
	 */
	public static void printlnJSONPOrJSONObject(HttpServletRequest request, HttpServletResponse response, Object object){
		String callback=request.getParameter("callback");
		if(null!=callback){
			HttpAdapter.printlnJSONPObject(response,object, callback);
		}else{
			HttpAdapter.printlnObject(response, object);
		}
	}
	
	/**
	 * 打印对象
	 * @param response
	 * @param object
	 */
	public static void printlnMessagge(HttpServletResponse response, boolean flag, Object data){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Max-Age", "1728000");
		Map<String, Object> map=new HashMap<String, Object>();
		PrintWriter writer=getWriter(response);
		map.put("msg",data);
		map.put("flag",flag);
		writer.println(JSON.toJSONString(map));
	}
	/**
	 * 打印对象
	 * @param response
	 * @param object
	 */
	public static void printlnDoubleMessagge(HttpServletResponse response, boolean flag, Object data,Object obj){
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
		response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
		response.addHeader("Access-Control-Max-Age", "1728000");
		Map<String, Object> map=new HashMap<String, Object>();
		PrintWriter writer=getWriter(response);
		map.put("msg",data);
		map.put("city",obj);
		map.put("flag",flag);
		writer.println(JSON.toJSONString(map));
	}
	/**
	 * 打印分页
	 * @param response
	 * @param object
	 * @param count
	 */
	public static void printlnPage(HttpServletResponse response, Object object, int total, int currentIndex){
		PrintWriter writer=getWriter(response);
		String json="{\"draw\":"+currentIndex+",\"recordsTotal\":"+total+",\"recordsFiltered\":"+total+",data:"+ JSON.toJSONString(object)+"}";
		writer.println(json);
	}
	/**
	 * 
	 */
	public static Map<String, Object> pages(Object object, Integer pageIndex, Integer pageSize, Integer total) {
		Map<String, Object> modelMap=new HashMap<String, Object>();
		modelMap.put("datas", object);
		modelMap.put("startIndex",((pageIndex-1)*pageSize)+1);
		Integer endIndex=pageIndex*pageSize;
		if (total<endIndex) {
			endIndex=total;
		}
		modelMap.put("endIndex",endIndex);
		modelMap.put("currendPage",pageIndex);
		Integer pages=0;
		if (total%pageSize>0) {
			pages=(total/pageSize)+1;
		}else {
			pages=total/pageSize;
		}
		modelMap.put("pages",pages);
		modelMap.put("total",total);
		return modelMap;
	}
	public static Map<String, Object> pagesNew(Object object,Object pinPaiMap, Integer pageIndex, Integer pageSize, Integer total) {
		Map<String, Object> modelMap=new HashMap<String, Object>();
		modelMap.put("datas", object);
		modelMap.put("pinPais", pinPaiMap);
		modelMap.put("startIndex",((pageIndex-1)*pageSize)+1);
		Integer endIndex=pageIndex*pageSize;
		if (total<endIndex) {
			endIndex=total;
		}
		modelMap.put("endIndex",endIndex);
		modelMap.put("currendPage",pageIndex);
		Integer pages=0;
		if (total%pageSize>0) {
			pages=(total/pageSize)+1;
		}else {
			pages=total/pageSize;
		}
		modelMap.put("pages",pages);
		modelMap.put("total",total);
		return modelMap;
	}


}
