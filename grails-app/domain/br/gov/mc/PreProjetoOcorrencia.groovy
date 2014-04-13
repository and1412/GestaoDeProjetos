package br.gov.mc

class PreProjetoOcorrencia {

  static belongsTo = [preProjeto: PreProjeto]

  Date dataOcorrencia
  String descricaoOcorrencia
  byte[] documentoRelacionado
  String tipo_arquivo
  String nome_arquivo
  Long userId
  private User user

  static auditable = true
  static transients = ['user']
  static constraints = {
    preProjeto (nullable:false) 
    dataOcorrencia (nullable:false)
    descricaoOcorrencia (blank: false, nullable:false, maxSize: 260)
    documentoRelacionado  (nullable:true, maxSize: 5 * 1024 * 1024, fileManager: [name: 'nome_arquivo', type: 'tipo_arquivo'])
    tipo_arquivo (blank:true, nullable:true, display: false)
    nome_arquivo  (blank:true, nullable:true, display: false)
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
    
  String toString() {"Numero da ocorrência: $id" }
}
