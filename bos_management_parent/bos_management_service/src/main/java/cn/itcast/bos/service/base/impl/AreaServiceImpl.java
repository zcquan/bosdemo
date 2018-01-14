package cn.itcast.bos.service.base.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;

@Service
@Transactional
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDao dao;
	@Override
	public void importXls(File myfile) {
//		HSSFWorkbook  工作薄
//		HSSFSheet      工作表
//		HSSFRow        行
//		HSSFCell        单元格
		HSSFWorkbook book = null;
		try {
			book = new HSSFWorkbook(new FileInputStream(myfile));
			HSSFSheet sheet = book.getSheetAt(0);
			int lastRowIndex = sheet.getLastRowNum();
			HSSFRow row = null;
			Area area = null;
			for (int i = 1; i <= lastRowIndex; i++) {
				row = sheet.getRow(i);
//				区域编号	省份	城市	区域	邮编
				area = new Area();
				String id = row.getCell(0).getStringCellValue();
				area.setId(id);
				String province = row.getCell(1).getStringCellValue();
				area.setProvince(province);
				String city = row.getCell(2).getStringCellValue();
				area.setCity(city);
				String district = row.getCell(3).getStringCellValue();
				area.setDistrict(district);
				String postcode = row.getCell(4).getStringCellValue();
				area.setPostcode(postcode);
//				System.out.println(id+province+city+district+postcode);
				
				province =province.substring(0, province.length()-1);
				city =city.substring(0, city.length()-1);
				district =district.substring(0, district.length()-1);
				
//				System.out.println(province+city+district);
//				城市编码  shijiazhuang
//				简码  HBSJZQD
				String citycode = PinYin4jUtils.hanziToPinyin(city, "");
//				System.out.println(citycode);
				area.setCitycode(citycode);
				
				String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
				String shortcode = StringUtils.join(headByString);
//				System.out.println(shortcode);
				area.setShortcode(shortcode);
				
				
				dao.save(area);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	@Override
	public Page<Area> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}
	@Override
	public List<Area> findAll() {
		return dao.findAll();
	}
	@Override
	public List<Area> findAll(String string, String string2) {
//		select * from t_area where c_citycode like lower('%shijiazhuang%') or  c_shortcode like upper('%shijiazhuang%')
		return dao.findByCitycodeLikeOrShortcodeLike(string,string2);
	}

}
