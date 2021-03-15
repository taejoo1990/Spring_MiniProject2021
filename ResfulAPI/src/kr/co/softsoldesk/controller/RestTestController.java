package kr.co.softsoldesk.controller;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.softsoldesk.beans.DataBean;

@RestController
public class RestTestController {

	@GetMapping("/test2")
	public ResponseEntity<ArrayList<DataBean>> Test2(){
		DataBean bean1 = new DataBean("���ڿ�1", 100, 0.1, true);
		DataBean bean2 = new DataBean("���ڿ�2", 200, 0.2, true);
		DataBean bean3 = new DataBean("���ڿ�3", 300, 0.3, false);
		
		ArrayList<DataBean> list= new ArrayList();
		list.add(bean1);
		list.add(bean2);
		list.add(bean3);
		
		ResponseEntity<ArrayList<DataBean>> entity=new ResponseEntity<ArrayList<DataBean>>(list, HttpStatus.OK);
		return entity;
		
	}
	
}
