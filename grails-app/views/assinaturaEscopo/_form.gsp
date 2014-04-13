<%@ page import="br.gov.mc.AssinaturaEscopo" %>



<div class="fieldcontain ${hasErrors(bean: assinaturaEscopoInstance, field: 'escopo', 'error')} required">
	<label for="escopo">
		<g:message code="assinaturaEscopo.escopo.label" default="Escopo" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="escopo" name="escopo.id" from="${br.gov.mc.Escopo.list()}" optionKey="id" required="" value="${assinaturaEscopoInstance?.escopo?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: assinaturaEscopoInstance, field: 'tipoAssinatura', 'error')} required">
	<label for="tipoAssinatura">
		<g:message code="assinaturaEscopo.tipoAssinatura.label" default="Tipo Assinatura" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="tipoAssinatura" from="${assinaturaEscopoInstance.constraints.tipoAssinatura.inList}" required="" value="${assinaturaEscopoInstance?.tipoAssinatura}" valueMessagePrefix="assinaturaEscopo.tipoAssinatura"/>
</div>

