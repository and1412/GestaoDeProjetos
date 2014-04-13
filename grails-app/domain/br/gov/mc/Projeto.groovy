package br.gov.mc

class Projeto {

    String nome
    String descricao
    String statusProjeto
    Date dataCadastro = new Date()
    AreaDemandante areaDemandante
    Long servidorDemandanteId
    private User servidorDemandante
    
    //definicao de prioridade do projeto
    String abrangencia
    String alinhamentoPDTI
    String alinhamentoPE
    String impacto
    String urgencia
    Integer prioridadeCalculdada
    Integer prioridadeCTI
    
    FabricaSoftware fabricaSoftware
    String localExecucao
    
    static auditable = true
    static hasMany = [escopos: Escopo,  arquitetura: Arquitetura]    
    static transients = ['servidorDemandante']
    static constraints = {
          nome (unique: true, blank:false,nullable:false, maxSize:60)
          descricao (blank:false,nullable:false, maxSize:200, widget:'textarea') 
          statusProjeto (blank:false, inList:["CADASTRADO","EM EXECUCAO","CONCLUIDO","CANCELADO","SUSPENSO"] )
          dataCadastro(nullable:false, display: false)
          areaDemandante (nullable:false)
          abrangencia (blank:false, inList:["DEPARTAMENTO","SECRETARIA","MINISTERIO"] )
          alinhamentoPDTI (blank:false, inList:["NAO PREVISTO", "PREVISTO"] )
          alinhamentoPE (blank:false, inList:["NAO PREVISTO", "PREVISTO"] )
          impacto (blank:false, inList:["BAIXO", "MEDIO", "ALTO"] )
          urgencia (blank:false, inList:["COMPROMISSO INTERNO", "COMPROMISSO EXTERNO", "PRAZO LEGAL"] )
          prioridadeCTI (nullable:false)
          prioridadeCalculdada(nullable:false)
          fabricaSoftware (nullable:false)
          localExecucao (blank:false, inList:["BRASILIA-DF", "BLUMENAU-SC"] )
          arquitetura (nullable:true)
          escopos(nullable:true)
          servidorDemandanteId (display:false)
    }
   
//    def calcularPrioridade (){
//        def pd = abrangencia.codigo + urgencia.codigo + impacto.codigo + alinhamentoPDTI.codigo + alinhamentoPE.codigo 
//        return pd
//    }
    User getServidorDemandante() {
        if (!servidorDemandante && servidorDemandanteId) {
            servidorDemandante = User.get(servidorDemandanteId)
        }
        return servidorDemandante
    }

    void setServidorDemandante(User servidorDemandante) {
        servidorDemandanteId = servidorDemandante.id
    }
    
    String toString() {"Projeto: $nome" }
}
