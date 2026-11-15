pipeline {
    agent any
    tools {
        jdk 'Java17'
        maven 'Maven3'
    }
    stages {
        stage('UI regression suite') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn -B test'
                    } else {
                        bat 'mvn -B test'
                    }
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/TEST-*.xml'
                }
            }
        }
    }
}
