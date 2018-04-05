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

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="newspaper" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->

	<security:authorize access="hasRole('USER')">
	<display:column><acme:links url="newspaper/user/edit.do?newspaperId=${row.id}" code="newspaper.edit" /></display:column>
	</security:authorize>
	
	<acme:column property="title" code="newspaper.title" />
	<acme:column property="publication" code="newspaper.publication" />
	<acme:column property="description" code="newspaper.description" />
	<spring:message code="newspaper.picture" var="picture"/>
	<display:column><img class="imagesNewspaper" src="${row.picture}"></display:column>

	<display:column> <acme:links url="article/list.do?newspaperId=${row.id}" code="newspaper.articles" /> </display:column>

</display:table>

<!-- Action links -->

<br><security:authorize access="hasRole('USER')">
		<display:column> <acme:links url="newspaper/user/create.do" code="newspaper.create" /> </display:column>
</security:authorize>
