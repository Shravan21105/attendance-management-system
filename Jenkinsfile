pipeline {
agent any

```
environment {
    AWS_ACCOUNT_ID = '816975732391'
    AWS_REGION = 'ap-south-1'
    IMAGE_NAME = 'attendance-system'
    ECR_REPO = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}"
}

stages {

    stage('Build Maven Project') {
        agent {
            docker {
                image 'maven:3.9.6-eclipse-temurin-21'
                args '-u root:root'
            }
        }

        steps {

            sh 'mvn clean package -DskipTests'

            sh 'echo ===== CURRENT DIRECTORY FILES ====='
            sh 'ls -la'

            sh 'echo ===== TARGET DIRECTORY FILES ====='
            sh 'ls -la target'
        }
    }

    stage('Build Docker Image') {
        steps {
            sh "docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} ."
        }
    }

    stage('Tag Docker Image') {
        steps {
            sh "docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${ECR_REPO}:${BUILD_NUMBER}"
        }
    }

    stage('Login to AWS ECR') {
        steps {

            withCredentials([[
                $class: 'AmazonWebServicesCredentialsBinding',
                credentialsId: 'aws-ecr-credentials'
            ]]) {

                sh """
                aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com
                """
            }
        }
    }

    stage('Push Image to ECR') {
        steps {
            sh "docker push ${ECR_REPO}:${BUILD_NUMBER}"
        }
    }

    stage('Deploy Container') {
        steps {

            sh """
            docker stop attendance-container || true
            docker rm attendance-container || true

            docker run -d \
            --name attendance-container \
            -p 8080:8080 \
            ${ECR_REPO}:${BUILD_NUMBER}
            """
        }
    }
}

post {

    success {
        echo 'Build and Deployment Successful'
    }

    failure {
        echo 'Build Failed'
    }
}
```

}
