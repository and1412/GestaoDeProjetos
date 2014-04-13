package br.gov.mc

class FabricaSoftware {

    String empresa
    String cnpj
    String numeroContrato
    Date dataContrato
    String endereco
    String cep
    String cidade
    String uf    
    String fone
    String fax
    

    
    static constraints = {
        // como fazer o formatador de cnpj/cep/fone
         empresa (blank:false,nullable:false, maxSize:120)
         cnpj (blank:false,nullable:false, maxSize:18)
         numeroContrato (blank:false,nullable:false, maxSize:18)
         dataContrato(nullable:false)
         endereco (blank:false,nullable:false, maxSize:120)  
         cep (blank:false,nullable:false, maxSize:10)
         cidade (blank:false,nullable:false, maxSize:50)
         uf (blank:false,nullable:false, maxSize:2)
         fone (blank:false,nullable:false, maxSize:15)
         fax (blank:false,nullable:false, maxSize:15)
    }
    
    String toString() {"Empresa: $empresa" }
}
