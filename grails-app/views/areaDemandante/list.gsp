
<%@ page import="br.gov.mc.AreaDemandante" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'areaDemandante.label', default: 'AreaDemandante')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-areaDemandante" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-areaDemandante" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="sigla" title="${message(code: 'areaDemandante.sigla.label', default: 'Sigla')}" />
					
						<g:sortableColumn property="descricao" title="${message(code: 'areaDemandante.descricao.label', default: 'Descricao')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${areaDemandanteInstanceList}" status="i" var="areaDemandanteInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${areaDemandanteInstance.id}">${fieldValue(bean: areaDemandanteInstance, field: "sigla")}</g:link></td>
					
						<td>${fieldValue(bean: areaDemandanteInstance, field: "descricao")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${areaDemandanteInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
