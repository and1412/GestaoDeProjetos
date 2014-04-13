package br.gov.mc

class AreaDemandante {

    String sigla
    String descricao
    
    static auditable = true
    
    static constraints = {
        sigla (blank:false,nullable:false, maxSize: 20)
        descricao (blank:false,nullable:false, maxSize: 100)
    }
    
    static mapping = {
        sort "sigla"
    }

    
    String toString() {"$sigla"}
    
}
