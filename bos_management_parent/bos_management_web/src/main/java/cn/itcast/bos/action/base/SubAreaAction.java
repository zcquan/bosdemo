package cn.itcast.bos.action.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;
import cn.itcast.bos.utils.FileUtils;

public class SubAreaAction extends BaseAction<SubArea> {

	@Autowired
	private SubAreaService serivce;
	
	@Action("subAreaAction_save")
	public void save(){
		try {
			serivce.save(model);
			ajaxReturn(true, "操作成功");
		} catch (Exception e) {
			ajaxReturn(false, "操作失败");
			e.printStackTrace();
		}
	}
	@Action("subAreaAction_exportXls")
	public void exportXls() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String filePath =ServletActionContext.getServletContext().getRealPath("/")+"template//"+"subArea.xls";
		FileInputStream  in = new FileInputStream(filePath);
		
		OutputStream outputStream = response.getOutputStream();
//		一个流
//		两个头
//		mime类型   Excel application/vnd.ms-excel
//		打开方式  默认是浏览器直接打开  inline 浏览器中打开     attachment 文件下载
//		response.setContentType("application/vnd.ms-excel");
		String  fileName = "分区数据导出.xls";
		String agent =  ServletActionContext.getRequest().getHeader("User-Agent");
		fileName = FileUtils.encodeDownloadFilename(fileName, agent);
		response.setHeader("content-disposition", "attachment;filename="+fileName);
		
		
		serivce.exportXls(outputStream,in);
	}
	@Action("subAreaAction_findByPage")
	public void findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<SubArea> page = serivce.findAll(pageable);
		Map map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		JavaToJSon(map);
	}
	@Action("subAreaAction_findAll")
	public void findAll(){
		List<SubArea> list = serivce.findAll();
		JavaToJSon(list);
	}
	@Action("subAreaAction_showChart")
	public void showChart(){
		List list = serivce.showChart(); 
		// [[昌平区：15]，[顺义区10],[西城区：8]]  
//		[{name:昌平区,num:15},{name:顺义区,num:10},{name:西城区,num:8}]
 		JavaToJSon(list);
	}
}
