
<%@ page import="br.gov.mc.AssinaturaEscopo" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'assinaturaEscopo.label', default: 'AssinaturaEscopo')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-assinaturaEscopo" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-assinaturaEscopo" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list assinaturaEscopo">
			
				<g:if test="${assinaturaEscopoInstance?.escopo}">
				<li class="fieldcontain">
					<span id="escopo-label" class="property-label"><g:message code="assinaturaEscopo.escopo.label" default="Escopo" /></span>
					
						<span class="property-value" aria-labelledby="escopo-label"><g:link controller="escopo" action="show" id="${assinaturaEscopoInstance?.escopo?.id}">${assinaturaEscopoInstance?.escopo?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${assinaturaEscopoInstance?.tipoAssinatura}">
				<li class="fieldcontain">
					<span id="tipoAssinatura-label" class="property-label"><g:message code="assinaturaEscopo.tipoAssinatura.label" default="Tipo Assinatura" /></span>
					
						<span class="property-value" aria-labelledby="tipoAssinatura-label"><g:fieldValue bean="${assinaturaEscopoInstance}" field="tipoAssinatura"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${assinaturaEscopoInstance?.dataAssinatura}">
				<li class="fieldcontain">
					<span id="dataAssinatura-label" class="property-label"><g:message code="assinaturaEscopo.dataAssinatura.label" default="Data Assinatura" /></span>
					
						<span class="property-value" aria-labelledby="dataAssinatura-label"><g:formatDate date="${assinaturaEscopoInstance?.dataAssinatura}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${assinaturaEscopoInstance?.userId}">
				<li class="fieldcontain">
					<span id="userId-label" class="property-label"><g:message code="assinaturaEscopo.userId.label" default="User Id" /></span>
					
						<span class="property-value" aria-labelledby="userId-label"><g:fieldValue bean="${assinaturaEscopoInstance}" field="userId"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${assinaturaEscopoInstance?.id}" />
					<g:link class="edit" action="edit" id="${assinaturaEscopoInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
