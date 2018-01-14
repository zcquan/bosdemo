package cn.itcast.bos.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype") 
public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	protected T model;
	
	@Override
	public T getModel() {
		return model;
	}
	
	public BaseAction(){
		Type type = this.getClass().getGenericSuperclass();// BaseAction<Courier> 
		ParameterizedType pt = (ParameterizedType) type;
		Type[] types = pt.getActualTypeArguments();  //<Courier> 
		Class<T> classs = (Class<T>) types[0];   //Courier
		try {
			model = classs.newInstance();   //实例化
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected int page;//当前页码，如果开启分页栏后会自动传递过来
	protected int rows;//每页显示的条数，如果开启分页栏后会自动传递过来
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void JavaToJSon(Object obj){
		String jsonString = JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd",SerializerFeature.DisableCircularReferenceDetect); //转json时设置时间格式
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		try {
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * //data={"success":true|false,"message":"保存成功"|"保存失败"}
	 * @param success
	 * @param message
	 */
	public void ajaxReturn(Boolean success,String message){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("message", message);
//		把对象转json字符串
		String jsonString = JSON.toJSONString(map);
//		回写到浏览器上  
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		try {
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
