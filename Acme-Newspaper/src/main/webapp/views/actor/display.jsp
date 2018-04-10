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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="user/display.do" modelAttribute="user">

<security:authorize access="hasRole('USER')">
<display:table name="followTable" class="displaytag" id="row">

<jstl:if test="${row.id != currentUserId }">
	<display:column><acme:links url="user/follow.do?userId=${row.id}" code="actor.follow" /></display:column>
	<display:column><acme:links url="user/unfollow.do?userId=${row.id}" code="actor.unfollow" /></display:column>
</jstl:if>

<display:column><acme:links url="user/listFollowers.do?userId=${row.id }" code="actor.followers" /></display:column>
<display:column><acme:links url="user/listFollowing.do?userId=${row.id }" code="actor.following" /></display:column>

</display:table>
</security:authorize>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.name" var="nameHeader" /> <jstl:out
			value="${nameHeader }" />:
	</span>
	<jstl:out value="${user.name }" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.surname" var="surnameHeader" /> <jstl:out
			value="${surnameHeader }" />:
	</span>
	<jstl:out value="${user.surname }" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.email" var="emailHeader" /> <jstl:out
			value="${emailHeader }" />:
	</span>
	<jstl:out value="${user.email }" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.phoneNumber" var="phoneNumberHeader" /> <jstl:out
			value="${phoneNumberHeader }" />:
	</span>
	<jstl:out value="${user.phoneNumber }" />
</p>

<p>
	<span style="font-weight: bold;"> <spring:message
			code="actor.postalAddress" var="postalAddressHeader" /> <jstl:out
			value="${postalAddressHeader }" />:
	</span>
	<jstl:out value="${user.postalAddress }" />
</p>
</form:form>

<spring:message code="actor.articles" var="articles" />
<h2><jstl:out value="${articles}"></jstl:out></h2>

<display:table name="articles" class="displaytag" id="row">
	
	<!-- Attributes -->
	
		<spring:message code="article.title" var="title" />:
	<display:column title ="${title}" sortable="true">
		<jstl:forEach var="title" items="${row.title}">
			<a href="article/display.do?articleId=${row.id}">${title }</a>
		</jstl:forEach>	
	</display:column>
	
	<acme:column property="moment" code="article.moment" />
	
	<acme:column property="summary" code="article.summary" />
	
</display:table>

<spring:message code="actor.chirp" var="chirp" />
<h2><jstl:out value="${chirp}"></jstl:out></h2>

<display:table name="chirps" class="displaytag" id="row">
	
	<!-- Attributes -->
	
	<security:authorize access="hasAnyRole('USER', 'ADMIN')">
	<display:column><acme:links url="chirp/user/edit.do?chirpId=${row.id}" code="chirp.edit" /></display:column>
	</security:authorize>

	<spring:message code="chirp.title" var="title" />:
	<display:column title ="${title}" sortable="true">
		<jstl:forEach var="title" items="${row.title}">
			<jstl:out value="${title}"></jstl:out><br/>
		</jstl:forEach>	
	</display:column>
	
	
	<spring:message code="chirp.description" var="description" />:
	<display:column title ="${description}" sortable="true">
		<jstl:forEach var="description" items="${row.description}">
			<jstl:out value="${description}"></jstl:out><br/>
		</jstl:forEach>	
	</display:column>
	
</display:table>

<security:authorize access="hasRole('USER')">
		<acme:links url="chirp/user/create.do" code="chirp.create" />
</security:authorize>