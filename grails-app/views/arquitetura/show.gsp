
<%@ page import="br.gov.mc.Arquitetura" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'arquitetura.label', default: 'Arquitetura')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-arquitetura" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-arquitetura" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list arquitetura">
			
				<g:if test="${arquiteturaInstance?.linguagem}">
				<li class="fieldcontain">
					<span id="linguagem-label" class="property-label"><g:message code="arquitetura.linguagem.label" default="Linguagem" /></span>
					
						<span class="property-value" aria-labelledby="linguagem-label"><g:fieldValue bean="${arquiteturaInstance}" field="linguagem"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${arquiteturaInstance?.servidorWeb}">
				<li class="fieldcontain">
					<span id="servidorWeb-label" class="property-label"><g:message code="arquitetura.servidorWeb.label" default="Servidor Web" /></span>
					
						<span class="property-value" aria-labelledby="servidorWeb-label"><g:fieldValue bean="${arquiteturaInstance}" field="servidorWeb"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${arquiteturaInstance?.bancoDados}">
				<li class="fieldcontain">
					<span id="bancoDados-label" class="property-label"><g:message code="arquitetura.bancoDados.label" default="Banco Dados" /></span>
					
						<span class="property-value" aria-labelledby="bancoDados-label"><g:fieldValue bean="${arquiteturaInstance}" field="bancoDados"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${arquiteturaInstance?.componentes}">
				<li class="fieldcontain">
					<span id="componentes-label" class="property-label"><g:message code="arquitetura.componentes.label" default="Componentes" /></span>
					
						<span class="property-value" aria-labelledby="componentes-label"><g:fieldValue bean="${arquiteturaInstance}" field="componentes"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${arquiteturaInstance?.id}" />
					<g:link class="edit" action="edit" id="${arquiteturaInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
