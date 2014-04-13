package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(FuncionalidadeController)
@Mock(Funcionalidade)
class FuncionalidadeControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/funcionalidade/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.funcionalidadeInstanceList.size() == 0
        assert model.funcionalidadeInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.funcionalidadeInstance != null
    }

    void testSave() {
        controller.save()

        assert model.funcionalidadeInstance != null
        assert view == '/funcionalidade/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/funcionalidade/show/1'
        assert controller.flash.message != null
        assert Funcionalidade.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/funcionalidade/list'

        populateValidParams(params)
        def funcionalidade = new Funcionalidade(params)

        assert funcionalidade.save() != null

        params.id = funcionalidade.id

        def model = controller.show()

        assert model.funcionalidadeInstance == funcionalidade
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/funcionalidade/list'

        populateValidParams(params)
        def funcionalidade = new Funcionalidade(params)

        assert funcionalidade.save() != null

        params.id = funcionalidade.id

        def model = controller.edit()

        assert model.funcionalidadeInstance == funcionalidade
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/funcionalidade/list'

        response.reset()

        populateValidParams(params)
        def funcionalidade = new Funcionalidade(params)

        assert funcionalidade.save() != null

        // test invalid parameters in update
        params.id = funcionalidade.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/funcionalidade/edit"
        assert model.funcionalidadeInstance != null

        funcionalidade.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/funcionalidade/show/$funcionalidade.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        funcionalidade.clearErrors()

        populateValidParams(params)
        params.id = funcionalidade.id
        params.version = -1
        controller.update()

        assert view == "/funcionalidade/edit"
        assert model.funcionalidadeInstance != null
        assert model.funcionalidadeInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/funcionalidade/list'

        response.reset()

        populateValidParams(params)
        def funcionalidade = new Funcionalidade(params)

        assert funcionalidade.save() != null
        assert Funcionalidade.count() == 1

        params.id = funcionalidade.id

        controller.delete()

        assert Funcionalidade.count() == 0
        assert Funcionalidade.get(funcionalidade.id) == null
        assert response.redirectedUrl == '/funcionalidade/list'
    }
}
