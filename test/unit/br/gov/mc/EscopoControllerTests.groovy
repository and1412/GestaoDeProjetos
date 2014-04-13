package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(EscopoController)
@Mock(Escopo)
class EscopoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/escopo/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.escopoInstanceList.size() == 0
        assert model.escopoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.escopoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.escopoInstance != null
        assert view == '/escopo/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/escopo/show/1'
        assert controller.flash.message != null
        assert Escopo.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/escopo/list'

        populateValidParams(params)
        def escopo = new Escopo(params)

        assert escopo.save() != null

        params.id = escopo.id

        def model = controller.show()

        assert model.escopoInstance == escopo
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/escopo/list'

        populateValidParams(params)
        def escopo = new Escopo(params)

        assert escopo.save() != null

        params.id = escopo.id

        def model = controller.edit()

        assert model.escopoInstance == escopo
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/escopo/list'

        response.reset()

        populateValidParams(params)
        def escopo = new Escopo(params)

        assert escopo.save() != null

        // test invalid parameters in update
        params.id = escopo.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/escopo/edit"
        assert model.escopoInstance != null

        escopo.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/escopo/show/$escopo.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        escopo.clearErrors()

        populateValidParams(params)
        params.id = escopo.id
        params.version = -1
        controller.update()

        assert view == "/escopo/edit"
        assert model.escopoInstance != null
        assert model.escopoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/escopo/list'

        response.reset()

        populateValidParams(params)
        def escopo = new Escopo(params)

        assert escopo.save() != null
        assert Escopo.count() == 1

        params.id = escopo.id

        controller.delete()

        assert Escopo.count() == 0
        assert Escopo.get(escopo.id) == null
        assert response.redirectedUrl == '/escopo/list'
    }
}
