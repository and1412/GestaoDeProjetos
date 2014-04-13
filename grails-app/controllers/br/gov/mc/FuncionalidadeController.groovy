package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class FuncionalidadeController {


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
        def list = Funcionalidade.list(params)
        def listObject = [funcionalidadeInstanceList: list, funcionalidadeInstanceTotal: Funcionalidade.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [funcionalidadeInstance: new Funcionalidade(params)]
    }

    def save() {
        def funcionalidadeInstance = new Funcionalidade(params)
        
        if (!funcionalidadeInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [funcionalidadeInstance: funcionalidadeInstance])}
                json {
                    response.status = 403
                    render funcionalidadeInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render funcionalidadeInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'funcionalidade.label', default: 'Funcionalidade'), funcionalidadeInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: funcionalidadeInstance.id)
            }
            json {
                response.status = 201
                render funcionalidadeInstance as JSON
            }
            xml {
                response.status = 201
                render funcionalidadeInstance.id
            }
        }
    }

    def show() {
        def funcionalidadeInstance = Funcionalidade.get(params.id)
        if (!funcionalidadeInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'funcionalidade.label', default: 'Funcionalidade'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [funcionalidadeInstance: funcionalidadeInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def funcionalidadeInstance = Funcionalidade.get(params.id)
        if (!funcionalidadeInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'funcionalidade.label', default: 'Funcionalidade'), params.id])
            redirect(action: "list")
            return
        }
        [funcionalidadeInstance: funcionalidadeInstance]
    }

    def update() {
        def funcionalidadeInstance = Funcionalidade.get(params.id)
         if (!funcionalidadeInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'funcionalidade.label', default: 'Funcionalidade'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (funcionalidadeInstance.version > version) {
                funcionalidadeInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'funcionalidade.label', default: 'Funcionalidade')] as Object[],
                          "Another user has updated this Funcionalidade while you were editing")
                withFormat {
                    html {render(view: "edit", model: [funcionalidadeInstance: funcionalidadeInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            funcionalidadeInstance.properties = params
            

        if (!funcionalidadeInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [funcionalidadeInstance: funcionalidadeInstance])}
                json {
                    response.status = 403
                    render funcionalidadeInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render funcionalidadeInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'funcionalidade.label', default: 'Funcionalidade'), funcionalidadeInstance.id])
                redirect(action: "show", id: funcionalidadeInstance.id)
            }
            json {
                response.status = 204
                render funcionalidadeInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def funcionalidadeInstance = Funcionalidade.get(params.id)
        if (!funcionalidadeInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'funcionalidade.label', default: 'Funcionalidade'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            funcionalidadeInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'funcionalidade.label', default: 'Funcionalidade'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'funcionalidade.label', default: 'Funcionalidade'), params.id])
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
