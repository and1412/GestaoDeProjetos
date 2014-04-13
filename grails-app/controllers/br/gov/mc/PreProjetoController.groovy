package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class PreProjetoController {


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
        def list = PreProjeto.list(params)
        def listObject = [preProjetoInstanceList: list, preProjetoInstanceTotal: PreProjeto.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [preProjetoInstance: new PreProjeto(params)]
    }

    def save() {
        def preProjetoInstance = new PreProjeto(params)
        
        if (!preProjetoInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [preProjetoInstance: preProjetoInstance])}
                json {
                    response.status = 403
                    render preProjetoInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render preProjetoInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'preProjeto.label', default: 'PreProjeto'), preProjetoInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: preProjetoInstance.id)
            }
            json {
                response.status = 201
                render preProjetoInstance as JSON
            }
            xml {
                response.status = 201
                render preProjetoInstance.id
            }
        }
    }

    def show() {
        def preProjetoInstance = PreProjeto.get(params.id)
        if (!preProjetoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'preProjeto.label', default: 'PreProjeto'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [preProjetoInstance: preProjetoInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def preProjetoInstance = PreProjeto.get(params.id)
        if (!preProjetoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'preProjeto.label', default: 'PreProjeto'), params.id])
            redirect(action: "list")
            return
        }
        [preProjetoInstance: preProjetoInstance]
    }

    def update() {
        def preProjetoInstance = PreProjeto.get(params.id)
         if (!preProjetoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'preProjeto.label', default: 'PreProjeto'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (preProjetoInstance.version > version) {
                preProjetoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'preProjeto.label', default: 'PreProjeto')] as Object[],
                          "Another user has updated this PreProjeto while you were editing")
                withFormat {
                    html {render(view: "edit", model: [preProjetoInstance: preProjetoInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            preProjetoInstance.properties = params
            

        if (!preProjetoInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [preProjetoInstance: preProjetoInstance])}
                json {
                    response.status = 403
                    render preProjetoInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render preProjetoInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'preProjeto.label', default: 'PreProjeto'), preProjetoInstance.id])
                redirect(action: "show", id: preProjetoInstance.id)
            }
            json {
                response.status = 204
                render preProjetoInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def preProjetoInstance = PreProjeto.get(params.id)
        if (!preProjetoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'preProjeto.label', default: 'PreProjeto'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            preProjetoInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'preProjeto.label', default: 'PreProjeto'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'preProjeto.label', default: 'PreProjeto'), params.id])
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
