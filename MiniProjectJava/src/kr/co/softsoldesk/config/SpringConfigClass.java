package kr.co.softsoldesk.config;


import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//���������Ϳ� ���� ����������� �Ϸ���,   AbstractAnnotationConfigDispatcherServletInitializer �� ����ؾ��Ѵ�.
//(WebApplicationInitializer �������̽� �������δ� �ȵ�!)

public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer {
	// DispatcherServlet�� ������ ��û �ּҸ� �����Ѵ�.
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[] { "/" };
	}

	// Spring MVC ������Ʈ ������ ���� Ŭ������ �����Ѵ�.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] {ServletAppContext.class };
	}

	// ������Ʈ���� ����� Bean���� ���Ǳ� ���� Ŭ������ �����Ѵ�.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class[] { RootAppContext.class };
	}

	// �Ķ���� ���ڵ� ���� ����
	@Override
	protected Filter[] getServletFilters() {
		// TODO Auto-generated method stub
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
	
	
	//���������͸� ���� MultipartResolver�� ���μ���
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		
		super.customizeRegistration(registration);
		//null: ����������� �ƴ� ������ ����ϴ� �ӽñ����� 
		//52428800: ���ε�� �޸� �Ҵ� �뷮
		//524288000: �ִ� ��û���Ͽ뷮
		// 0: fileSizeThreshold. �����͸� �޾Ƽ� �ڵ����� ����
		MultipartConfigElement config1=new MultipartConfigElement(null,52428800,524288000,0);
		registration.setMultipartConfig(config1);
	}
	
}



//====== ���������Ϳ� ���� ��Ƽ��Ʈ�������� ���ٸ�, WebApplicationInitializer �������̽� �������ε� ����� ���������������ϴ�. 

/*
public class SpringConfigClass implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		//System.out.println("onStartup");
		//(web.xml����<servlet> �����ο� ����)
		//������Ʈ ������ ���� Ŭ���� ��ü ����
		AnnotationConfigWebApplicationContext servletAppContext=new AnnotationConfigWebApplicationContext();
		servletAppContext.register(SevletAppContext.class);
		
		//��û ������ �м��ؼ� ��Ʈ�ѷ��� �����ϴ� ������ ��Ī�Ѵ�
		DispatcherServlet dispatcherServlet=new DispatcherServlet(servletAppContext);
		//�Ű������� ����� servletContext��ü�� �̿��Ͽ� servlet�� �߰�
		ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher", dispatcherServlet);
		
		//�ΰ�����
		servlet.setLoadOnStartup(1);//�߰� �� loading�� ����� ���� �����Ѵ�
		servlet.addMapping("/");//��� ��ο� �����ϰڴ�
		
		//(wed.xml���� <context-param>�����ο� ����)
		//Bean�� ������ xml ������ �����Ѵ�
		AnnotationConfigWebApplicationContext rootAppContext=new AnnotationConfigWebApplicationContext();
		rootAppContext.register(RootAppContext.class);
		
		//(wed.xml���� <listener>�����ο� ����)
		ContextLoaderListener listener=new ContextLoaderListener(rootAppContext);
		servletContext.addListener(listener);
		
		//(wed.xml���� <filter>�����ο� ����)
		//�ĸ����� ���ڵ� ����
		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
		filter.setInitParameter("encoding", "UTF-8");
	    //dispatcher�� ���ؼ� �߰��� Servlet�� UTF-8�� encoding�ϰڴٴ� ������
		filter.addMappingForServletNames(null, false, "dispatcher");
		
		
		//====multipartResolver�� ���μ����� ���⼭�Ѵ�.
		
		
		
		
	}

}*/