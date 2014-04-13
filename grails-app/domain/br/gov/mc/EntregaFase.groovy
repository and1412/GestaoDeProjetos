package br.gov.mc

class EntregaFase {

    static belongsTo = [fase:Fase]
    Date dataEntrega = new Date()

    Long servidorFabricaId
    private User servidorFabrica
    
    static auditable = true
    static transients = ['servidorFabrica']
    static constraints = {
        fase (nullable:false)
        dataEntrega (nullable:false, display:false)
        servidorFabricaId (display:false)
    }
    
    String toString() {"Entrega Fase - $id - $dataEntrega - $fase"}

    User getServidorFabrica() {
        if (!servidorFabrica && servidorFabricaId) {
            servidorFabrica = User.get(servidorFabricaId)
        }
        return servidorFabrica
    }

    void setServidorFabrica(User servidorFabrica) {
        servidorFabricaId = servidorFabrica.id
    }
}
