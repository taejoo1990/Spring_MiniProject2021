package kr.co.softsoldesk.config;


import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//정적데이터에 대한 리졸버등록을 하려면,   AbstractAnnotationConfigDispatcherServletInitializer 을 상속해야한다.
//(WebApplicationInitializer 인터페이스 구현으로는 안됨!)

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
		return new Class[] {ServletAppContext.class };
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
		return new Filter[] {encodingFilter};
	}
	
	
	//정적데이터를 위한 MultipartResolver의 세부설정
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		
		super.customizeRegistration(registration);
		//null: 사용자지정이 아닌 서버가 사용하는 임시기억장소 
		//52428800: 업로드시 메모리 할당 용량
		//524288000: 최대 요청파일용량
		// 0: fileSizeThreshold. 데이터를 받아서 자동으로 저장
		MultipartConfigElement config1=new MultipartConfigElement(null,52428800,524288000,0);
		registration.setMultipartConfig(config1);
	}
	
}



//====== 정적데이터에 대한 멀티파트리졸버가 없다면, WebApplicationInitializer 인터페이스 구현으로도 충분히 스프링구동가능하다. 

/*
public class SpringConfigClass implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//System.out.println("onStartup");
		//(web.xml에서<servlet> 구현부와 같음)
		//프로젝트 구현을 위한 클래스 객체 생성
		AnnotationConfigWebApplicationContext servletAppContext=new AnnotationConfigWebApplicationContext();
		servletAppContext.register(SevletAppContext.class);
		
		//요청 정보를 분석해서 컨트롤러를 선택하는 서블릿을 지칭한다
		DispatcherServlet dispatcherServlet=new DispatcherServlet(servletAppContext);
		//매개변수로 선언된 servletContext객체를 이용하여 servlet에 추가
		ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher", dispatcherServlet);
		
		//부가설정
		servlet.setLoadOnStartup(1);//추가 후 loading을 어떤경우라도 제일 먼저한다
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
		
		
		//====multipartResolver의 세부설정을 여기서한다.
		
		
		
		
	}

}*/