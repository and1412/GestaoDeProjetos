package org.codehaus.groovy.grails.plugins.orm.auditable

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class AuditLogEventController {


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
        def list = AuditLogEvent.list(params)
        def listObject = [auditLogEventInstanceList: list, auditLogEventInstanceTotal: AuditLogEvent.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [auditLogEventInstance: new AuditLogEvent(params)]
    }

    def save() {
        def auditLogEventInstance = new AuditLogEvent(params)
        
        if (!auditLogEventInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [auditLogEventInstance: auditLogEventInstance])}
                json {
                    response.status = 403
                    render auditLogEventInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render auditLogEventInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'auditLogEvent.label', default: 'AuditLogEvent'), auditLogEventInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: auditLogEventInstance.id)
            }
            json {
                response.status = 201
                render auditLogEventInstance as JSON
            }
            xml {
                response.status = 201
                render auditLogEventInstance.id
            }
        }
    }

    def show() {
        def auditLogEventInstance = AuditLogEvent.get(params.id)
        if (!auditLogEventInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditLogEvent.label', default: 'AuditLogEvent'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [auditLogEventInstance: auditLogEventInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def auditLogEventInstance = AuditLogEvent.get(params.id)
        if (!auditLogEventInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditLogEvent.label', default: 'AuditLogEvent'), params.id])
            redirect(action: "list")
            return
        }
        [auditLogEventInstance: auditLogEventInstance]
    }

    def update() {
        def auditLogEventInstance = AuditLogEvent.get(params.id)
         if (!auditLogEventInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditLogEvent.label', default: 'AuditLogEvent'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (auditLogEventInstance.version > version) {
                auditLogEventInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'auditLogEvent.label', default: 'AuditLogEvent')] as Object[],
                          "Another user has updated this AuditLogEvent while you were editing")
                withFormat {
                    html {render(view: "edit", model: [auditLogEventInstance: auditLogEventInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            auditLogEventInstance.properties = params
            

        if (!auditLogEventInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [auditLogEventInstance: auditLogEventInstance])}
                json {
                    response.status = 403
                    render auditLogEventInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render auditLogEventInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'auditLogEvent.label', default: 'AuditLogEvent'), auditLogEventInstance.id])
                redirect(action: "show", id: auditLogEventInstance.id)
            }
            json {
                response.status = 204
                render auditLogEventInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def auditLogEventInstance = AuditLogEvent.get(params.id)
        if (!auditLogEventInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditLogEvent.label', default: 'AuditLogEvent'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            auditLogEventInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'auditLogEvent.label', default: 'AuditLogEvent'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'auditLogEvent.label', default: 'AuditLogEvent'), params.id])
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
