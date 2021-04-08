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
	
	//properties 로부터, 고정된 DB정보를 읽어올것이므로 위에다가 @PropertySource올려놓고,
		//거기서 값들을 추출할때는 @Value("el표현식")으로 추출한다. 
		
		@Value("${db.classname}")
		private String db_classname;
		@Value("${db.url}")
		private String db_url;
		@Value("${db.username}")
		private String db_username;
		@Value("${db.password}")
		private String db_password;
		
		
		// 인터셉터를 쓰기위한 설정에서, 서비스 객체 오토와이어드필요함!!!!!!!!!!!!!!!!!!!!!!
		// 인터셉터에 오토와이어드를 올려놓을 수 없기때문에, 서블릿에서 객체만들어서 오토와이어드해놓아야한다. 
		
		@Autowired
		private TopMenuService topMenuService;
		
		@Autowired
		private BoardService boardService;
		
		@Resource(name="loginUserBean")
		private UserBean loginUserBean;
		
		//=========== 인터셉터 설정(addInterceptors 자동완성한 다음에 설정시작)=======================================
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
		
		WebMvcConfigurer.super.addInterceptors(registry);
	
		// 인터셉터 객체 생성하기
		TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topMenuService,loginUserBean);
		CheckLoginInterceptor checkLoginInterceptor=new CheckLoginInterceptor(loginUserBean);
		
		// 1) TopMenuInterceptor : 
		//뭐가들어오든 일단은 게시판 종류별로 top에 싹 세팅되어야 하므로, 인터셉터의레지스트리의 pathpatterns를 /** all로 준다. 
		
		InterceptorRegistration reg1=registry.addInterceptor(topMenuInterceptor);
		reg1.addPathPatterns("/**"); //어떤 주소가 들어오든, 메모리에 항상 떠있도록 레지스트리에 인터셉터 상주시킴.
	
		
		//2) CheckLoginInterceptor :
		//비로그인 상태에서만 인터셉트해야하므로, 적용할 경로(정보수정과 로그아웃, 보드의 모든것)들과 제외할 경로를 다르게준다.
		
		InterceptorRegistration reg2=registry.addInterceptor(checkLoginInterceptor);
		reg2.addPathPatterns("/user/modify_user","/user/logout","/board/*"); 
		reg2.excludePathPatterns("/board/main");
		
		CheckWriterInterceptor checkWriterInterceptor = new CheckWriterInterceptor(loginUserBean, boardService);
		InterceptorRegistration reg3 = registry.addInterceptor(checkWriterInterceptor);
		reg3.addPathPatterns("/board/modify", "/board/delete");

		}
		//----접속관련한 객체들을 만들어내야하므로 @Bean 써야한다.
		
		//데이터베이스 접속 정보 관리 : DataSource. 반환은 세팅끝낸 source
		@Bean
		public BasicDataSource dataSource() {
			
			BasicDataSource source=new BasicDataSource();
			source.setDriverClassName(db_classname);
			source.setUrl(db_url);
			source.setUsername(db_username);
			source.setPassword(db_password);
			
			return source;
		}
		
		//쿼리문과 DB간 접속 관리하는 객체 ---getCon();--- SessionFactory(여기에 매개변수로 접속정보 source가 들어온다)
		@Bean
		public SqlSessionFactory factory(BasicDataSource source) throws Exception{
			
			SqlSessionFactoryBean factoryBean=new SqlSessionFactoryBean();
			factoryBean.setDataSource(source);//위의 로그인정보를 붙여서 
			SqlSessionFactory factory=factoryBean.getObject();//object타입으로 붙여서 리턴
			
			return factory;//factory : 접속완료된 상태

		}
		
		//접속 끝났으니, 해당 접속에서 적용할 mapper를 등록. 
		//쿼리문 실행을 위한 객체(자료형 : 맵퍼인터페이스(sql문모여있는곳), 매개변수 : 세션. 열어놓은 세션에 내가 설정한 맵퍼팩토리빈을 붙여야함)
		//--- MapperFactory(Mapper Proxy 생성)
		//------Mapper인터페이스를 이용하려면 여기에서 맵퍼팩토리빈으로 설정해서 컨테이너에 올려놔야한다.
		
		//BoardMapper
		@Bean
		public MapperFactoryBean<BoardMapper> getBoardMapper(SqlSessionFactory factory) throws Exception{
			//MyBatis 맵퍼인터페이스에 injection을 할 수 있게 하는 MapperFactoryBean(자료형은 내가만들어놓은 맵퍼인터페이스)
			MapperFactoryBean<BoardMapper> factoryBean=new MapperFactoryBean<BoardMapper>(BoardMapper.class);
			factoryBean.setSqlSessionFactory(factory);
			return factoryBean;
		}
		
		//TopMenuMapper
		@Bean
		public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception{
			//MyBatis 맵퍼인터페이스에 injection을 할 수 있게 하는 MapperFactoryBean(자료형은 내가만들어놓은 맵퍼인터페이스)
			MapperFactoryBean<TopMenuMapper> factoryBean=new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
			factoryBean.setSqlSessionFactory(factory);
			return factoryBean;
		}
		//UserMapper
		@Bean
		public MapperFactoryBean<UserMapper> getUserMenuMapper(SqlSessionFactory factory) throws Exception{
			//MyBatis 맵퍼인터페이스에 injection을 할 수 있게 하는 MapperFactoryBean(자료형은 내가만들어놓은 맵퍼인터페이스)
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
	
		
	//=============프로퍼티관련===========
	
	//프로퍼티에서 에러메시지 뽑아서 뷰까지 가기위해 리로더블 넣기
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		 ReloadableResourceBundleMessageSource res=new ReloadableResourceBundleMessageSource();
		//확장자빼고 베이스네임넣기
		 res.setBasenames("/WEB-INF/properties/error_message");
		 return res;
	}
	
	// 유효성 검사시 뷰단에서 필요한 error_message.properties와, 접속을 위해 백단에서 필요한 db.properties 파일이 충돌하므로, 
	// 둘을 분리시키기 위해 PropertySourcesPlaceholderConfigurer를 설정해준다.
	// 자바에서는 하이버네이트에서 기본으로 제공하는 메시지와 프로퍼티즈가 충돌한다고 봐도된다.
	@Bean
	public static PropertySourcesPlaceholderConfigurer  PropertySourcesPlaceholderConfigurer() {
		
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
	//==========MutipartResolver 등록 : 정적데이터의 별도처리위함 =================
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		
		
		return new StandardServletMultipartResolver();//리졸버객체 생성하여 반환.
		//이 객체의 세부설정은 SpringConfigClass에서 한다. (web.xml역할을 하는곳)
	}
	
	
}
