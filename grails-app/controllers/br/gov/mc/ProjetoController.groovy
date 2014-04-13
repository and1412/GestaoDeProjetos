package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class ProjetoController {


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
        def list = Projeto.list(params)
        def listObject = [projetoInstanceList: list, projetoInstanceTotal: Projeto.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [projetoInstance: new Projeto(params)]
    }

    def save() {
        def projetoInstance = new Projeto(params)
        
        if (!projetoInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [projetoInstance: projetoInstance])}
                json {
                    response.status = 403
                    render projetoInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render projetoInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'projeto.label', default: 'Projeto'), projetoInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: projetoInstance.id)
            }
            json {
                response.status = 201
                render projetoInstance as JSON
            }
            xml {
                response.status = 201
                render projetoInstance.id
            }
        }
    }

    def show() {
        def projetoInstance = Projeto.get(params.id)
        if (!projetoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'projeto.label', default: 'Projeto'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [projetoInstance: projetoInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def projetoInstance = Projeto.get(params.id)
        if (!projetoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'projeto.label', default: 'Projeto'), params.id])
            redirect(action: "list")
            return
        }
        [projetoInstance: projetoInstance]
    }

    def update() {
        def projetoInstance = Projeto.get(params.id)
         if (!projetoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'projeto.label', default: 'Projeto'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (projetoInstance.version > version) {
                projetoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'projeto.label', default: 'Projeto')] as Object[],
                          "Another user has updated this Projeto while you were editing")
                withFormat {
                    html {render(view: "edit", model: [projetoInstance: projetoInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            projetoInstance.properties = params
            

        if (!projetoInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [projetoInstance: projetoInstance])}
                json {
                    response.status = 403
                    render projetoInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render projetoInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'projeto.label', default: 'Projeto'), projetoInstance.id])
                redirect(action: "show", id: projetoInstance.id)
            }
            json {
                response.status = 204
                render projetoInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def projetoInstance = Projeto.get(params.id)
        if (!projetoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'projeto.label', default: 'Projeto'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            projetoInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'projeto.label', default: 'Projeto'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'projeto.label', default: 'Projeto'), params.id])
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
