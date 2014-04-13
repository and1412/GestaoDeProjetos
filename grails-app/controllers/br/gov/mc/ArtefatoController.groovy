package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class ArtefatoController {


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
        def list = Artefato.list(params)
        def listObject = [artefatoInstanceList: list, artefatoInstanceTotal: Artefato.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    

    def create() {
           [artefatoInstance: new Artefato(params)]
    }

    def save() {
        def artefatoInstance = new Artefato(params)
        
        if (!artefatoInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [artefatoInstance: artefatoInstance])}
                json {
                    response.status = 403
                    render artefatoInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render artefatoInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'artefato.label', default: 'Artefato'), artefatoInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: artefatoInstance.id)
            }
            json {
                response.status = 201
                render artefatoInstance as JSON
            }
            xml {
                response.status = 201
                render artefatoInstance.id
            }
        }
    }

    def show() {
        def artefatoInstance = Artefato.get(params.id)
        if (!artefatoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'artefato.label', default: 'Artefato'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [artefatoInstance: artefatoInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def artefatoInstance = Artefato.get(params.id)
        if (!artefatoInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'artefato.label', default: 'Artefato'), params.id])
            redirect(action: "list")
            return
        }
        [artefatoInstance: artefatoInstance]
    }

    def update() {
        def artefatoInstance = Artefato.get(params.id)
         if (!artefatoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'artefato.label', default: 'Artefato'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (artefatoInstance.version > version) {
                artefatoInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'artefato.label', default: 'Artefato')] as Object[],
                          "Another user has updated this Artefato while you were editing")
                withFormat {
                    html {render(view: "edit", model: [artefatoInstance: artefatoInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
            artefatoInstance.properties = params
            

        if (!artefatoInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [artefatoInstance: artefatoInstance])}
                json {
                    response.status = 403
                    render artefatoInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render artefatoInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'artefato.label', default: 'Artefato'), artefatoInstance.id])
                redirect(action: "show", id: artefatoInstance.id)
            }
            json {
                response.status = 204
                render artefatoInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def artefatoInstance = Artefato.get(params.id)
        if (!artefatoInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'artefato.label', default: 'Artefato'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            artefatoInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'artefato.label', default: 'Artefato'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'artefato.label', default: 'Artefato'), params.id])
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
