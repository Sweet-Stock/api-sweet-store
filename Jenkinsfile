pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh './gradlew assemble'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
            post {
                success {
                    sh 'kill -9 `lsof -t -i:8080` || echo "nada na porta 8080"'
                }
            }
        }
        stage('Deliver') {
            steps {
                  sh 'java -jar build/libs/api-sweet-store-0.0.1-SNAPSHOT.jar'
                
            }
        }
        
    }
}
