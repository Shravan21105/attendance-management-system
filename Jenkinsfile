pipeline {
    agent any

    stages {

        stage('Build Maven Project') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-21'
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

    }
}