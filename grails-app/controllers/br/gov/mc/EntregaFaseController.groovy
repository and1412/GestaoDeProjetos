package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class EntregaFaseController {


    def notificarService

    static allowedMethods = [list:'GET',
        show:'GET',
        edit:['GET', 'POST'],
        save:'POST',
        update:['POST','PUT'],
        delete:['POST','DELETE']
    ]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 50, 200)
        def list = EntregaFase.list(params)
        def listObject = [entregaFaseInstanceList: list, entregaFaseInstanceTotal: EntregaFase.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [entregaFaseInstance: new EntregaFase(params)]
    }

    def save() {
        def entregaFaseInstance = new EntregaFase(params)
        
        if (!entregaFaseInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [entregaFaseInstance: entregaFaseInstance])}
                json {
                    response.status = 403
                    render entregaFaseInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render entregaFaseInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'entregaFase.label', default: 'EntregaFase'), entregaFaseInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: entregaFaseInstance.id)
            }
            json {
                response.status = 201
                render entregaFaseInstance as JSON
            }
            xml {
                response.status = 201
                render entregaFaseInstance.id
            }
        }
    }

    def show() {
        def entregaFaseInstance = EntregaFase.get(params.id)
        if (!entregaFaseInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'entregaFase.label', default: 'EntregaFase'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [entregaFaseInstance: entregaFaseInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def entregaFaseInstance = EntregaFase.get(params.id)
        if (!entregaFaseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'entregaFase.label', default: 'EntregaFase'), params.id])
            redirect(action: "list")
            return
        }
        [entregaFaseInstance: entregaFaseInstance]
    }

    def update() {
        def entregaFaseInstance = EntregaFase.get(params.id)
         if (!entregaFaseInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'entregaFase.label', default: 'EntregaFase'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (entregaFaseInstance.version > version) {
                entregaFaseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'entregaFase.label', default: 'EntregaFase')] as Object[],
                          "Another user has updated this EntregaFase while you were editing")
                withFormat {
                    html {render(view: "edit", model: [entregaFaseInstance: entregaFaseInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            entregaFaseInstance.properties = params
            

        if (!entregaFaseInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [entregaFaseInstance: entregaFaseInstance])}
                json {
                    response.status = 403
                    render entregaFaseInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render entregaFaseInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'entregaFase.label', default: 'EntregaFase'), entregaFaseInstance.id])
                redirect(action: "show", id: entregaFaseInstance.id)
            }
            json {
                response.status = 204
                render entregaFaseInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def entregaFaseInstance = EntregaFase.get(params.id)
        if (!entregaFaseInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'entregaFase.label', default: 'EntregaFase'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            entregaFaseInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'entregaFase.label', default: 'EntregaFase'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'entregaFase.label', default: 'EntregaFase'), params.id])
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
