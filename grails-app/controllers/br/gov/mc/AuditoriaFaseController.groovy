package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class AuditoriaFaseController {


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
        def list = AuditoriaFase.list(params)
        def listObject = [auditoriaFaseInstanceList: list, auditoriaFaseInstanceTotal: AuditoriaFase.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [auditoriaFaseInstance: new AuditoriaFase(params)]
    }

    def save() {
        def auditoriaFaseInstance = new AuditoriaFase(params)
        
        if (!auditoriaFaseInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [auditoriaFaseInstance: auditoriaFaseInstance])}
                json {
                    response.status = 403
                    render auditoriaFaseInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render auditoriaFaseInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'auditoriaFase.label', default: 'AuditoriaFase'), auditoriaFaseInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: auditoriaFaseInstance.id)
            }
            json {
                response.status = 201
                render auditoriaFaseInstance as JSON
            }
            xml {
                response.status = 201
                render auditoriaFaseInstance.id
            }
        }
    }

    def show() {
        def auditoriaFaseInstance = AuditoriaFase.get(params.id)
        if (!auditoriaFaseInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditoriaFase.label', default: 'AuditoriaFase'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [auditoriaFaseInstance: auditoriaFaseInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def auditoriaFaseInstance = AuditoriaFase.get(params.id)
        if (!auditoriaFaseInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditoriaFase.label', default: 'AuditoriaFase'), params.id])
            redirect(action: "list")
            return
        }
        [auditoriaFaseInstance: auditoriaFaseInstance]
    }

    def update() {
        def auditoriaFaseInstance = AuditoriaFase.get(params.id)
         if (!auditoriaFaseInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditoriaFase.label', default: 'AuditoriaFase'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (auditoriaFaseInstance.version > version) {
                auditoriaFaseInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'auditoriaFase.label', default: 'AuditoriaFase')] as Object[],
                          "Another user has updated this AuditoriaFase while you were editing")
                withFormat {
                    html {render(view: "edit", model: [auditoriaFaseInstance: auditoriaFaseInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            auditoriaFaseInstance.properties = params
            

        if (!auditoriaFaseInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [auditoriaFaseInstance: auditoriaFaseInstance])}
                json {
                    response.status = 403
                    render auditoriaFaseInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render auditoriaFaseInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'auditoriaFase.label', default: 'AuditoriaFase'), auditoriaFaseInstance.id])
                redirect(action: "show", id: auditoriaFaseInstance.id)
            }
            json {
                response.status = 204
                render auditoriaFaseInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def auditoriaFaseInstance = AuditoriaFase.get(params.id)
        if (!auditoriaFaseInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditoriaFase.label', default: 'AuditoriaFase'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            auditoriaFaseInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'auditoriaFase.label', default: 'AuditoriaFase'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'auditoriaFase.label', default: 'AuditoriaFase'), params.id])
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
