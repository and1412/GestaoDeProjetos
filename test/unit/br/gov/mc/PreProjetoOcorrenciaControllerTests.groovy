package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(PreProjetoOcorrenciaController)
@Mock(PreProjetoOcorrencia)
class PreProjetoOcorrenciaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/preProjetoOcorrencia/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.preProjetoOcorrenciaInstanceList.size() == 0
        assert model.preProjetoOcorrenciaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.preProjetoOcorrenciaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.preProjetoOcorrenciaInstance != null
        assert view == '/preProjetoOcorrencia/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/preProjetoOcorrencia/show/1'
        assert controller.flash.message != null
        assert PreProjetoOcorrencia.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/preProjetoOcorrencia/list'

        populateValidParams(params)
        def preProjetoOcorrencia = new PreProjetoOcorrencia(params)

        assert preProjetoOcorrencia.save() != null

        params.id = preProjetoOcorrencia.id

        def model = controller.show()

        assert model.preProjetoOcorrenciaInstance == preProjetoOcorrencia
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/preProjetoOcorrencia/list'

        populateValidParams(params)
        def preProjetoOcorrencia = new PreProjetoOcorrencia(params)

        assert preProjetoOcorrencia.save() != null

        params.id = preProjetoOcorrencia.id

        def model = controller.edit()

        assert model.preProjetoOcorrenciaInstance == preProjetoOcorrencia
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/preProjetoOcorrencia/list'

        response.reset()

        populateValidParams(params)
        def preProjetoOcorrencia = new PreProjetoOcorrencia(params)

        assert preProjetoOcorrencia.save() != null

        // test invalid parameters in update
        params.id = preProjetoOcorrencia.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/preProjetoOcorrencia/edit"
        assert model.preProjetoOcorrenciaInstance != null

        preProjetoOcorrencia.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/preProjetoOcorrencia/show/$preProjetoOcorrencia.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        preProjetoOcorrencia.clearErrors()

        populateValidParams(params)
        params.id = preProjetoOcorrencia.id
        params.version = -1
        controller.update()

        assert view == "/preProjetoOcorrencia/edit"
        assert model.preProjetoOcorrenciaInstance != null
        assert model.preProjetoOcorrenciaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/preProjetoOcorrencia/list'

        response.reset()

        populateValidParams(params)
        def preProjetoOcorrencia = new PreProjetoOcorrencia(params)

        assert preProjetoOcorrencia.save() != null
        assert PreProjetoOcorrencia.count() == 1

        params.id = preProjetoOcorrencia.id

        controller.delete()

        assert PreProjetoOcorrencia.count() == 0
        assert PreProjetoOcorrencia.get(preProjetoOcorrencia.id) == null
        assert response.redirectedUrl == '/preProjetoOcorrencia/list'
    }
}
