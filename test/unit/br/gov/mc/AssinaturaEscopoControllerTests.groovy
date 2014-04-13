package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(AssinaturaEscopoController)
@Mock(AssinaturaEscopo)
class AssinaturaEscopoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/assinaturaEscopo/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.assinaturaEscopoInstanceList.size() == 0
        assert model.assinaturaEscopoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.assinaturaEscopoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.assinaturaEscopoInstance != null
        assert view == '/assinaturaEscopo/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/assinaturaEscopo/show/1'
        assert controller.flash.message != null
        assert AssinaturaEscopo.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/assinaturaEscopo/list'

        populateValidParams(params)
        def assinaturaEscopo = new AssinaturaEscopo(params)

        assert assinaturaEscopo.save() != null

        params.id = assinaturaEscopo.id

        def model = controller.show()

        assert model.assinaturaEscopoInstance == assinaturaEscopo
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/assinaturaEscopo/list'

        populateValidParams(params)
        def assinaturaEscopo = new AssinaturaEscopo(params)

        assert assinaturaEscopo.save() != null

        params.id = assinaturaEscopo.id

        def model = controller.edit()

        assert model.assinaturaEscopoInstance == assinaturaEscopo
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/assinaturaEscopo/list'

        response.reset()

        populateValidParams(params)
        def assinaturaEscopo = new AssinaturaEscopo(params)

        assert assinaturaEscopo.save() != null

        // test invalid parameters in update
        params.id = assinaturaEscopo.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/assinaturaEscopo/edit"
        assert model.assinaturaEscopoInstance != null

        assinaturaEscopo.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/assinaturaEscopo/show/$assinaturaEscopo.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        assinaturaEscopo.clearErrors()

        populateValidParams(params)
        params.id = assinaturaEscopo.id
        params.version = -1
        controller.update()

        assert view == "/assinaturaEscopo/edit"
        assert model.assinaturaEscopoInstance != null
        assert model.assinaturaEscopoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/assinaturaEscopo/list'

        response.reset()

        populateValidParams(params)
        def assinaturaEscopo = new AssinaturaEscopo(params)

        assert assinaturaEscopo.save() != null
        assert AssinaturaEscopo.count() == 1

        params.id = assinaturaEscopo.id

        controller.delete()

        assert AssinaturaEscopo.count() == 0
        assert AssinaturaEscopo.get(assinaturaEscopo.id) == null
        assert response.redirectedUrl == '/assinaturaEscopo/list'
    }
}
