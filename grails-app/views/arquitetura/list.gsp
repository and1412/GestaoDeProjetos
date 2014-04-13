
<%@ page import="br.gov.mc.Arquitetura" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'arquitetura.label', default: 'Arquitetura')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-arquitetura" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-arquitetura" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="linguagem" title="${message(code: 'arquitetura.linguagem.label', default: 'Linguagem')}" />
					
						<g:sortableColumn property="servidorWeb" title="${message(code: 'arquitetura.servidorWeb.label', default: 'Servidor Web')}" />
					
						<g:sortableColumn property="bancoDados" title="${message(code: 'arquitetura.bancoDados.label', default: 'Banco Dados')}" />
					
						<g:sortableColumn property="componentes" title="${message(code: 'arquitetura.componentes.label', default: 'Componentes')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${arquiteturaInstanceList}" status="i" var="arquiteturaInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${arquiteturaInstance.id}">${fieldValue(bean: arquiteturaInstance, field: "linguagem")}</g:link></td>
					
						<td>${fieldValue(bean: arquiteturaInstance, field: "servidorWeb")}</td>
					
						<td>${fieldValue(bean: arquiteturaInstance, field: "bancoDados")}</td>
					
						<td>${fieldValue(bean: arquiteturaInstance, field: "componentes")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${arquiteturaInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
