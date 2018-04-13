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

<security:authorize access="hasRole('CUSTOMER')">
<form:form action="${requestURI}" modelAttribute="subscription">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	<form:hidden path="newspaper" />
	
	<acme:textbox path="creditcard.holderName" code="subscription.creditCard.holderName" />
	<acme:textbox path="creditcard.brandName" code="subscription.creditCard.brandName" />
	<acme:textbox path="creditcard.number" code="subscription.creditCard.number" />
	<acme:textbox path="creditcard.expirationMonth" code="subscription.creditCard.expirationMonth" />
	<acme:textbox path="creditcard.expirationYear" code="subscription.creditCard.expirationYear" />
	<acme:textbox path="creditcard.CVV" code="subscription.creditCard.CVV" />
	
	<!-- Buttons -->
	
	<acme:submit name="save" code="subscription.submit" />
	<acme:cancel url="/" code="subscription.cancel" />
	
</form:form>
</security:authorize>


