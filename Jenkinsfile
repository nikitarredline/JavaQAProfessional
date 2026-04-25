pipeline {
    agent any

    parameters {
        string(name: 'SELENOID_URL', defaultValue: '', description: '')
        string(name: 'BROWSER', defaultValue: '', description: '')
        string(name: 'BROWSER_VERSION', defaultValue: '', description: '')
        string(name: 'DEVICE_NAME', defaultValue: '', description: '')
    }

    stages {

        stage('Run tests') {
            steps {
                script {

                    def args = ""

                    if (params.SELENOID_URL?.trim()) {
                        args += " -Dremote.url=${params.SELENOID_URL}"
                    }

                    if (params.BROWSER?.trim()) {
                        args += " -Dbrowser.name=${params.BROWSER}"
                    }

                    if (params.BROWSER_VERSION?.trim()) {
                        args += " -Dbrowser.version=${params.BROWSER_VERSION}"
                    }

                    if (params.DEVICE_NAME?.trim()) {
                        args += " -DdeviceName=${params.DEVICE_NAME}"
                    }

                    sh """
                        docker run --rm \
                          -v /root/jenkins_home/workspace/ui_tests:/workspace \
                          -w /workspace \
                          maven:3.9.9-eclipse-temurin-21 \
                          mvn clean test ${args}
                    """
                }
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                    includeProperties: false,
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'target/allure-results']]
                ])
            }
        }
    }

    post {
        always {
            echo "PIPELINE FINISHED"
        }
    }
}