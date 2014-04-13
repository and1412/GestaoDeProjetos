package br.gov.mc

class Role {

	String authority

	static mapping = {
		datasource 'security'
		cache true
	}

	static constraints = {
		authority blank: false, unique: true
	}
}
