package br.gov.mc



class FileManagerConstraint {

    static expectsParams = ['name','type']

    def validate = { propertyValue ->
        return null
    }
}
