package cn.itcast.bos.service.base.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.service.base.SubAreaService;

@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {

	@Autowired
	private SubAreaDao dao;

	@Override
	public void save(SubArea model) {
		if(model.getId()==null){
			model.setId(UUID.randomUUID().toString());
		}
		dao.save(model);
	}

	@Override
	public Page<SubArea> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<SubArea> findAll() {
		return dao.findAll();
	}

	@Override
	public void exportXls(OutputStream out,FileInputStream in) {
		List<SubArea> list = dao.findAll();
		HSSFWorkbook book = null;
		try {
			book = new HSSFWorkbook(in);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		HSSFSheet sheet = book.getSheetAt(0);
		
		HSSFCellStyle cellStyle = book.getSheetAt(1).getRow(0).getCell(0).getCellStyle();
		
//		分拣编号	省	市	区	关键字	起始号	终止号	单双号	辅助关键字
		int rowIndex=2;
		HSSFRow row = null;
		HSSFCell cell = null;
		for (SubArea subArea : list) {
			row = sheet.createRow(rowIndex);
			cell = row.createCell(0);
			cell.setCellValue(subArea.getId());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(1);
			cell.setCellValue(subArea.getArea().getProvince());
			cell.setCellStyle(cellStyle);
			
			cell = row.createCell(2);
			cell.setCellValue(subArea.getArea().getCity());
			cell.setCellStyle(cellStyle);
			
			cell=row.createCell(3);
			cell.setCellValue(subArea.getArea().getDistrict());
			cell.setCellStyle(cellStyle);
			
			cell=row.createCell(4);
			cell.setCellValue(subArea.getKeyWords());
			cell.setCellStyle(cellStyle);
			
			cell=row.createCell(5);
			cell.setCellValue(subArea.getStartNum());
			cell.setCellStyle(cellStyle);
			
			cell=row.createCell(6);
			cell.setCellValue(subArea.getEndNum());
			cell.setCellStyle(cellStyle);
			
			cell=row.createCell(7);
			cell.setCellValue(subArea.getSingle());
			cell.setCellStyle(cellStyle);
			
			row.createCell(8);
			cell.setCellValue(subArea.getAssistKeyWords());
			cell.setCellStyle(cellStyle);
			rowIndex++;
		}
		try {
			book.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public List showChart() {
		// [[昌平区,15],[顺义区,10],[西城区,8]]  
//		[{name:昌平区,num:15},{name:顺义区,num:10},{name:西城区,num:8}]
		return dao.showChart();
	}
}
