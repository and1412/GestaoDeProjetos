<%@ page import="br.gov.mc.AreaDemandante" %>



<div class="fieldcontain ${hasErrors(bean: areaDemandanteInstance, field: 'sigla', 'error')} required">
	<label for="sigla">
		<g:message code="areaDemandante.sigla.label" default="Sigla" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="sigla" maxlength="20" size="20" required="" value="${areaDemandanteInstance?.sigla}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: areaDemandanteInstance, field: 'descricao', 'error')} required">
	<label for="descricao">
		<g:message code="areaDemandante.descricao.label" default="Descricao" />
		<span class="required-indicator">*</span>
	</label>
	<g:textArea name="descricao" cols="40" rows="5" maxlength="100" required="" value="${areaDemandanteInstance?.descricao}"/>
</div>

