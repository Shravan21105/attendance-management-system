pipeline {
    agent any

    stages {

        stage('Build Maven Project') {
            agent {
                docker {
                    image 'maven:3.9.6-eclipse-temurin-21'
                    args '-u root -v /tmp:/tmp'
                }
            }

            steps {
                sh 'rm -rf target'
                sh 'mvn package -Dmaven.repo.local=/tmp/.m2'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t attendance-system .'
            }
        }

    }
}