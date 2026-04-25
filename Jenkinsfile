pipeline {
    agent any

    stages {

        stage('Pull image') {
            steps {
                sh '''
                    docker pull 89.124.113.71:5005/ui-tests:1.0
                '''
            }
        }

        stage('Run UI tests') {
            steps {
                sh '''
                    docker run --rm \
                      -v /root/jenkins_home/workspace/ui_tests:/workspace \
                      -w /workspace \
                      89.124.113.71:5005/ui-tests:1.0 \
                      mvn clean test \
                        -Dremote.url=http://89.124.113.71/wd/hub \
                        -Dbrowser.name=chrome \
                        -Dbrowser.version=128.0
                '''
            }
        }
    }

    post {
        always {
            echo "GENERATING ALLURE REPORT"

            allure([
                includeProperties: false,
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'target/allure-results']]
            ])

            echo "PIPELINE FINISHED"
        }
    }
}