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

<form:form action="newspaper/display.do" modelAttribute="newspaper">

<p>
	<span style="font-weight: bold;"> <spring:message
			code="newspaper.title" var="titleHeader" /> <jstl:out
			value="${titleHeader}" />:
	</span>
	<jstl:out value="${newspaper.title}" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="newspaper.description" var="descriptionHeader" /> <jstl:out
			value="${descriptionHeader}" />:
	</span>
	<jstl:out value="${newspaper.description}" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="newspaper.picture" var="pictureHeader" /> <jstl:out
			value="${pictureHeader}" />:
	</span>
	<jstl:out value="${newspaper.picture}" />
</p>

<display:table name="articles" class="displaytag" id="row">
	
	<!-- Attributes -->

	<spring:message code="article.title" var="title" />:
	<display:column title ="${title}" sortable="true">
		<jstl:forEach var="title" items="${row.title}">
			<jstl:out value="${title}"></jstl:out><br/>
		</jstl:forEach>	
	</display:column>
	
	<spring:message code="article.summary" var="summary" />:
	<display:column title ="${summary}" sortable="true">
		<jstl:forEach var="summary" items="${row.summary}">
			<jstl:out value="${summary}"></jstl:out><br/>
		</jstl:forEach>	
	</display:column>
	
	<display:column><acme:links url="user/display.do?userId=${row.writer.id}" code="article.user" /></display:column>

</display:table>

</form:form>

<div>
	<a href="#" onClick="history.back();"> <spring:message
			code="newspaper.back" />
	</a>
</div>
