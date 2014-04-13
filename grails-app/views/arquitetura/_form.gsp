<%@ page import="br.gov.mc.Arquitetura" %>



<div class="fieldcontain ${hasErrors(bean: arquiteturaInstance, field: 'linguagem', 'error')} required">
	<label for="linguagem">
		<g:message code="arquitetura.linguagem.label" default="Linguagem" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="linguagem" from="${arquiteturaInstance.constraints.linguagem.inList}" required="" value="${arquiteturaInstance?.linguagem}" valueMessagePrefix="arquitetura.linguagem"/>
</div>

<div class="fieldcontain ${hasErrors(bean: arquiteturaInstance, field: 'servidorWeb', 'error')} required">
	<label for="servidorWeb">
		<g:message code="arquitetura.servidorWeb.label" default="Servidor Web" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="servidorWeb" from="${arquiteturaInstance.constraints.servidorWeb.inList}" required="" value="${arquiteturaInstance?.servidorWeb}" valueMessagePrefix="arquitetura.servidorWeb"/>
</div>

<div class="fieldcontain ${hasErrors(bean: arquiteturaInstance, field: 'bancoDados', 'error')} required">
	<label for="bancoDados">
		<g:message code="arquitetura.bancoDados.label" default="Banco Dados" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="bancoDados" from="${arquiteturaInstance.constraints.bancoDados.inList}" required="" value="${arquiteturaInstance?.bancoDados}" valueMessagePrefix="arquitetura.bancoDados"/>
</div>

<div class="fieldcontain ${hasErrors(bean: arquiteturaInstance, field: 'componentes', 'error')} required">
	<label for="componentes">
		<g:message code="arquitetura.componentes.label" default="Componentes" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="componentes" from="${arquiteturaInstance.constraints.componentes.inList}" required="" value="${arquiteturaInstance?.componentes}" valueMessagePrefix="arquitetura.componentes"/>
</div>

