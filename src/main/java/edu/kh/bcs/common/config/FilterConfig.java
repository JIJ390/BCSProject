package edu.kh.bcs.common.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.kh.bcs.common.filter.AdminFilter;
import jakarta.servlet.Filter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<AdminFilter> adminFilter() {

		FilterRegistrationBean<AdminFilter> filter = new FilterRegistrationBean<>();

		AdminFilter adminFilter = new AdminFilter();

		filter.setFilter(adminFilter);

		// 필터가 동작할 요청 경로 패턴 지정
		String[] filteringUrl = { "/admin" , "/admin/*"};
		filter.setUrlPatterns(Arrays.asList(filteringUrl));
		
		filter.setName("loginFilter");
		filter.setOrder(1);
 
		return filter;
	}

}
