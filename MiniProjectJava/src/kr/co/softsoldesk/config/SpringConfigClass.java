package kr.co.softsoldesk.config;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.cglib.core.internal.CustomizerRegistry;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//SpringMVCXML/WebContent/WEB-INF/web.xml
/*
public class SpringConfigClass implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//System.out.println("onStartup");
		//(web.xml에서<servlet> 구현부와 같음)
		//프로젝트 구현을 위한 클래스 객체 생성
		AnnotationConfigWebApplicationContext servletAppContext=new AnnotationConfigWebApplicationContext();
		servletAppContext.register(ServletAppContext.class);
		
		//요청 정보를 분석해서 컨트롤러를 선택하는 서블릿을 지칭한다
		DispatcherServlet dispatcherServlet=new DispatcherServlet(servletAppContext);
		//매개변수로 선언된 servletContext객체를 이용하여 servlet에 추가
		ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher", dispatcherServlet);
		
		//부가설정
		servlet.setLoadOnStartup(1);//추가 후 loadiing을 어떤경우라도 제일 먼저한다
		servlet.addMapping("/");//모든 경로에 적용하겠다
		
		//(wed.xml에서 <context-param>구현부와 같음)
		//Bean을 정의할 xml 파일을 지원한다
		AnnotationConfigWebApplicationContext rootAppContext=new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		//(wed.xml에서 <listener>구현부와 같음)
		ContextLoaderListener listener=new ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
		
		//(wed.xml에서 <filter>구현부와 같음)
		//파리미터 인코딩 설정
		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
	    //dispatcher에 의해서 추가된 Servlet에 UTF-8로 encoding하겠다는 구현부
		filter.addMappingForServletNames(null, false, "dispatcher");
		
	}
}*/

public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer {
	// DispatcherServlet에 매핑할 요청 주소를 셋팅한다.
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}

	// Spring MVC 프로젝트 설정을 위한 클래스를 지정한다.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { ServletAppContext.class };
	}

	// 프로젝트에서 사용할 Bean들을 정의기 위한 클래스를 지정한다.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { RootAppContext.class };
	}

	// 파라미터 인코딩 필터 설정
	@Override
	protected Filter[] getServletFilters() {
		// TODO Auto-generated method stub
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] { encodingFilter };
	}
	
	//MultipartResolver 세부정보
	//null : 사용자 지정이 아닌 서버가 제공하는 임시기억장소
	//52428800 : 업로드시 메모리 용량 할당
	//524288000 : 파일데이터를 포함한 전체용량
	//0 : 데이터를 받아서 자동으로 저장
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// TODO Auto-generated method stub
		super.customizeRegistration(registration);
		
		MultipartConfigElement config1=new MultipartConfigElement(null, 52428800, 524288000, 0);
		registration.setMultipartConfig(config1);
		
	}

}
