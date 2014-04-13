package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class FabricaSoftwareController {


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
        def list = FabricaSoftware.list(params)
        def listObject = [fabricaSoftwareInstanceList: list, fabricaSoftwareInstanceTotal: FabricaSoftware.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [fabricaSoftwareInstance: new FabricaSoftware(params)]
    }

    def save() {
        def fabricaSoftwareInstance = new FabricaSoftware(params)
        
        if (!fabricaSoftwareInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [fabricaSoftwareInstance: fabricaSoftwareInstance])}
                json {
                    response.status = 403
                    render fabricaSoftwareInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render fabricaSoftwareInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'fabricaSoftware.label', default: 'FabricaSoftware'), fabricaSoftwareInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: fabricaSoftwareInstance.id)
            }
            json {
                response.status = 201
                render fabricaSoftwareInstance as JSON
            }
            xml {
                response.status = 201
                render fabricaSoftwareInstance.id
            }
        }
    }

    def show() {
        def fabricaSoftwareInstance = FabricaSoftware.get(params.id)
        if (!fabricaSoftwareInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'fabricaSoftware.label', default: 'FabricaSoftware'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [fabricaSoftwareInstance: fabricaSoftwareInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def fabricaSoftwareInstance = FabricaSoftware.get(params.id)
        if (!fabricaSoftwareInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'fabricaSoftware.label', default: 'FabricaSoftware'), params.id])
            redirect(action: "list")
            return
        }
        [fabricaSoftwareInstance: fabricaSoftwareInstance]
    }

    def update() {
        def fabricaSoftwareInstance = FabricaSoftware.get(params.id)
         if (!fabricaSoftwareInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'fabricaSoftware.label', default: 'FabricaSoftware'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (fabricaSoftwareInstance.version > version) {
                fabricaSoftwareInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'fabricaSoftware.label', default: 'FabricaSoftware')] as Object[],
                          "Another user has updated this FabricaSoftware while you were editing")
                withFormat {
                    html {render(view: "edit", model: [fabricaSoftwareInstance: fabricaSoftwareInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            fabricaSoftwareInstance.properties = params
            

        if (!fabricaSoftwareInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [fabricaSoftwareInstance: fabricaSoftwareInstance])}
                json {
                    response.status = 403
                    render fabricaSoftwareInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render fabricaSoftwareInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'fabricaSoftware.label', default: 'FabricaSoftware'), fabricaSoftwareInstance.id])
                redirect(action: "show", id: fabricaSoftwareInstance.id)
            }
            json {
                response.status = 204
                render fabricaSoftwareInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def fabricaSoftwareInstance = FabricaSoftware.get(params.id)
        if (!fabricaSoftwareInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'fabricaSoftware.label', default: 'FabricaSoftware'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            fabricaSoftwareInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'fabricaSoftware.label', default: 'FabricaSoftware'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'fabricaSoftware.label', default: 'FabricaSoftware'), params.id])
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
