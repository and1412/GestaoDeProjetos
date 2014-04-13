package br.gov.mc



import org.junit.*
import grails.test.mixin.*

@TestFor(ProjetoController)
@Mock(Projeto)
class ProjetoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/projeto/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.projetoInstanceList.size() == 0
        assert model.projetoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.projetoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.projetoInstance != null
        assert view == '/projeto/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/projeto/show/1'
        assert controller.flash.message != null
        assert Projeto.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/projeto/list'

        populateValidParams(params)
        def projeto = new Projeto(params)

        assert projeto.save() != null

        params.id = projeto.id

        def model = controller.show()

        assert model.projetoInstance == projeto
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/projeto/list'

        populateValidParams(params)
        def projeto = new Projeto(params)

        assert projeto.save() != null

        params.id = projeto.id

        def model = controller.edit()

        assert model.projetoInstance == projeto
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/projeto/list'

        response.reset()

        populateValidParams(params)
        def projeto = new Projeto(params)

        assert projeto.save() != null

        // test invalid parameters in update
        params.id = projeto.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/projeto/edit"
        assert model.projetoInstance != null

        projeto.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/projeto/show/$projeto.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        projeto.clearErrors()

        populateValidParams(params)
        params.id = projeto.id
        params.version = -1
        controller.update()

        assert view == "/projeto/edit"
        assert model.projetoInstance != null
        assert model.projetoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/projeto/list'

        response.reset()

        populateValidParams(params)
        def projeto = new Projeto(params)

        assert projeto.save() != null
        assert Projeto.count() == 1

        params.id = projeto.id

        controller.delete()

        assert Projeto.count() == 0
        assert Projeto.get(projeto.id) == null
        assert response.redirectedUrl == '/projeto/list'
    }
}
