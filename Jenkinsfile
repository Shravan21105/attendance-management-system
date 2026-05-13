pipeline {
    agent any

    stages {

        stage('Build Maven Project') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-21'
                    args '-u root:root'
                }
            }

            steps {
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t attendance-system .'
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                docker stop attendance-container || true
                docker rm attendance-container || true
                docker run -d --name attendance-container -p 8080:8080 attendance-system
                '''
            }
        }

    }
}