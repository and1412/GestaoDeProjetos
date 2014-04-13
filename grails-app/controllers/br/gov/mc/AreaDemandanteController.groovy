package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class AreaDemandanteController {


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
        def list = AreaDemandante.list(params)
        def listObject = [areaDemandanteInstanceList: list, areaDemandanteInstanceTotal: AreaDemandante.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [areaDemandanteInstance: new AreaDemandante(params)]
    }

    def save() {
        def areaDemandanteInstance = new AreaDemandante(params)
        
        if (!areaDemandanteInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [areaDemandanteInstance: areaDemandanteInstance])}
                json {
                    response.status = 403
                    render areaDemandanteInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render areaDemandanteInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'areaDemandante.label', default: 'AreaDemandante'), areaDemandanteInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: areaDemandanteInstance.id)
            }
            json {
                response.status = 201
                render areaDemandanteInstance as JSON
            }
            xml {
                response.status = 201
                render areaDemandanteInstance.id
            }
        }
    }

    def show() {
        def areaDemandanteInstance = AreaDemandante.get(params.id)
        if (!areaDemandanteInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'areaDemandante.label', default: 'AreaDemandante'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [areaDemandanteInstance: areaDemandanteInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def areaDemandanteInstance = AreaDemandante.get(params.id)
        if (!areaDemandanteInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'areaDemandante.label', default: 'AreaDemandante'), params.id])
            redirect(action: "list")
            return
        }
        [areaDemandanteInstance: areaDemandanteInstance]
    }

    def update() {
        def areaDemandanteInstance = AreaDemandante.get(params.id)
         if (!areaDemandanteInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'areaDemandante.label', default: 'AreaDemandante'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (areaDemandanteInstance.version > version) {
                areaDemandanteInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'areaDemandante.label', default: 'AreaDemandante')] as Object[],
                          "Another user has updated this AreaDemandante while you were editing")
                withFormat {
                    html {render(view: "edit", model: [areaDemandanteInstance: areaDemandanteInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            areaDemandanteInstance.properties = params
            

        if (!areaDemandanteInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [areaDemandanteInstance: areaDemandanteInstance])}
                json {
                    response.status = 403
                    render areaDemandanteInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render areaDemandanteInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'areaDemandante.label', default: 'AreaDemandante'), areaDemandanteInstance.id])
                redirect(action: "show", id: areaDemandanteInstance.id)
            }
            json {
                response.status = 204
                render areaDemandanteInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def areaDemandanteInstance = AreaDemandante.get(params.id)
        if (!areaDemandanteInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'areaDemandante.label', default: 'AreaDemandante'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            areaDemandanteInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'areaDemandante.label', default: 'AreaDemandante'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'areaDemandante.label', default: 'AreaDemandante'), params.id])
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
