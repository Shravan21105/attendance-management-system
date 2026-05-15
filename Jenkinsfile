pipeline {

    agent any

    environment {

        AWS_ACCOUNT_ID = '816975732391'
        AWS_REGION = 'ap-south-1'
        IMAGE_NAME = 'attendance-system'

        ECR_REPO = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/${IMAGE_NAME}"
    }

    stages {

        stage('Build Maven Project') {

            steps {

                sh 'mvn clean package -DskipTests'

                sh 'echo ===== PROJECT FILES ====='
                sh 'ls -la'

                sh 'echo ===== TARGET FILES ====='
                sh 'ls -la target'
            }
        }

        stage('Build Docker Image') {

            steps {

                sh """
                docker build -t ${IMAGE_NAME}:${BUILD_NUMBER} .
                """
            }
        }

        stage('Tag Docker Image') {

            steps {

                sh """
                docker tag ${IMAGE_NAME}:${BUILD_NUMBER} ${ECR_REPO}:${BUILD_NUMBER}
                """
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

                sh """
                docker push ${ECR_REPO}:${BUILD_NUMBER}
                """
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

        stage('Verify Deployment') {

            steps {

                sh """
                docker ps
                """

                sh """
                sleep 10
                curl http://localhost:8080/attendance/status || true
                """
            }
        }
    }

    post {

        success {

            echo 'Build and Deployment Successful'

            emailext(

                subject: "SUCCESS: ${JOB_NAME} #${BUILD_NUMBER}",

                body: """
Build Successful

Job Name: ${JOB_NAME}

Build Number: ${BUILD_NUMBER}

Docker Image:
${ECR_REPO}:${BUILD_NUMBER}

Build URL:
${BUILD_URL}

Deployment Status:
Application deployed successfully on EC2.
""",

                to: 'shravansadalgekar2005@gmail.com'
            )
        }

        failure {

            echo 'Build Failed'

            emailext(

                subject: "FAILED: ${JOB_NAME} #${BUILD_NUMBER}",

                body: """
Build Failed

Job Name: ${JOB_NAME}

Build Number: ${BUILD_NUMBER}

Build URL:
${BUILD_URL}

Please check Jenkins Console Output.
""",

                to: 'shravansadalgekar2005@gmail.com'
            )
        }
    }
}