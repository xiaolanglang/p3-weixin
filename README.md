Jeewx 微信插件式开发框架，适合于微信管家系统，扩展第三方插件，插件以JAR形式存在
==========
p3-weixin
===============

架构技术介绍

    1.P3-weixin 采用SpringMvc + Mybatis + Velocity + Maven(构建) 框架技术
    2.插件引入方式
        pom.xml文件中，引入新开发的插件
        <!-- P3 jar -->
 	    <dependency>
			<groupId>org.jeecgframework</groupId>
			<artifactId>P3-Biz-gzbargain</artifactId>
			<version>2.0.0</version>
		</dependency>
	3.项目启动访问方式：
	  采用maven方式，启动Web项目
      http://localhost:8080/P3-Web/{页面访问地址}
    4.页面层面不能采用jsp，需要采用模板语言Velocity
    5.实现插件式开发，按照模块进行开发，每个模块可以单独达成jar包
	6.数据库配置文件：
	  src/main/resources/db.properties
	  
开发环境入门

    1.Eclipse + Maven + JDK7
    2.项目以Maven方式导入eclipse
	3.采用maven方式，启动主项目P3-Web，命令：tomcat:run
      活动访问地址：http://localhost:8080/P3-Web/gzbargain/toIndex.do?actId=actgzbargain00001&openid=oR0jFt_DTsAUJebWqGeq3A1VWfRw&subscribe=1
	  说明：插件不能单独启动，maven方式引入到Web项目

	  
### 微信插件说明（陆续更新...）
	  1.微信砍价活动   P3-Biz-gzbargain
	  
	  
### Jeecg开源社区	  
* [Jeewx 捷微官网](http://www.jeewx.com)
* [Jeewx 捷微演示](http://www.jeewx.com/jeewx)
* [Jeewx 技术论坛](http://www.jeecg.org)
*  Jeewx微信开发群四；413534092