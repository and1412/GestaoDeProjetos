package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(FaseController)
@Mock(Fase)
class FaseControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/fase/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.faseInstanceList.size() == 0
        assert model.faseInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.faseInstance != null
    }

    void testSave() {
        controller.save()

        assert model.faseInstance != null
        assert view == '/fase/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/fase/show/1'
        assert controller.flash.message != null
        assert Fase.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/fase/list'

        populateValidParams(params)
        def fase = new Fase(params)

        assert fase.save() != null

        params.id = fase.id

        def model = controller.show()

        assert model.faseInstance == fase
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/fase/list'

        populateValidParams(params)
        def fase = new Fase(params)

        assert fase.save() != null

        params.id = fase.id

        def model = controller.edit()

        assert model.faseInstance == fase
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/fase/list'

        response.reset()

        populateValidParams(params)
        def fase = new Fase(params)

        assert fase.save() != null

        // test invalid parameters in update
        params.id = fase.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/fase/edit"
        assert model.faseInstance != null

        fase.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/fase/show/$fase.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        fase.clearErrors()

        populateValidParams(params)
        params.id = fase.id
        params.version = -1
        controller.update()

        assert view == "/fase/edit"
        assert model.faseInstance != null
        assert model.faseInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/fase/list'

        response.reset()

        populateValidParams(params)
        def fase = new Fase(params)

        assert fase.save() != null
        assert Fase.count() == 1

        params.id = fase.id

        controller.delete()

        assert Fase.count() == 0
        assert Fase.get(fase.id) == null
        assert response.redirectedUrl == '/fase/list'
    }
}
