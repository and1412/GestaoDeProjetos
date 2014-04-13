package br.gov.mc

class Funcionalidade {

    static belongsTo = [escopo:Escopo]
    String nome
    String descricao
    Float qtdeHST
    Float qtdePF

    static auditable = true
    static constraints = {
          escopo (nullable:false)
          nome (blank:false, nullable:false, maxSize:60)
          descricao (blank:false, nullable:false, maxSize:200)  
          qtdeHST (nullable:false)
          qtdePF (nullable:false)
    }
    
     String toString() {"$nome" }
}
