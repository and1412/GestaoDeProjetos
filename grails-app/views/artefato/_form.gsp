<%@ page import="br.gov.mc.Artefato" %>



<div class="fieldcontain ${hasErrors(bean: artefatoInstance, field: 'nome', 'error')} required">
	<label for="nome">
		<g:message code="artefato.nome.label" default="Nome" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="nome" required="" value="${artefatoInstance?.nome}"/>
</div>

