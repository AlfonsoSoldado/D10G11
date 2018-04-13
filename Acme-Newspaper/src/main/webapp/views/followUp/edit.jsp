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

<form:form action="${requestURI}" modelAttribute="followUp">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<security:authorize access="hasRole('USER')">
	<acme:textbox code="followUp.title" path="title" />
	<acme:textbox code="followUp.summary" path="summary" />
	<acme:textbox code="followUp.text" path="text" />
	<acme:textbox code="followUp.pictures" path="pictures" />
	<acme:select items="${article}" itemLabel="title" code="followUp.article" path="article"/>
	
	<!-- Buttons -->
	
	<acme:submit name="save" code="followUp.submit" />
	<acme:cancel url="newspaper/list.do" code="followUp.cancel" />
	<%-- <acme:delete confirmationCode="followUp.confirm.delete" buttonCode="followUp.delete" id="${followUp.id}" /> --%>
	</security:authorize>
	
</form:form>


