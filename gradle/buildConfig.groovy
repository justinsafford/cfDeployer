environments {

    dev {
        cf {
            target = 'https://api.run.pivotal.io'
            space = 'development'
            uri = 'http://cfDeployer-dev.cfapps.io'
            application = 'cfDeployer-dev'
        }
    }

    uat {
        cf {
            target = 'https://api.run.pivotal.io'
            space = 'uat'
            uri = 'http://cfDeployer-uat.cfapps.io'
            application = 'cfDeployer-uat'
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