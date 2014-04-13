package br.gov.mc

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.XML
import grails.converters.JSON


class PreProjetoOcorrenciaController {


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
        def list = PreProjetoOcorrencia.list(params)
        def listObject = [preProjetoOcorrenciaInstanceList: list, preProjetoOcorrenciaInstanceTotal: PreProjetoOcorrencia.count()]
        withFormat {
            html listObject
            json { render list as JSON }
            xml { render listObject as XML }
        }
    }

    
    def downloaddocumentoRelacionado(Long id){
        def preProjetoOcorrenciaInstance = PreProjetoOcorrencia.get(id)
        if (preProjetoOcorrenciaInstance){
            response.setContentType("application/octet-stream")
            response.setHeader("Content-disposition", "filename=${preProjetoOcorrenciaInstance.nome_arquivo}")
            response.outputStream << preProjetoOcorrenciaInstance.documentoRelacionado 
            return
        }
    }
    

    def create() {
           [preProjetoOcorrenciaInstance: new PreProjetoOcorrencia(params)]
    }

    def save() {
        def preProjetoOcorrenciaInstance = new PreProjetoOcorrencia(params)
        
        def uploadedFile = request.getFile('documentoRelacionado')
        //Setando nome e tipo do documento
        preProjetoOcorrenciaInstance.nome_arquivo = uploadedFile.originalFilename
        preProjetoOcorrenciaInstance.tipo_arquivo = uploadedFile.contentType
        
        if (!preProjetoOcorrenciaInstance.save(flush: true)) {
            withFormat {
                html {render(view: "create", model: [preProjetoOcorrenciaInstance: preProjetoOcorrenciaInstance])}
                json {
                    response.status = 403
                    render preProjetoOcorrenciaInstance.errors as JSON
                }
                xml {
                    response.status =403
                    render preProjetoOcorrenciaInstance.errors as XML
                }
            }
            return
        }
        flash.message = message(code: 'default.created.message', args: [message(code: 'preProjetoOcorrencia.label', default: 'PreProjetoOcorrencia'), preProjetoOcorrenciaInstance.id])
        withFormat {
            html {
                redirect(action: "show", id: preProjetoOcorrenciaInstance.id)
            }
            json {
                response.status = 201
                render preProjetoOcorrenciaInstance as JSON
            }
            xml {
                response.status = 201
                render preProjetoOcorrenciaInstance.id
            }
        }
    }

    def show() {
        def preProjetoOcorrenciaInstance = PreProjetoOcorrencia.get(params.id)
        if (!preProjetoOcorrenciaInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'preProjetoOcorrencia.label', default: 'PreProjetoOcorrencia'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        def object = [preProjetoOcorrenciaInstance: preProjetoOcorrenciaInstance]
        withFormat {
            html {object}
            json { render object as JSON }
            xml { render object as XML }
        }
    }

    def edit() {
        def preProjetoOcorrenciaInstance = PreProjetoOcorrencia.get(params.id)
        if (!preProjetoOcorrenciaInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'preProjetoOcorrencia.label', default: 'PreProjetoOcorrencia'), params.id])
            redirect(action: "list")
            return
        }
        [preProjetoOcorrenciaInstance: preProjetoOcorrenciaInstance]
    }

    def update() {
        def preProjetoOcorrenciaInstance = PreProjetoOcorrencia.get(params.id)
         if (!preProjetoOcorrenciaInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'preProjetoOcorrencia.label', default: 'PreProjetoOcorrencia'), params.id])
                    redirect(action:"list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (preProjetoOcorrenciaInstance.version > version) {
                preProjetoOcorrenciaInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'preProjetoOcorrencia.label', default: 'PreProjetoOcorrencia')] as Object[],
                          "Another user has updated this PreProjetoOcorrencia while you were editing")
                withFormat {
                    html {render(view: "edit", model: [preProjetoOcorrenciaInstance: preProjetoOcorrenciaInstance])}
                    json { response.sendError(409) }
                    xml { response.sendError(409) }
                }
                return
            }
        }

        
        def uploadedFile = request.getFile('documentoRelacionado')        

        if (uploadedFile.empty){
            def filename = preProjetoOcorrenciaInstance.nome_arquivo
            def filetype = preProjetoOcorrenciaInstance.tipo_arquivo 
            def bytes = preProjetoOcorrenciaInstance.documentoRelacionado

            preProjetoOcorrenciaInstance.properties = params

            preProjetoOcorrenciaInstance.nome_arquivo = filename
            preProjetoOcorrenciaInstance.tipo_arquivo = filetype 
            preProjetoOcorrenciaInstance.documentoRelacionado = bytes

        }
        else{
            
            preProjetoOcorrenciaInstance.properties = params
            //Setando nome e tipo do documento
            preProjetoOcorrenciaInstance.nome_arquivo = uploadedFile.originalFilename
            preProjetoOcorrenciaInstance.tipo_arquivo = uploadedFile.contentType
        }
        

        if (!preProjetoOcorrenciaInstance.save(flush: true)) {
            withFormat {
                html {render(view: "edit", model: [preProjetoOcorrenciaInstance: preProjetoOcorrenciaInstance])}
                json {
                    response.status = 403
                    render preProjetoOcorrenciaInstance.errors as JSON
                }
                xml {
                    response.status = 403
                    render preProjetoOcorrenciaInstance.errors as XML
                }
            }
            return
        }
        withFormat {
            html {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'preProjetoOcorrencia.label', default: 'PreProjetoOcorrencia'), preProjetoOcorrenciaInstance.id])
                redirect(action: "show", id: preProjetoOcorrenciaInstance.id)
            }
            json {
                response.status = 204
                render preProjetoOcorrenciaInstance as JSON
            }
            xml {
                response.status = 204
                render ''
            }
        }
    }

    def delete() {
        def preProjetoOcorrenciaInstance = PreProjetoOcorrencia.get(params.id)
        if (!preProjetoOcorrenciaInstance) {
            withFormat {
                html {
                    flash.message = message(code: 'default.not.found.message', args: [message(code: 'preProjetoOcorrencia.label', default: 'PreProjetoOcorrencia'), params.id])
                    redirect(action: "list")
                }
                json { response.sendError(404) }
                xml { response.sendError(404) }
            }
            return
        }
        try {
            preProjetoOcorrenciaInstance.delete(flush: true)
            withFormat {
                html {
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'preProjetoOcorrencia.label', default: 'PreProjetoOcorrencia'), params.id])
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
                    flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'preProjetoOcorrencia.label', default: 'PreProjetoOcorrencia'), params.id])
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
