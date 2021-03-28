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
		//(web.xml����<servlet> �����ο� ����)
		//������Ʈ ������ ���� Ŭ���� ��ü ����
		AnnotationConfigWebApplicationContext servletAppContext=new AnnotationConfigWebApplicationContext();
		servletAppContext.register(ServletAppContext.class);
		
		//��û ������ �м��ؼ� ��Ʈ�ѷ��� �����ϴ� ������ ��Ī�Ѵ�
		DispatcherServlet dispatcherServlet=new DispatcherServlet(servletAppContext);
		//�Ű������� ����� servletContext��ü�� �̿��Ͽ� servlet�� �߰�
		ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher", dispatcherServlet);
		
		//�ΰ�����
		servlet.setLoadOnStartup(1);//�߰� �� loadiing�� ����� ���� �����Ѵ�
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
		
	}
}*/

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
		return new Class[] { ServletAppContext.class };
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
		return new Filter[] { encodingFilter };
	}
	
	//MultipartResolver ��������
	//null : ����� ������ �ƴ� ������ �����ϴ� �ӽñ�����
	//52428800 : ���ε�� �޸� �뷮 �Ҵ�
	//524288000 : ���ϵ����͸� ������ ��ü�뷮
	//0 : �����͸� �޾Ƽ� �ڵ����� ����
	@Override
	protected void customizeRegistration(Dynamic registration) {
		// TODO Auto-generated method stub
		super.customizeRegistration(registration);
		
		MultipartConfigElement config1=new MultipartConfigElement(null, 52428800, 524288000, 0);
		registration.setMultipartConfig(config1);
		
	}

}
