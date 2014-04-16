//dataSource {
//    pooled = true
//    jmxExport = true
//    driverClassName = "org.h2.Driver"
//    username = "sa"
//    password = ""
//}
//hibernate {
//    cache.use_second_level_cache = true
//    cache.use_query_cache = false
//    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
////    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
//    singleSession = true // configure OSIV singleSession mode
//}

grails {
    mongo {
        host = "localhost"
        port = 27017
//        username = "blah"
//        password = "blah"
    }
}

// environment specific settings
environments {
    development {
        grails {
            mongo {
                databaseName = "sst-analyze-dev"
            }
        }
    }
    test {
        grails {
            mongo {
                databaseName = "sst-analyze-test"
            }
        }
    }
    production {
        grails {
            mongo {
                databaseName = "sst-analyze"
            }
        }
    }
}
