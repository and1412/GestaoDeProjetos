package br.gov.mc

class Fase {

    static belongsTo = [escopo:Escopo]
    String tipoFase
    Date dataInicio
    Date dataFim
    Float qtdePF
    
    // ver como faz para adiconar artefatos ao inves de escolher
    static hasMany = [ artefatos: Artefato, entregaFase: EntregaFase, auditoriaFase: AuditoriaFase ] 

    static auditable = true
    static constraints = {
        escopo (nullable:false)
        tipoFase (blank:false, inList:["FASE CONCEPCAO", "FASE ELABORACAO", "FASE CONSTRUCAO","FASE TRANSICAO","SPRINT"])
        dataInicio (nullable:false)
        dataFim (nullable:false)
        qtdePF (nullable:false)
        artefatos (nullable:true)
        entregaFase (nullable:true)
        auditoriaFase (nullable:true)
    }
    
    String toString() { "$escopo - $tipoFase" }
}
