package kr.co.lotte.mallserver;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@SpringBootApplication
@ComponentScan(basePackages = "kr.co.lotte")
@MapperScan("kr.co.lotte.mapper")
public class MallServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallServerApplication.class, args);
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource datasource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(datasource);
		Resource[] arrResource = new PathMatchingResourcePatternResolver()
	            .getResources("classpath:mappers/*Mapper.xml");
        sqlSessionFactoryBean.setMapperLocations(arrResource);
		
		return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
	}
}
