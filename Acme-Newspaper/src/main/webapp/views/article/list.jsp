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

<form method=GET action="article/search.do">
	Search: <input type="text" name="criteria"> <input
		type="submit">
</form>

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="article" requestURI="${requestURI}" id="row">
	
	<!-- Attributes -->

	<security:authorize access="hasRole('USER')">
		<display:column><acme:links url="article/user/edit.do?articleId=${row.id}" code="article.edit" /></display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column><acme:links url="article/administrator/edit.do?articleId=${row.id}" code="article.delete" /></display:column>
	</security:authorize>
	
	<acme:column property="title" code="article.title" />
	<acme:column property="summary" code="article.summary" />
	<acme:column property="body" code="article.body" />
	<acme:column property="pictures" code="article.pictures"/>
	<acme:column property="draftmode" code="article.draftmode" />

	<display:column><acme:links url="user/display.do?userId=${row.writer.id}" code="article.user" /></display:column>
	<display:column> <acme:links url="followUp/list.do?articleId=${row.id}" code="article.followUp" /> </display:column>

</display:table>

<!-- Action links -->

<br><security:authorize access="hasRole('USER')">
		<acme:links url="article/user/create.do" code="article.create" />
</security:authorize>
