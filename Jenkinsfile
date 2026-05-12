pipeline {
    agent any

    stages {

        stage('Build Maven Project') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-21'
                    args '-u root'
                }
            }

            steps {
                sh 'mvn clean package -Dmaven.repo.local=/tmp/.m2/repository'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t attendance-system .'
            }
        }

    }
}