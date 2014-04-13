@artifact.package@class @artifact.name@ {

    static constraints = {
    }
    
    //- Executed when an object is loaded from the database    
    def onLoad(){
        log.info("onload...")
    }
    
    //- Executed before an object is initially persisted to the database
    def beforeInsert(){
         log.info("beforeInsert...")
    }

    //- Executed before an object is updated
    def beforeUpdate(){
         log.info("beforeUpdate...")
    }
    
    //- Executed before an object is deleted
    def beforeDelete(){
         log.info("beforeDelete...")
    }
    
    //- Executed before an object is validated
    def beforeValidate(){
         log.info("beforeValidate...")
    }

    //- Executed after an object is persisted to the database
    def afterInsert() {
        log.info("afterInsert...")
    }

    //- Executed after an object has been updated
    def afterUpdate(){
         log.info("afterUpdate...")
    }
    
    // - Executed after an object has been deleted
    def afterDelete(){
         log.info("afterDelete...")
    }
}
