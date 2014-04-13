package br.gov.mc

class NotificarService {

    static transactional = false

    def mailService 

    def emitirAlerta(String usuario, String email, String mensagem) {
            mailService.sendMail{
                to email
                from "gestaoprojetosmc@gmail.com"
                subject "Saudações do sistema Gestão de Projetos do MC"
                body "Olá! ${usuario}, \n ${mensagem}"
            }
    }

    //Atenção! Esse é um serviço para gerar uma coleção de dados de usuário para teste.
    def prepararDados(){
        //Criando Map de contatos que serão alertados
        def dados = [[nome:"André Cruz",email:"andrelink14@hotmail.com"],[nome:"André Cruz",email:"andrelink14@gmail.com"]]
        //Adicionando novos contatos
        dados << [nome:"Lellis",email:"lellismm@gmail.com"]
        return dados

    }

    def prepararMensagem(){
        def mensagem = "Seja bem-vindo ao sistema!"
    }
}

