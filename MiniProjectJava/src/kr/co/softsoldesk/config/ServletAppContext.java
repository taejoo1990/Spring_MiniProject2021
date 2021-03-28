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
import kr.co.softsoldesk.interceptor.TopMenuInterceptor;
import kr.co.softsoldesk.mapper.BoardMapper;
import kr.co.softsoldesk.mapper.TopMenuMapper;
import kr.co.softsoldesk.mapper.UserMapper;
import kr.co.softsoldesk.service.TopMenuService;

// (servlet-context���� <annotation-driven/>�� ����)
@Configuration
// Controller�� ������̼��� �����Ǿ� �ִ� Ŭ������ ����ϴ� ������̼�
@EnableWebMvc
// ��ĵ�� ��Ű�� ���
@ComponentScan("kr.co.softsoldesk.controller")
@ComponentScan("kr.co.softsoldesk.service")
@ComponentScan("kr.co.softsoldesk.dao")
@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer {
	// xml������ servlet-context�� �ڹٷ� �����ϱ� ���� Ŭ����

	@Value("${db.classname}")
	private String db_classname;

	@Value("${db.url}")
	private String db_url;

	@Value("${db.username}")
	private String db_username;

	@Value("${db.password}")
	private String db_password;

	@Autowired
	private TopMenuService topMenuService;

	@Resource(name="loginUserBean") // root�� ���� �ֱ� ����
	private UserBean loginUserBean;

	// controller�޼��� (home())���� ��ȯ�ϴ� ���ڿ� ��(���), ��(Ȯ����)�� ���� ��θ� ����
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/", ".jsp");
	}

	// ���������� (�̹���, ����, ������, js, css) ��� ����
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");

	}

	// �����ͺ��̽� ���� ���� ����
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);

		return source;
	}

	// �������� ���� �����ϴ� ��ü(SqlSessionFactory ����, ���� ���� ��ü)
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		SqlSessionFactory factory = factoryBean.getObject();
		return factory;
	}

	// ������ ������ ���� ��ü(�������� �����ϴ� Mapper�� ����)
	@Bean
	public MapperFactoryBean<BoardMapper> getBoardMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<BoardMapper> factoryBean = new MapperFactoryBean<BoardMapper>(BoardMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	// ������ ������ ���� ��ü(�������� �����ϴ� Mapper�� ����)
		@Bean
		public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception {
			MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<UserMapper>(UserMapper.class);
			factoryBean.setSqlSessionFactory(factory);
			return factoryBean;
		}

	// ������ ������ ���� ��ü(�������� �����ϴ� Mapper�� ����)
	@Bean
	public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<TopMenuMapper> factoryBean = new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);

		TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topMenuService, loginUserBean);

		InterceptorRegistration reg1 = registry.addInterceptor(topMenuInterceptor); // �޸� Ȯ��
		
		reg1.addPathPatterns("/**");
		
		CheckLoginInterceptor checkLoginInterceptor = new CheckLoginInterceptor(loginUserBean);
		
        InterceptorRegistration reg2 = registry.addInterceptor(checkLoginInterceptor); // �޸� Ȯ�� (�޸� ������ �ȵǴ�)
		
		reg2.addPathPatterns("/user/modify", "/user/logout", "/board/*"); // �� ����
		reg2.excludePathPatterns("/board/main"); // ����	
	}
	// (�⺻�޼����� property�޼����� �浹)�ҽ��� �޼����� ������ �����ؾ� ��
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {

		ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
		res.setBasenames("/WEB-INF/properties/error_message");

		return res;
	}
	//MultipartResolver(����������)���
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver(); //��ü �����Ͽ� ��ȯ
	}

}
