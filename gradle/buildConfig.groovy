environments {

    dev {
        cf {
            target = 'https://api.run.pivotal.io'
            space = 'development'
            uri = 'http://cfDeployer-dev.cfapps.io'
            application = 'cfDeployer-dev'
        }
    }

    staging {
        cf {
            target = 'https://api.run.pivotal.io'
            space = 'staging'
            uri = 'http://cfDeployer-staging.cfapps.io'
            application = 'cfDeployer-staging'
        }
    }
}