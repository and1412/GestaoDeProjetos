package br.gov.mc

class Artefato {

    String nome

    static auditable = true

    static constraints = {
        nome(blank:false, nullable:false)
    }
    
    String toString() {"$nome" }
}
