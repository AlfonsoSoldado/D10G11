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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestUri}" modelAttribute="newspaper">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="publication" />

	<acme:textbox code="newspaper.title" path="title" />
	<acme:textbox code="newspaper.description" path="description" />
	<acme:textbox code="newspaper.picture" path="picture" />
	<acme:selectBoolean code="newspaper.hide" path="hide" items="${hide}" />
	<acme:select items="${articles}" itemLabel="articles" code="newspaper.article" path="articles"/>
	
	<!-- Buttons -->
	
	<acme:submit name="save" code="newspaper.save" />
	<acme:delete confirmationCode="newspaper.confirm.delete" buttonCode="newspaper.delete" id="${newspaper.id}" />
	<acme:cancel url="newspaper/list.do" code="newspaper.cancel" />

</form:form>


