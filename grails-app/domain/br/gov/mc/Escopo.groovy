package br.gov.mc

class Escopo {

    static belongsTo = [projeto: Projeto]
    
    String versao
    String descricao
    Date dataEmissao
    String statusEscopo = "ABERTO"
    Float quantidadePF  
    Float quantidadeHST
    
    //disciplinas contratadas
    boolean disciplinaLevantamentoRequisitos    
    boolean disciplinaAnaliseEprojeto
    boolean disciplinaDesenvolvimento
    boolean disciplinaTestes
    boolean disciplinaImplantacao

    
    static hasMany = [ funcionalidades: Funcionalidade, assinaturas: AssinaturaEscopo, fases: Fase ] 
    static auditable = true
    static constraints = {
        
        projeto (nullable:false)
         
        versao (blank:false, inList:["1.0", "2.0", "3.0","4.0","5.0","6.0","7.0","8.0","9.0","10.0"])
        
        dataEmissao (nullable:false)
        descricao (blank:false, nullable:false, maxSize: 250)
         
        //Aberto: montando o escopo - inserindo fases;
        // Bloqueado: a partir da primeira assinatura - ninquem mais modifica o escopo - notificar para assinatura;
        // Fechado: quando todos já assinaram o escopo: notificar o fechamento;
        statusEscopo (display:false)

        quantidadePF (nullable: false)
        quantidadeHST (nullable: false)
        
        disciplinaLevantamentoRequisitos ()
        disciplinaAnaliseEprojeto ()
        disciplinaDesenvolvimento ()
        disciplinaTestes ()
        disciplinaImplantacao ()

        funcionalidades (nullable:true)
        fases ()
        assinaturas (nullable:true, display: false)
    }
    
    String toString() {
           return "Escopo - $id - $projeto"
    }
}
