package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class EscopoController {


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
        def list = Escopo.list(params)
        def listObject = [escopoInstanceList: list, escopoInstanceTotal: Escopo.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [escopoInstance: new Escopo(params)]
    }

    def save() {
        def escopoInstance = new Escopo(params)
        
        if (!escopoInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [escopoInstance: escopoInstance])}
                json {
                    response.status = 403
                    render escopoInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render escopoInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'escopo.label', default: 'Escopo'), escopoInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: escopoInstance.id)
            }
            json {
                response.status = 201
                render escopoInstance as JSON
            }
            xml {
                response.status = 201
                render escopoInstance.id
            }
        }
    }

    def show() {
        def escopoInstance = Escopo.get(params.id)
        if (!escopoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'escopo.label', default: 'Escopo'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [escopoInstance: escopoInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def escopoInstance = Escopo.get(params.id)
        if (!escopoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'escopo.label', default: 'Escopo'), params.id])
            redirect(action: "list")
            return
        }
        [escopoInstance: escopoInstance]
    }

    def update() {
        def escopoInstance = Escopo.get(params.id)
         if (!escopoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'escopo.label', default: 'Escopo'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (escopoInstance.version > version) {
                escopoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'escopo.label', default: 'Escopo')] as Object[],
                          "Another user has updated this Escopo while you were editing")
                withFormat {
                    html {render(view: "edit", model: [escopoInstance: escopoInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            escopoInstance.properties = params
            

        if (!escopoInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [escopoInstance: escopoInstance])}
                json {
                    response.status = 403
                    render escopoInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render escopoInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'escopo.label', default: 'Escopo'), escopoInstance.id])
                redirect(action: "show", id: escopoInstance.id)
            }
            json {
                response.status = 204
                render escopoInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def escopoInstance = Escopo.get(params.id)
        if (!escopoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'escopo.label', default: 'Escopo'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            escopoInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'escopo.label', default: 'Escopo'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'escopo.label', default: 'Escopo'), params.id])
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
