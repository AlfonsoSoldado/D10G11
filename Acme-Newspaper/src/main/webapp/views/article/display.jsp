<%--
 * display.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="article/display.do" modelAttribute="article">

<p>
	<span style="font-weight: bold;"> <spring:message
			code="article.title" var="titleHeader" /> <jstl:out
			value="${titleHeader}" />:
	</span>
	<jstl:out value="${article.title}" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="article.moment" var="momentHeader" /> <jstl:out
			value="${momentHeader}" />:
	</span>
	<jstl:out value="${article.moment}" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="article.summary" var="summaryHeader" /> <jstl:out
			value="${summaryHeader}" />:
	</span>
	<jstl:out value="${article.summary}" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="article.body" var="bodyHeader" /> <jstl:out
			value="${bodyHeader}" />:
	</span>
	<jstl:out value="${article.body}" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="article.pictures" var="picturesHeader" /> <jstl:out
			value="${picturesHeader}" />:
	</span>
	<jstl:forEach var="p" items="${pictures }">
		<img src="${p }"/>
	</jstl:forEach>
</p>

</form:form>

<div>
	<a href="#" onClick="history.back();"> <spring:message
			code="newspaper.back" />
	</a>
</div>
