package com.casco.operationportal.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @program wings_smartoffice_platform
 * @description:
 * @author: fredric
 * @create: 2019/09/08 09:41
 */

@EnableTransactionManagement
@Configuration
@MapperScan("com.casco.operationportal.mapper")
public class MybatisPlusConfig {

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		// 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
		// paginationInterceptor.setOverflow(false);
		// 设置最大单页限制数量，默认 500 条，-1 不受限制
		// paginationInterceptor.setLimit(500);
		// 开启 count 的 join 优化,只针对部分 left join
//		paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));

		paginationInterceptor.setDialectType("mysql");
		return paginationInterceptor;
	}
}
