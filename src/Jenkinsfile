- job:
    name: ui_tests
    project-type: pipeline

    parameters:
      - string:
          name: REFSPEC
          default: main
          description: "Git branch"

      - string:
          name: SELENOID_URL
          default: ""
          description: "Optional (e.g. http://89.124.113.71/wd/hub)"

      - string:
          name: BROWSER
          default: ""
          description: "Optional (e.g. chrome)"

      - string:
          name: BROWSER_VERSION
          default: ""
          description: "Optional (e.g. 128.0)"

      - string:
          name: DEVICE_NAME
          default: ""
          description: "Optional (e.g. iPhoneX)"

    definition:
      cps:
        script: |
          pipeline {
              agent any

              stages {

                  stage('Checkout') {
                      steps {
                          deleteDir()
                          git branch: params.REFSPEC,
                              url: 'https://github.com/nikitarredline/JavaQAProfessional'
                      }
                  }

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