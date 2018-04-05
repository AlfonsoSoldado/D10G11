<%--
 * edit.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestUri}" modelAttribute="article">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />

	<acme:textbox code="article.title" path="title" />
	<acme:textbox code="article.summary" path="summary" />
	<acme:textbox code="article.body" path="body" />
	<acme:textbox code="article.pictures" path="pictures" />
	<acme:selectBoolean code="article.draftmode" path="draftmode" items="${draftmode}" />
	
	<!-- Buttons -->
	
	<acme:submit name="save" code="article.save" />
	<acme:delete confirmationCode="article.confirm.delete" buttonCode="article.delete" id="${article.id}" />
	<acme:cancel url="article/list.do" code="article.cancel" />

</form:form>


