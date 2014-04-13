// locations to search for config files that get merged into the main config;
// config files can be ConfigSlurper scripts, Java properties files, or classes
// in the classpath in ConfigSlurper format

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if (System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [
    all:           '*/*',
    atom:          'application/atom+xml',
    css:           'text/css',
    csv:           'text/csv',
    form:          'application/x-www-form-urlencoded',
    html:          ['text/html','application/xhtml+xml'],
    js:            'text/javascript',
    json:          ['application/json', 'text/json'],
    multipartForm: 'multipart/form-data',
    rss:           'application/rss+xml',
    text:          'text/plain',
    xml:           ['text/xml', 'application/xml']
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

// What URL patterns should be processed by the resources plugin
grails.resources.adhoc.patterns = ['/images/*', '/css/*', '/js/*', '/plugins/*']

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = 'Instance'

// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = []
// whether to disable processing of multi part requests
grails.web.disable.multipart=false

// request parameters to mask when logging exceptions
grails.exceptionresolver.params.exclude = ['password']

// configure auto-caching of queries by default (if false you can cache individual queries with 'cache: true')
grails.hibernate.cache.queries = false

environments {
    development {
        grails.logging.jul.usebridge = true
    }
    production {
        grails.logging.jul.usebridge = false
        // TODO: grails.serverURL = "http://www.changeme.com"
    }
}

// log4j configuration
log4j = {

    appenders 
    {
        //Saída de log para o console ou terminal
        console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
        //Saída de log para um arquivo xml chamado applog.xml na pasta target
        file name: 'applogxml', layout: xml, file: 'target/applog.xml'
        //Saida de log para um arquivo txt chamado applog.log na pasta target
        file name: 'applogtxt', file: 'target/applog.log',
          layout: pattern(conversionPattern: "[%d{HH:mm:ss:SSS}] %-5p %c{2}: %m%n")
      
        //Saída de log para um banco de dados chamado ProjetosDEVLog
        //Atenção esse banco deve ser criado manualmente, na pasta do projeto consta um dump
        //do banco chamado ProjetosDEVLog.sql que contêm as especificações da tabela.
        appender new org.apache.log4j.jdbc.JDBCAppender(
            name: "applogjdbc",
            URL: "jdbc:mysql://localhost/ProjetosDEVLog?useUnicode=yes&characterEncoding=UTF-8",
            user: "lellis",
            password: "123456",
            driver: "com.mysql.jdbc.Driver",
            sql: "INSERT INTO log VALUES(NULL,'Message: %m','[%-5p]','%d{yyyy.MM.dd HH:mm:ss}','Category: %c');"
        )

        //Saída de trace que irá interceptar todas as exceções que são disparadas na aplicação.
        appender new org.apache.log4j.jdbcplus.JDBCAppender(
            name: "stacktrace",
            url: "jdbc:mysql://localhost/ProjetosDEVLog?useUnicode=yes&characterEncoding=UTF-8",
            username: "lellis",
            password: "123456",
            dbclass: "com.mysql.jdbc.Driver",
            sql: "INSERT INTO trace VALUES(NULL,'@THROWABLE@','@TIMESTAMP@','@CAT@');"
        )

    }

    // Mapeando a área de atuação de cada appender
    info additivity: false, applogxml:
    [
        'grails.app.controllers',
        'grails.app.domain',
        'grails.app.services',
        'grails.app.taglib',
        'grails.app.conf'
    ]

    info additivity: false, applogtxt:
    [
        'grails.app.controllers',
        'grails.app.domain',
        'grails.app.services',
        'grails.app.taglib',
        'grails.app.conf'
    ]

    info additivity: false, applogjdbc: 
    [
        'grails.app.controllers',
        'grails.app.domain',
        'grails.app.services',
        'grails.app.taglib',
        'grails.app.conf'
    ]
    
    error stdout:
    [
        'org.codehaus.groovy.grails.web.servlet',        // controllers
        'org.codehaus.groovy.grails.web.pages',          // GSP
        'org.codehaus.groovy.grails.web.sitemesh',       // layouts
        'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
        'org.codehaus.groovy.grails.web.mapping',        // URL mapping
        'org.codehaus.groovy.grails.commons',            // core / classloading
        'org.codehaus.groovy.grails.plugins',            // plugins
        'org.codehaus.groovy.grails.orm.hibernate',      // hibernate integration
        'org.springframework',
        'org.hibernate'
    ]

    root {
       info 'applogtxt'
    }
  
}

grails {
    mail {
        host = "smtp.gmail.com"
        port = 465
        username = "gestaoprojetosmc@gmail.com"
        password = "gestaoprojetos"
        props = ["mail.smtp.auth":"true",                     
                  "mail.smtp.socketFactory.port":"465",
                  "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                  "mail.smtp.socketFactory.fallback":"false"]
   }
}

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'br.gov.mc.User'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'br.gov.mc.UserRole'
grails.plugins.springsecurity.authority.className = 'br.gov.mc.Role'
grails.plugins.springsecurity.requestMap.className = 'br.gov.mc.Requestmap'
grails.plugins.springsecurity.securityConfigType = 'Requestmap'
