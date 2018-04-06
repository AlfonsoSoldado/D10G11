<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="/Acme-Newspaper"><img src="images/logo.png" alt="Acme-Rendezvous Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.edit.configuration" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('CUSTOMER')">
			<li><a class="fNiv"> <spring:message code="master.page.profile" /> (<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/customer/edit.do"><spring:message code="master.page.edit.profile" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('USER')">
			<li><a class="fNiv"> <spring:message code="master.page.profile" /> (<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/user/edit.do"><spring:message code="master.page.edit.profile" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li><a href="newspaper/user/listNotPublicated.do"><spring:message code="master.page.newspaper.list.notpublicated" /></a>
		</security:authorize>
		
		<security:authorize access="isAnonymous()">
		<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv" href=""><spring:message code="master.page.register" /></a>
				<ul>
					<li><a href="customer/register_Customer.do"><spring:message code="master.page.register.customer" /></a>
					<li><a href="user/register_User.do"><spring:message code="master.page.register.user" /></a>
				</ul>
			</li>
		<li><a href="newspaper/list.do"><spring:message code="master.page.newspaper.list" /></a>
		<li><a href="user/list.do"><spring:message code="master.page.user.list" /></a>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
		<li><a href="newspaper/list.do"><spring:message code="master.page.newspaper.list" /></a>
		<li><a href="user/list.do"><spring:message code="master.page.user.list" /></a>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
