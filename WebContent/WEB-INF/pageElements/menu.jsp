<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<h4>
	<bean:message key="news" />
</h4>

<ul>
	<li><a href="news.do?method=newsList"> <bean:message
				key="news.list" />
	</a></li>
	<li><a href="news.do?method=addNewsPage"> <bean:message
				key="add.news" />
	</a></li>
</ul>
