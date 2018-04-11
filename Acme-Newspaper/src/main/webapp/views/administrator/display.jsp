<%--
 * layout.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<h3>
	<spring:message code="administrator.averageNewspaperPerUser" />
</h3>
<p>
	<jstl:out value="${averageNewspaperPerUser}" />
</p>


<h3>
	<spring:message code="administrator.standardDesviationNewspaperPerUser" />
</h3>
<p>
	<jstl:out value="${standardDesviationNewspaperPerUser}" />
</p>


<h3>
	<spring:message code="administrator.averageArticlesPerUser" />
</h3>
<p>
	<jstl:out value="${averageArticlesPerUser}" />
</p>


<h3>
	<spring:message code="administrator.standardDesviationArticlesPerUser" />
</h3>
<p>
	<jstl:out value="${standardDesviationArticlesPerUser}" />
</p>


<h3>
	<spring:message code="administrator.averageArticlesPerNewspaper" />
</h3>
<p>
	<jstl:out value="${averageArticlesPerNewspaper}" />
</p>


<h3>
	<spring:message
		code="administrator.standardDesviationArticlesPerNewspaper" />
</h3>
<p>
	<jstl:out value="${standardDesviationArticlesPerNewspaper}" />
</p>



<h3>
	<jstl:if test="${newspapers10moreThanAvereage==null}">
		<spring:message code="administrator.newspapers10moreThanAvereage" />
		<br>
		<spring:message code="administrator.noHay" />

	</jstl:if>
</h3>
<p>
	<jstl:if test="${newspapers10moreThanAvereage!=null}">
		<h3>
			<spring:message code="administrator.newspapers10moreThanAvereage" />

		</h3>
		<jstl:forEach var="row" items="${newspapers10moreThanAvereage}">

			<jstl:out value="${row.title }"></jstl:out>
		</jstl:forEach>
	</jstl:if>
</p>

<h3>
	<jstl:if test="${newspapers10fewerThanAvereage==null}">
		<spring:message code="administrator.newspapers10moreThanAvereage" />
		<br>
		<spring:message code="administrator.noHay" />
	</jstl:if>
</h3>
<p>
	<jstl:if test="${newspapers10fewerThanAvereage!=null}">
		<h3>
			<spring:message code="administrator.newspapers10moreThanAvereage" />

		</h3>
		<jstl:forEach var="row" items="${newspapers10fewerThanAvereage}">

			<jstl:out value="${row.title }"></jstl:out>
			<br></br>
		</jstl:forEach>
	</jstl:if>
</p>



<h3>
	<spring:message code="administrator.ratioUsersCreatedEverNewspaper" />
</h3>
<p>
	<jstl:out value="${ratioUsersCreatedEverNewspaper}" />
</p>


<h3>
	<spring:message code="administrator.ratioUsersEverWrittenArticle" />
</h3>
<p>
	<jstl:out value="${ratioUsersEverWrittenArticle}" />
</p>


<h3>
	<spring:message code="administrator.averageFollowupsPerArticle" />
</h3>
<p>
	<jstl:out value="${averageFollowupsPerArticle}" />
</p>


<h3>
	<spring:message
		code="administrator.averageFollowupsPerArticleToOneWeekPublishedArticle" />
</h3>
<p>
	<jstl:out
		value="${averageFollowupsPerArticleToOneWeekPublishedArticle}" />
</p>


<h3>
	<spring:message
		code="administrator.averageFollowupsPerArticleToTwoWeekPublishedArticle" />
</h3>
<p>
	<jstl:out
		value="${averageFollowupsPerArticleToTwoWeekPublishedArticle}" />
</p>


<h3>
	<spring:message code="administrator.averageChirpsPerUser" />
</h3>
<p>
	<jstl:out value="${averageChirpsPerUser}" />
</p>


<h3>
	<spring:message code="administrator.standardDesviationChirpsPerUser" />
</h3>
<p>
	<jstl:out value="${standardDesviationChirpsPerUser}" />
</p>


<h3>
	<spring:message
		code="administrator.ratioUsersMorePosted75ChirpsOfAveragePerUser" />
</h3>
<p>
	<jstl:out value="${ratioUsersMorePosted75ChirpsOfAveragePerUser}" />
</p>




<h3>
	<spring:message code="administrator.ratioPublicVsPrivateNewspaper" />

</h3>
<jstl:forEach var="row" items="${ratioPublicVsPrivateNewspaper}">
	<h4>
		[
		<jstl:out value="${row }"></jstl:out>
		]
	</h4>
</jstl:forEach>






<h3>
	<spring:message
		code="administrator.averageArticlesPerNewspaperPrivates" />
</h3>
<p>
	<jstl:out value="${averageArticlesPerNewspaperPrivates}" />
</p>



<h3>
	<spring:message code="administrator.averageArticlesPerNewspaperPublics" />
</h3>
<p>
	<jstl:out value="${averageArticlesPerNewspaperPublics}" />
</p>




<h3>
	<spring:message
		code="administrator.ratioPrivateNewspaperSubsciptionsVsTotalCustomers" />

</h3>
<jstl:forEach var="row"
	items="${ratioPrivateNewspaperSubsciptionsVsTotalCustomers}">
	<h4>
		[
		<jstl:out value="${row }"></jstl:out>
		]
	</h4>
</jstl:forEach>




<h3>
	<spring:message
		code="administrator.AverageRatioPrivateVsPublicNewspaperPerPublisher" />

</h3>
<jstl:forEach var="row"
	items="${AverageRatioPrivateVsPublicNewspaperPerPublisher}">
	<h4>
		[
		<jstl:out value="${row }"></jstl:out>
		]
	</h4>
</jstl:forEach>


