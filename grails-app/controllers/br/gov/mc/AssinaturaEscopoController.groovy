package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class AssinaturaEscopoController {

    def notificarService

    static allowedMethods = [
        show:'GET',
        save:'POST',
        delete:['POST','DELETE']
    ]

    def create() {
           [assinaturaEscopoInstance: new AssinaturaEscopo(params)]
    }

    def save() {
        def assinaturaEscopoInstance = new AssinaturaEscopo(params)
        
        if (!assinaturaEscopoInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [assinaturaEscopoInstance: assinaturaEscopoInstance])}
                json {
                    response.status = 403
                    render assinaturaEscopoInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render assinaturaEscopoInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'assinaturaEscopo.label', default: 'AssinaturaEscopo'), assinaturaEscopoInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: assinaturaEscopoInstance.id)
            }
            json {
                response.status = 201
                render assinaturaEscopoInstance as JSON
            }
            xml {
                response.status = 201
                render assinaturaEscopoInstance.id
            }
        }
    }

    def show() {
        def assinaturaEscopoInstance = AssinaturaEscopo.get(params.id)
        if (!assinaturaEscopoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'assinaturaEscopo.label', default: 'AssinaturaEscopo'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [assinaturaEscopoInstance: assinaturaEscopoInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def delete() {
        def assinaturaEscopoInstance = AssinaturaEscopo.get(params.id)
        if (!assinaturaEscopoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'assinaturaEscopo.label', default: 'AssinaturaEscopo'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            assinaturaEscopoInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'assinaturaEscopo.label', default: 'AssinaturaEscopo'), params.id])
                    redirect(action: "list")
                }
                json {
                    response.status = 204
                    render ''
                }
                xml {
                    response.status = 204
                    render ''
                }
            }
        }
        catch (DataIntegrityViolationException e) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'assinaturaEscopo.label', default: 'AssinaturaEscopo'), params.id])
                    redirect(action: "show", id: params.id)
                }
                json { response.sendError(500) }
                xml { response.sendError(500) }
            }
        }
    }

    def enviarAlerta(){
        //função preparar dados está gerando dados de teste, adapte para sua necessidade
        def dados = notificarService.prepararDados()
        //função preparar Mensagem está gerando dados de teste, adapte para sua necessidade
        def mensagem = notificarService.prepararMensagem()
        dados.each{
            notificarService.emitirAlerta(it.nome,it.email,mensagem)
        }
        render "E-mails enviados com sucesso"
    }
}
