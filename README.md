# topnb
Leopard监控系统，包含方法耗时监控、请求耗时监控、线程数量统计等功能。

官网使用文档<http://leopard.io/modules/topnb>

# Leopard官网的TopNB例子
<http://leopard.io/topnb/index.leo>

# pom.xml配置

```
	<dependencies>
		...
		<dependency>
			<groupId>io.leopard.topnb</groupId>
			<artifactId>topnb-profiler</artifactId>
		</dependency>
		<version>0.0.1</version>
		...
	</dependencies>
```

#启用方法耗时监控
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<import resource="classpath:/topnb/config.xml" />

</beans>
```

#如果你的webserver不支持@WebServlet、@WebListener
请在web.xml加入
```
	<absolute-ordering>
		<name>topnb_webfragment</name>
		...
	</absolute-ordering>
```


#查看统计数据
<http://localhost/topnb/index.leo>

