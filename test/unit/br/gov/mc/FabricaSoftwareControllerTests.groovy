package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(FabricaSoftwareController)
@Mock(FabricaSoftware)
class FabricaSoftwareControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/fabricaSoftware/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.fabricaSoftwareInstanceList.size() == 0
        assert model.fabricaSoftwareInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.fabricaSoftwareInstance != null
    }

    void testSave() {
        controller.save()

        assert model.fabricaSoftwareInstance != null
        assert view == '/fabricaSoftware/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/fabricaSoftware/show/1'
        assert controller.flash.message != null
        assert FabricaSoftware.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/fabricaSoftware/list'

        populateValidParams(params)
        def fabricaSoftware = new FabricaSoftware(params)

        assert fabricaSoftware.save() != null

        params.id = fabricaSoftware.id

        def model = controller.show()

        assert model.fabricaSoftwareInstance == fabricaSoftware
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/fabricaSoftware/list'

        populateValidParams(params)
        def fabricaSoftware = new FabricaSoftware(params)

        assert fabricaSoftware.save() != null

        params.id = fabricaSoftware.id

        def model = controller.edit()

        assert model.fabricaSoftwareInstance == fabricaSoftware
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/fabricaSoftware/list'

        response.reset()

        populateValidParams(params)
        def fabricaSoftware = new FabricaSoftware(params)

        assert fabricaSoftware.save() != null

        // test invalid parameters in update
        params.id = fabricaSoftware.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/fabricaSoftware/edit"
        assert model.fabricaSoftwareInstance != null

        fabricaSoftware.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/fabricaSoftware/show/$fabricaSoftware.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        fabricaSoftware.clearErrors()

        populateValidParams(params)
        params.id = fabricaSoftware.id
        params.version = -1
        controller.update()

        assert view == "/fabricaSoftware/edit"
        assert model.fabricaSoftwareInstance != null
        assert model.fabricaSoftwareInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/fabricaSoftware/list'

        response.reset()

        populateValidParams(params)
        def fabricaSoftware = new FabricaSoftware(params)

        assert fabricaSoftware.save() != null
        assert FabricaSoftware.count() == 1

        params.id = fabricaSoftware.id

        controller.delete()

        assert FabricaSoftware.count() == 0
        assert FabricaSoftware.get(fabricaSoftware.id) == null
        assert response.redirectedUrl == '/fabricaSoftware/list'
    }
}
