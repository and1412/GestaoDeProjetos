package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class FaseController {


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
        def list = Fase.list(params)
        def listObject = [faseInstanceList: list, faseInstanceTotal: Fase.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [faseInstance: new Fase(params)]
    }

    def save() {
        def faseInstance = new Fase(params)
        
        if (!faseInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [faseInstance: faseInstance])}
                json {
                    response.status = 403
                    render faseInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render faseInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'fase.label', default: 'Fase'), faseInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: faseInstance.id)
            }
            json {
                response.status = 201
                render faseInstance as JSON
            }
            xml {
                response.status = 201
                render faseInstance.id
            }
        }
    }

    def show() {
        def faseInstance = Fase.get(params.id)
        if (!faseInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'fase.label', default: 'Fase'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [faseInstance: faseInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def faseInstance = Fase.get(params.id)
        if (!faseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fase.label', default: 'Fase'), params.id])
            redirect(action: "list")
            return
        }
        [faseInstance: faseInstance]
    }

    def update() {
        def faseInstance = Fase.get(params.id)
         if (!faseInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'fase.label', default: 'Fase'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (faseInstance.version > version) {
                faseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'fase.label', default: 'Fase')] as Object[],
                          "Another user has updated this Fase while you were editing")
                withFormat {
                    html {render(view: "edit", model: [faseInstance: faseInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            faseInstance.properties = params
            

        if (!faseInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [faseInstance: faseInstance])}
                json {
                    response.status = 403
                    render faseInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render faseInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'fase.label', default: 'Fase'), faseInstance.id])
                redirect(action: "show", id: faseInstance.id)
            }
            json {
                response.status = 204
                render faseInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def faseInstance = Fase.get(params.id)
        if (!faseInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'fase.label', default: 'Fase'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            faseInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'fase.label', default: 'Fase'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'fase.label', default: 'Fase'), params.id])
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
