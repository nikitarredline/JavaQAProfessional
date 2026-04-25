pipeline {
    agent any

    stages {

        stage('Checkout info') {
            steps {
                echo "Running UI tests from homework_4 branch"
            }
        }

        stage('Run UI tests') {
            steps {
                sh '''
                    docker run --rm \
                      -v /root/jenkins_home/workspace/ui_tests:/workspace \
                      -w /workspace \
                      maven:3.9.9-eclipse-temurin-21 \
                      mvn clean test \
                      -Dremote.url=http://89.124.113.71/wd/hub \
                      -Dbrowser.name=chrome \
                      -Dbrowser.version=128.0
                '''
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