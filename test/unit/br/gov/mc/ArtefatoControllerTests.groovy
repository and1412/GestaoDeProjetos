package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(ArtefatoController)
@Mock(Artefato)
class ArtefatoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/artefato/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.artefatoInstanceList.size() == 0
        assert model.artefatoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.artefatoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.artefatoInstance != null
        assert view == '/artefato/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/artefato/show/1'
        assert controller.flash.message != null
        assert Artefato.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/artefato/list'

        populateValidParams(params)
        def artefato = new Artefato(params)

        assert artefato.save() != null

        params.id = artefato.id

        def model = controller.show()

        assert model.artefatoInstance == artefato
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/artefato/list'

        populateValidParams(params)
        def artefato = new Artefato(params)

        assert artefato.save() != null

        params.id = artefato.id

        def model = controller.edit()

        assert model.artefatoInstance == artefato
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/artefato/list'

        response.reset()

        populateValidParams(params)
        def artefato = new Artefato(params)

        assert artefato.save() != null

        // test invalid parameters in update
        params.id = artefato.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/artefato/edit"
        assert model.artefatoInstance != null

        artefato.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/artefato/show/$artefato.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        artefato.clearErrors()

        populateValidParams(params)
        params.id = artefato.id
        params.version = -1
        controller.update()

        assert view == "/artefato/edit"
        assert model.artefatoInstance != null
        assert model.artefatoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/artefato/list'

        response.reset()

        populateValidParams(params)
        def artefato = new Artefato(params)

        assert artefato.save() != null
        assert Artefato.count() == 1

        params.id = artefato.id

        controller.delete()

        assert Artefato.count() == 0
        assert Artefato.get(artefato.id) == null
        assert response.redirectedUrl == '/artefato/list'
    }
}
