package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(PreProjetoController)
@Mock(PreProjeto)
class PreProjetoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/preProjeto/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.preProjetoInstanceList.size() == 0
        assert model.preProjetoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.preProjetoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.preProjetoInstance != null
        assert view == '/preProjeto/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/preProjeto/show/1'
        assert controller.flash.message != null
        assert PreProjeto.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/preProjeto/list'

        populateValidParams(params)
        def preProjeto = new PreProjeto(params)

        assert preProjeto.save() != null

        params.id = preProjeto.id

        def model = controller.show()

        assert model.preProjetoInstance == preProjeto
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/preProjeto/list'

        populateValidParams(params)
        def preProjeto = new PreProjeto(params)

        assert preProjeto.save() != null

        params.id = preProjeto.id

        def model = controller.edit()

        assert model.preProjetoInstance == preProjeto
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/preProjeto/list'

        response.reset()

        populateValidParams(params)
        def preProjeto = new PreProjeto(params)

        assert preProjeto.save() != null

        // test invalid parameters in update
        params.id = preProjeto.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/preProjeto/edit"
        assert model.preProjetoInstance != null

        preProjeto.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/preProjeto/show/$preProjeto.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        preProjeto.clearErrors()

        populateValidParams(params)
        params.id = preProjeto.id
        params.version = -1
        controller.update()

        assert view == "/preProjeto/edit"
        assert model.preProjetoInstance != null
        assert model.preProjetoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/preProjeto/list'

        response.reset()

        populateValidParams(params)
        def preProjeto = new PreProjeto(params)

        assert preProjeto.save() != null
        assert PreProjeto.count() == 1

        params.id = preProjeto.id

        controller.delete()

        assert PreProjeto.count() == 0
        assert PreProjeto.get(preProjeto.id) == null
        assert response.redirectedUrl == '/preProjeto/list'
    }
}
