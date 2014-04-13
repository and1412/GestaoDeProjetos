package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(AuditoriaFaseController)
@Mock(AuditoriaFase)
class AuditoriaFaseControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/auditoriaFase/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.auditoriaFaseInstanceList.size() == 0
        assert model.auditoriaFaseInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.auditoriaFaseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.auditoriaFaseInstance != null
        assert view == '/auditoriaFase/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/auditoriaFase/show/1'
        assert controller.flash.message != null
        assert AuditoriaFase.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/auditoriaFase/list'

        populateValidParams(params)
        def auditoriaFase = new AuditoriaFase(params)

        assert auditoriaFase.save() != null

        params.id = auditoriaFase.id

        def model = controller.show()

        assert model.auditoriaFaseInstance == auditoriaFase
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/auditoriaFase/list'

        populateValidParams(params)
        def auditoriaFase = new AuditoriaFase(params)

        assert auditoriaFase.save() != null

        params.id = auditoriaFase.id

        def model = controller.edit()

        assert model.auditoriaFaseInstance == auditoriaFase
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/auditoriaFase/list'

        response.reset()

        populateValidParams(params)
        def auditoriaFase = new AuditoriaFase(params)

        assert auditoriaFase.save() != null

        // test invalid parameters in update
        params.id = auditoriaFase.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/auditoriaFase/edit"
        assert model.auditoriaFaseInstance != null

        auditoriaFase.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/auditoriaFase/show/$auditoriaFase.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        auditoriaFase.clearErrors()

        populateValidParams(params)
        params.id = auditoriaFase.id
        params.version = -1
        controller.update()

        assert view == "/auditoriaFase/edit"
        assert model.auditoriaFaseInstance != null
        assert model.auditoriaFaseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/auditoriaFase/list'

        response.reset()

        populateValidParams(params)
        def auditoriaFase = new AuditoriaFase(params)

        assert auditoriaFase.save() != null
        assert AuditoriaFase.count() == 1

        params.id = auditoriaFase.id

        controller.delete()

        assert AuditoriaFase.count() == 0
        assert AuditoriaFase.get(auditoriaFase.id) == null
        assert response.redirectedUrl == '/auditoriaFase/list'
    }
}
