package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(EntregaFaseController)
@Mock(EntregaFase)
class EntregaFaseControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/entregaFase/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.entregaFaseInstanceList.size() == 0
        assert model.entregaFaseInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.entregaFaseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.entregaFaseInstance != null
        assert view == '/entregaFase/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/entregaFase/show/1'
        assert controller.flash.message != null
        assert EntregaFase.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/entregaFase/list'

        populateValidParams(params)
        def entregaFase = new EntregaFase(params)

        assert entregaFase.save() != null

        params.id = entregaFase.id

        def model = controller.show()

        assert model.entregaFaseInstance == entregaFase
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/entregaFase/list'

        populateValidParams(params)
        def entregaFase = new EntregaFase(params)

        assert entregaFase.save() != null

        params.id = entregaFase.id

        def model = controller.edit()

        assert model.entregaFaseInstance == entregaFase
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/entregaFase/list'

        response.reset()

        populateValidParams(params)
        def entregaFase = new EntregaFase(params)

        assert entregaFase.save() != null

        // test invalid parameters in update
        params.id = entregaFase.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/entregaFase/edit"
        assert model.entregaFaseInstance != null

        entregaFase.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/entregaFase/show/$entregaFase.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        entregaFase.clearErrors()

        populateValidParams(params)
        params.id = entregaFase.id
        params.version = -1
        controller.update()

        assert view == "/entregaFase/edit"
        assert model.entregaFaseInstance != null
        assert model.entregaFaseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/entregaFase/list'

        response.reset()

        populateValidParams(params)
        def entregaFase = new EntregaFase(params)

        assert entregaFase.save() != null
        assert EntregaFase.count() == 1

        params.id = entregaFase.id

        controller.delete()

        assert EntregaFase.count() == 0
        assert EntregaFase.get(entregaFase.id) == null
        assert response.redirectedUrl == '/entregaFase/list'
    }
}
