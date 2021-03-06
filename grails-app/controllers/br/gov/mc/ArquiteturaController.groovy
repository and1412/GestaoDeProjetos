package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class ArquiteturaController {


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
        def list = Arquitetura.list(params)
        def listObject = [arquiteturaInstanceList: list, arquiteturaInstanceTotal: Arquitetura.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [arquiteturaInstance: new Arquitetura(params)]
    }

    def save() {
        def arquiteturaInstance = new Arquitetura(params)
        
        if (!arquiteturaInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [arquiteturaInstance: arquiteturaInstance])}
                json {
                    response.status = 403
                    render arquiteturaInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render arquiteturaInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'arquitetura.label', default: 'Arquitetura'), arquiteturaInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: arquiteturaInstance.id)
            }
            json {
                response.status = 201
                render arquiteturaInstance as JSON
            }
            xml {
                response.status = 201
                render arquiteturaInstance.id
            }
        }
    }

    def show() {
        def arquiteturaInstance = Arquitetura.get(params.id)
        if (!arquiteturaInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'arquitetura.label', default: 'Arquitetura'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [arquiteturaInstance: arquiteturaInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def arquiteturaInstance = Arquitetura.get(params.id)
        if (!arquiteturaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'arquitetura.label', default: 'Arquitetura'), params.id])
            redirect(action: "list")
            return
        }
        [arquiteturaInstance: arquiteturaInstance]
    }

    def update() {
        def arquiteturaInstance = Arquitetura.get(params.id)
         if (!arquiteturaInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'arquitetura.label', default: 'Arquitetura'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (arquiteturaInstance.version > version) {
                arquiteturaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'arquitetura.label', default: 'Arquitetura')] as Object[],
                          "Another user has updated this Arquitetura while you were editing")
                withFormat {
                    html {render(view: "edit", model: [arquiteturaInstance: arquiteturaInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            arquiteturaInstance.properties = params
            

        if (!arquiteturaInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [arquiteturaInstance: arquiteturaInstance])}
                json {
                    response.status = 403
                    render arquiteturaInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render arquiteturaInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'arquitetura.label', default: 'Arquitetura'), arquiteturaInstance.id])
                redirect(action: "show", id: arquiteturaInstance.id)
            }
            json {
                response.status = 204
                render arquiteturaInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def arquiteturaInstance = Arquitetura.get(params.id)
        if (!arquiteturaInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'arquitetura.label', default: 'Arquitetura'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            arquiteturaInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'arquitetura.label', default: 'Arquitetura'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'arquitetura.label', default: 'Arquitetura'), params.id])
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
