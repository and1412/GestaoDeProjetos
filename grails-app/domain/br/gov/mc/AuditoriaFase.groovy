package br.gov.mc

class AuditoriaFase {

    static belongsTo = [fase:Fase]
    Date dataInicioAuditoria = new Date()
    Date dataFimAuditoria  

    Long servidorQualidadeId
    private User servidorQualidade  

   	static auditable = true
    static transients = ['servidorQualidade']
    static constraints = {
        fase(nullable:false)
        dataInicioAuditoria (nullable:false, display:false) 
        dataFimAuditoria (nullable:false, display:false)
        servidorQualidadeId (display:false)     
    }
    
    String toString() {"Auditoria Fase $dataInicioAuditoria - $id" }

    User getServidorQualidade() {
        if (!servidorQualidade && servidorQualidadeId) {
            servidorQualidade = User.get(servidorQualidadeId)
        }
        return servidorQualidade
    }

    void setServidorQualidade(User servidorQualidade) {
        servidorQualidadeId = servidorQualidade.id
    }

}
