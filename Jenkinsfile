#!groovy

library identifier: '3scale-toolbox-jenkins@master',
        retriever: modernSCM([$class: 'GitSCMSource',
                              remote: 'https://github.com/rh-integration/3scale-toolbox-jenkins.git',
                              traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]])

def service = null

node() {
  stage('Checkout Source') {
    checkout scm
  }

  stage("Deploy API in Prod") {
    echo "Deploying API"
    // Prepare
    service = toolbox.prepareThreescaleService(
        openapi: [filename: "src/main/resources/META-INF/openapi.yaml"],
        environment: [ baseSystemName: "books-library",
                       //publicBasePath: "/api/",
                       //environmentName: "prod",
                       privateBaseUrl: params.PRIVATE_URL ],
        toolbox: [ openshiftProject: "user10",
                   destination: "3scale-onprem",
                   image: "quay.io/redhat/3scale-toolbox:v0.17.1", // TODO: remove me once the final image is released
                   insecure: "yes",
                   secretName: params.SECRET_NAME,
                   activeDeadlineSeconds: 300],
        service: [:],
        applications: [
            [ name: "my-test-app", description: "This is used for tests", plan: "test", account: "ndobler" ]
        ],
        applicationPlans: [
          [ systemName: "test", name: "test", defaultPlan: true, published: true ]
        ]
    )

    echo "Got Service instance object"

    // Import OpenAPI
    service.importOpenAPI()

    echo "Service with system_name ${service.environment.targetSystemName} created !"

    // Create an Application Plan
    service.applyApplicationPlans()

    // Create an Application
    service.applyApplication()

    // Run integration tests
    // To run the integration tests when using APIcast SaaS instances, we need
    // to fetch the proxy definition to extract the staging public url
    //def proxy = service.readProxy("sandbox")
    //def userkey = service.applications[0].userkey
    //echo userKey
    
    //sh """set -e
    //echo "userkey is ${userkey}"
    //curl -sfk -w "GetAll: %{http_code}\n" -o /dev/null ${proxy.sandbox_endpoint}/api/books/all -H 'api-key: ${userkey}'
    //"""

    // Promote to production
    service.promoteToProduction()
  }

}