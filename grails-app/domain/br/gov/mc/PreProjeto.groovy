package br.gov.mc

class PreProjeto {

	Integer ordemServico
    String nome
    String descricao
    Date dataInicio
    Date dataFim
    Long userId
    private User user
    
    static hasMany = [preProjetoOcorrencias: PreProjetoOcorrencia, projetos: Projeto]    
    static auditable = true
    static transients = ['user']
    static constraints = {
         ordemServico (nullable:false)
         nome (blank:false,nullable:false, maxSize:100)
         descricao (blank:false,nullable:false, maxSize:200) 
         dataInicio (nullable:false)
         dataFim (nullable:false)
         preProjetoOcorrencias()
         projetos()
         userId (display:false)
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

    String toString() {"Pré-Projeto: $nome" }
}
