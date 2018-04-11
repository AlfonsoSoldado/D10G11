<%--
 * list.jsp
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Listing grid -->

<form method=GET action="newspaper/customer/search.do">
	Search: <input type="text" name="criteria"> <input
		type="submit">
</form>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="newspaper" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->
	
	<acme:column property="title" code="newspaper.title" />
	<acme:column property="publication" code="newspaper.publication" />
	<acme:column property="description" code="newspaper.description" />
	<spring:message code="newspaper.picture" var="picture"/>
	<display:column><img class="imagesNewspaper" src="${row.picture}"></display:column>

	<display:column><acme:links url="user/display.do?userId=${row.publisher.id}" code="newspaper.publisher" /></display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column><acme:links url="subscription/customer/create.do?newspaperId=${row.id}" code="newspaper.subscription" /></display:column>
	</security:authorize>

</display:table>
