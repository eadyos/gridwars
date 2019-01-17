def mysqlProperties = {
    maxActive                       = -1                // Max number of connections, -1 = infinite
    minEvictableIdleTimeMillis      = 30*60*1000        // Idle time before connection is automatically dropped.
    timeBetweenEvictionRunsMillis   = 30*60*1000        // How often to drop/test connections. -1 = never
    numTestsPerEvictionRun          = 3                 // Number of connections tested each run.
    testOnBorrow                    = true              // Test connection before giving it out.
    testWhileIdle                   = true              // Test connection during eviction runs.
    testOnReturn                    = true              // Test connection when returned to pool.
    validationQuery                 = "SELECT 1"        // Query to run to test connection.
}

dataSource {
    pooled = true
    jmxExport = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}
hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
//    cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
    singleSession = true // configure OSIV singleSession mode
}

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
//        dataSource {
//            dbCreate = "create" // one of 'create', 'create-drop', 'update', 'validate', ''
//            pooled = true
//            username = "dev"
//            password = "dev"
//            driverClassName = "com.mysql.jdbc.Driver"
//            url = "jdbc:mysql://localhost/gridwars?useUnicode=yes&characterEncoding=UTF-8"
//            properties mysqlProperties
//        }

    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }
    }
    production {
//        dataSource {
//            dbCreate = "update"
//            url = "jdbc:h2:prodDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
//            properties {
//               // See http://grails.org/doc/latest/guide/conf.html#dataSource for documentation
//               jmxEnabled = true
//               initialSize = 5
//               maxActive = 50
//               minIdle = 5
//               maxIdle = 25
//               maxWait = 10000
//               maxAge = 10 * 60000
//               timeBetweenEvictionRunsMillis = 5000
//               minEvictableIdleTimeMillis = 60000
//               validationQuery = "SELECT 1"
//               validationQueryTimeout = 3
//               validationInterval = 15000
//               testOnBorrow = true
//               testWhileIdle = true
//               testOnReturn = false
//               jdbcInterceptors = "ConnectionState"
//               defaultTransactionIsolation = java.sql.Connection.TRANSACTION_READ_COMMITTED
//            }
//        }

        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000;DB_CLOSE_ON_EXIT=FALSE"
        }

//
//        dataSource {
//            pooled = true
//            username = "dev"
//            password = "dev"
//            driverClassName = "com.mysql.jdbc.Driver"
//            dialect = "org.hibernate.dialect.MySQL5UTF8InnoDBDialect"
//            url = "jdbc:mysql://localhost/gridwars?useUnicode=yes&characterEncoding=UTF-8"
//            properties mysqlProperties
//        }

    }
}
