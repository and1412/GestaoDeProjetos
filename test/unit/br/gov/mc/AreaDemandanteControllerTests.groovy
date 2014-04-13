package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(AreaDemandanteController)
@Mock(AreaDemandante)
class AreaDemandanteControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/areaDemandante/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.areaDemandanteInstanceList.size() == 0
        assert model.areaDemandanteInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.areaDemandanteInstance != null
    }

    void testSave() {
        controller.save()

        assert model.areaDemandanteInstance != null
        assert view == '/areaDemandante/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/areaDemandante/show/1'
        assert controller.flash.message != null
        assert AreaDemandante.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/areaDemandante/list'

        populateValidParams(params)
        def areaDemandante = new AreaDemandante(params)

        assert areaDemandante.save() != null

        params.id = areaDemandante.id

        def model = controller.show()

        assert model.areaDemandanteInstance == areaDemandante
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/areaDemandante/list'

        populateValidParams(params)
        def areaDemandante = new AreaDemandante(params)

        assert areaDemandante.save() != null

        params.id = areaDemandante.id

        def model = controller.edit()

        assert model.areaDemandanteInstance == areaDemandante
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/areaDemandante/list'

        response.reset()

        populateValidParams(params)
        def areaDemandante = new AreaDemandante(params)

        assert areaDemandante.save() != null

        // test invalid parameters in update
        params.id = areaDemandante.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/areaDemandante/edit"
        assert model.areaDemandanteInstance != null

        areaDemandante.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/areaDemandante/show/$areaDemandante.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        areaDemandante.clearErrors()

        populateValidParams(params)
        params.id = areaDemandante.id
        params.version = -1
        controller.update()

        assert view == "/areaDemandante/edit"
        assert model.areaDemandanteInstance != null
        assert model.areaDemandanteInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/areaDemandante/list'

        response.reset()

        populateValidParams(params)
        def areaDemandante = new AreaDemandante(params)

        assert areaDemandante.save() != null
        assert AreaDemandante.count() == 1

        params.id = areaDemandante.id

        controller.delete()

        assert AreaDemandante.count() == 0
        assert AreaDemandante.get(areaDemandante.id) == null
        assert response.redirectedUrl == '/areaDemandante/list'
    }
}
