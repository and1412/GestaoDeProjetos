package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(ArquiteturaController)
@Mock(Arquitetura)
class ArquiteturaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/arquitetura/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.arquiteturaInstanceList.size() == 0
        assert model.arquiteturaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.arquiteturaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.arquiteturaInstance != null
        assert view == '/arquitetura/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/arquitetura/show/1'
        assert controller.flash.message != null
        assert Arquitetura.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/arquitetura/list'

        populateValidParams(params)
        def arquitetura = new Arquitetura(params)

        assert arquitetura.save() != null

        params.id = arquitetura.id

        def model = controller.show()

        assert model.arquiteturaInstance == arquitetura
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/arquitetura/list'

        populateValidParams(params)
        def arquitetura = new Arquitetura(params)

        assert arquitetura.save() != null

        params.id = arquitetura.id

        def model = controller.edit()

        assert model.arquiteturaInstance == arquitetura
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/arquitetura/list'

        response.reset()

        populateValidParams(params)
        def arquitetura = new Arquitetura(params)

        assert arquitetura.save() != null

        // test invalid parameters in update
        params.id = arquitetura.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/arquitetura/edit"
        assert model.arquiteturaInstance != null

        arquitetura.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/arquitetura/show/$arquitetura.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        arquitetura.clearErrors()

        populateValidParams(params)
        params.id = arquitetura.id
        params.version = -1
        controller.update()

        assert view == "/arquitetura/edit"
        assert model.arquiteturaInstance != null
        assert model.arquiteturaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/arquitetura/list'

        response.reset()

        populateValidParams(params)
        def arquitetura = new Arquitetura(params)

        assert arquitetura.save() != null
        assert Arquitetura.count() == 1

        params.id = arquitetura.id

        controller.delete()

        assert Arquitetura.count() == 0
        assert Arquitetura.get(arquitetura.id) == null
        assert response.redirectedUrl == '/arquitetura/list'
    }
}
