# Configurations
All configuration MUST be set in the `buildfiles/<env>.properties` file used for compilation
- current.env = the currently executing environment
- nome.ambiente = the name of the environment
- datasource.jndi-url = no more used. May be left to blank or to a default value
- messageSources.cacheSeconds = no more used. May be left to -1
- flag-debug = the Java compiler flag to activate debug symbols (on/off)
- jpaVendorAdapter.showSql = Specifies whether the JPA-generated SQL
    should be logged (deprecated)
- remincl.resource.provider = URL to the remote resources
- remincl.cache.time = caching time for the remote resources (default: 8 hours)
- portal.home = portal home for the logout
- sso.filter.name = Name for the SSO filter
- sso.filter.url.pattern = URL pattern for the SSO filter
    (specify a non-existing extension to prevent SSO checks)
- sso.loginHandler = fully-qualified class name for the SSO handler
- endpoint.url.service.core = Endpoint for the COR backend service
- datasource.url = The URL to the datasource (deprecated - use the JNDI to connect)
- datasource.user = The user with which to connect to the datasource
    (deprecated - use the JNDI to connect)
- datasource.password = The password with which to connect to the datasource
    (deprecated - use the JNDI to connect)
- birt.working.folder = The working folder for the BIRT report engine
- persistence.unit.showSql = Specifies whether the JPA-generated SQL should be logged
- persistence.unit.formatSql = Specifies whether the JPA-generated SQL should be formatted
- persistence.unit.use_get_generated_keys = Tells the JPA provider to retrieve the
    generated keys
- persistence.unit.use_jdbc_metadata_defaults = Tells the JPA provider not to connect to
    the database to retrieve metadata informations
- jspath = the path for the local JavaScript files (for proxying support)
- jspathexternal = the path for the external JavaScript files (for proxying support)
