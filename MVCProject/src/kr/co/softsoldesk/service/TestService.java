package kr.co.softsoldesk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.softsoldesk.DAO.TestDAO;

@Service
public class TestService {

	@Autowired
	private TestDAO dao;
	
	public String testMethod() {
		String text=dao.testDAOMethod();
		return text;
	}
}
