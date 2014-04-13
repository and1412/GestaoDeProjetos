package br.gov.mc

class Requestmap {

	String url
	String configAttribute

	static mapping = {
		datasource 'security'
		cache true
	}

	static constraints = {
		url blank: false, unique: true
		configAttribute blank: false
	}
}
