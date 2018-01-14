package cn.itcast.bos.action.base;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
public class AreaAction extends BaseAction<Area>{

	private File myfile;
	public File getMyfile() {
		return myfile;
	}
	public void setMyfile(File myfile) {
		this.myfile = myfile;
	}

	
	@Autowired
	private AreaService service;
	
	@Action("areaAction_importXls")
	public void importXls(){
//		System.out.println(myfile);
		service.importXls(myfile);
		
	}
	
	private String q;
	public void setQ(String q) {
		this.q = q;
	}
	@Action("areaAction_findAll")
	public void findAll(){
		System.out.println("q-----------------:"+q);
//		q:shijiazhuang   SHIJIAZHUANG
//		q:sjz            SJZ
		List<Area> list = null;
//		select * from t_area where c_citycode like lower('%shijiazhuang%') or  c_shortcode like upper('%shijiazhuang%')
		if(q==null){
			list = service.findAll();
		}else{
			list = service.findAll("%"+q.toLowerCase()+"%","%"+q.toUpperCase()+"%");
		}
		JavaToJSon(list);
	}
	
	
	@Action("areaAction_findByPage")
	public void findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Area> page = service.findAll(pageable);
//		{"total":X,"rows":[{},{}]}
		Map<String, Object> map  = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		JavaToJSon(map);
	}

}
