package br.gov.mc

class Arquitetura {

    String linguagem
    String servidorWeb
    String bancoDados
    String componentes
    
    static auditable = true
    static constraints = {
        // ver a possibilidade de carregar uma lista de arquivo
         linguagem (blank:false, inList:["JAVA","PHP"] )
         servidorWeb (blank:false, inList:["JBOSS","APACHE","TOMCAT"] )
         bancoDados (blank:false, inList:["SQL SERVER","POSTGREE","MYSQL"] )
         componentes (blank:false, inList:["MVC: HIBERNATE + JSP + STRUTS","MVC: HIBERNATE + JSF + SPRING","MVC: HIBERNATE + SPRING + GRAILS/GROOVY", "PHP"] )
    }
    
    String toString() {"$linguagem" + "," + "$servidorWeb" + "," +  "$bancoDados" + "," + componentes }
}
