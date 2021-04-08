package kr.co.softsoldesk.config;


import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.softsoldesk.beans.UserBean;
import kr.co.softsoldesk.interceptor.CheckLoginInterceptor;
import kr.co.softsoldesk.interceptor.CheckWriterInterceptor;
import kr.co.softsoldesk.interceptor.TopMenuInterceptor;
import kr.co.softsoldesk.mapper.BoardMapper;
import kr.co.softsoldesk.mapper.TopMenuMapper;
import kr.co.softsoldesk.mapper.UserMapper;
import kr.co.softsoldesk.service.BoardService;
import kr.co.softsoldesk.service.TopMenuService;


//SpringMVCXML/WebContent/WEB-INF/config/servlet-context.xml

@Configuration 
@EnableWebMvc
@ComponentScan("kr.co.softsoldesk.controller")
@ComponentScan("kr.co.softsoldesk.beans")
@ComponentScan("kr.co.softsoldesk.dao")
@ComponentScan("kr.co.softsoldesk.service")
@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer{
	
	//properties �κ���, ������ DB������ �о�ð��̹Ƿ� �����ٰ� @PropertySource�÷�����,
		//�ű⼭ ������ �����Ҷ��� @Value("elǥ����")���� �����Ѵ�. 
		
		@Value("${db.classname}")
		private String db_classname;
		@Value("${db.url}")
		private String db_url;
		@Value("${db.username}")
		private String db_username;
		@Value("${db.password}")
		private String db_password;
		
		
		// ���ͼ��͸� �������� ��������, ���� ��ü ������̾���ʿ���!!!!!!!!!!!!!!!!!!!!!!
		// ���ͼ��Ϳ� ������̾�带 �÷����� �� ���⶧����, �������� ��ü���� ������̾���س��ƾ��Ѵ�. 
		
		@Autowired
		private TopMenuService topMenuService;
		
		@Autowired
		private BoardService boardService;
		
		@Resource(name="loginUserBean")
		private UserBean loginUserBean;
		
		//=========== ���ͼ��� ����(addInterceptors �ڵ��ϼ��� ������ ��������)=======================================
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
		
		WebMvcConfigurer.super.addInterceptors(registry);
	
		// ���ͼ��� ��ü �����ϱ�
		TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topMenuService,loginUserBean);
		CheckLoginInterceptor checkLoginInterceptor=new CheckLoginInterceptor(loginUserBean);
		
		// 1) TopMenuInterceptor : 
		//���������� �ϴ��� �Խ��� �������� top�� �� ���õǾ�� �ϹǷ�, ���ͼ����Ƿ�����Ʈ���� pathpatterns�� /** all�� �ش�. 
		
		InterceptorRegistration reg1=registry.addInterceptor(topMenuInterceptor);
		reg1.addPathPatterns("/**"); //� �ּҰ� ������, �޸𸮿� �׻� ���ֵ��� ������Ʈ���� ���ͼ��� ���ֽ�Ŵ.
	
		
		//2) CheckLoginInterceptor :
		//��α��� ���¿����� ���ͼ�Ʈ�ؾ��ϹǷ�, ������ ���(���������� �α׾ƿ�, ������ ����)��� ������ ��θ� �ٸ����ش�.
		
		InterceptorRegistration reg2=registry.addInterceptor(checkLoginInterceptor);
		reg2.addPathPatterns("/user/modify_user","/user/logout","/board/*"); 
		reg2.excludePathPatterns("/board/main");
		
		CheckWriterInterceptor checkWriterInterceptor = new CheckWriterInterceptor(loginUserBean, boardService);
		InterceptorRegistration reg3 = registry.addInterceptor(checkWriterInterceptor);
		reg3.addPathPatterns("/board/modify", "/board/delete");

		}
		//----���Ӱ����� ��ü���� �������ϹǷ� @Bean ����Ѵ�.
		
		//�����ͺ��̽� ���� ���� ���� : DataSource. ��ȯ�� ���ó��� source
		@Bean
		public BasicDataSource dataSource() {
			
			BasicDataSource source=new BasicDataSource();
			source.setDriverClassName(db_classname);
			source.setUrl(db_url);
			source.setUsername(db_username);
			source.setPassword(db_password);
			
			return source;
		}
		
		//�������� DB�� ���� �����ϴ� ��ü ---getCon();--- SessionFactory(���⿡ �Ű������� �������� source�� ���´�)
		@Bean
		public SqlSessionFactory factory(BasicDataSource source) throws Exception{
			
			SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
			factoryBean.setDataSource(source);//���� �α��������� �ٿ��� 
			SqlSessionFactory factory=factoryBean.getObject();//objectŸ������ �ٿ��� ����
			
			return factory;//factory : ���ӿϷ�� ����

		}
		
		//���� ��������, �ش� ���ӿ��� ������ mapper�� ���. 
		//������ ������ ���� ��ü(�ڷ��� : �����������̽�(sql�����ִ°�), �Ű����� : ����. ������� ���ǿ� ���� ������ �������丮���� �ٿ�����)
		//--- MapperFactory(Mapper Proxy ����)
		//------Mapper�������̽��� �̿��Ϸ��� ���⿡�� �������丮������ �����ؼ� �����̳ʿ� �÷������Ѵ�.
		
		//BoardMapper
		@Bean
		public MapperFactoryBean<BoardMapper> getBoardMapper(SqlSessionFactory factory) throws Exception{
			//MyBatis �����������̽��� injection�� �� �� �ְ� �ϴ� MapperFactoryBean(�ڷ����� ������������ �����������̽�)
			MapperFactoryBean<BoardMapper> factoryBean=new MapperFactoryBean<BoardMapper>(BoardMapper.class);
			factoryBean.setSqlSessionFactory(factory);
			return factoryBean;
		}
		
		//TopMenuMapper
		@Bean
		public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception{
			//MyBatis �����������̽��� injection�� �� �� �ְ� �ϴ� MapperFactoryBean(�ڷ����� ������������ �����������̽�)
			MapperFactoryBean<TopMenuMapper> factoryBean=new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
			factoryBean.setSqlSessionFactory(factory);
			return factoryBean;
		}
		//UserMapper
		@Bean
		public MapperFactoryBean<UserMapper> getUserMenuMapper(SqlSessionFactory factory) throws Exception{
			//MyBatis �����������̽��� injection�� �� �� �ְ� �ϴ� MapperFactoryBean(�ڷ����� ������������ �����������̽�)
			MapperFactoryBean<UserMapper> factoryBean=new MapperFactoryBean<UserMapper>(UserMapper.class);
			factoryBean.setSqlSessionFactory(factory);
			return factoryBean;
			
		}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}
	
		
	//=============������Ƽ����===========
	
	//������Ƽ���� �����޽��� �̾Ƽ� ����� �������� ���δ��� �ֱ�
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		 ReloadableResourceBundleMessageSource res=new ReloadableResourceBundleMessageSource();
		//Ȯ���ڻ��� ���̽����ӳֱ�
		 res.setBasenames("/WEB-INF/properties/error_message");
		 return res;
	}
	
	// ��ȿ�� �˻�� ��ܿ��� �ʿ��� error_message.properties��, ������ ���� ��ܿ��� �ʿ��� db.properties ������ �浹�ϹǷ�, 
	// ���� �и���Ű�� ���� PropertySourcesPlaceholderConfigurer�� �������ش�.
	// �ڹٿ����� ���̹�����Ʈ���� �⺻���� �����ϴ� �޽����� ������Ƽ� �浹�Ѵٰ� �����ȴ�.
	@Bean
	public static PropertySourcesPlaceholderConfigurer  PropertySourcesPlaceholderConfigurer() {
		
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	//==========MutipartResolver ��� : ������������ ����ó������ =================
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		
		
		return new StandardServletMultipartResolver();//��������ü �����Ͽ� ��ȯ.
		//�� ��ü�� ���μ����� SpringConfigClass���� �Ѵ�. (web.xml������ �ϴ°�)
	}
	
	
}
