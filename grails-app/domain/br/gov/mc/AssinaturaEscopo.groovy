package br.gov.mc

class AssinaturaEscopo {

    static belongsTo = [escopo:Escopo]
    Long userId
    private User user
    String tipoAssinatura
    Date dataAssinatura = new Date()    

    static auditable = true
    static transients = ['user']
    static constraints = {
        escopo (nullable:false)
        tipoAssinatura (blank:false, inList:["GESTOR", "FISCAL TECNICO","FISCAL REQUISITANTE","PREPOSTO FABRICA SOFTWARE" ]);
        dataAssinatura (nullable:false, display: false)
        userId (display:false)
    }
    
    String toString() {
           "Assinatura do " + tipoAssinatura 
    }

    boolean verificarAssinatura() {
        if(!escopo.assinaturas.size())
            return true

        for(AssinaturaEscopo assinaturas : escopo.assinaturas) {
            if(assinaturas.tipoAssinatura == tipoAssinatura)
                return false
        }

        return true
    }

    User getUser() {
        if (!user && userId) {
            user = User.get(userId)
        }
        return user
    }

    void setUser(User user) {
        userId = user.id
    }

}
