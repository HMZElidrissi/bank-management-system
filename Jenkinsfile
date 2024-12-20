#!/usr/bin/env groovy

pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
        SONAR_PROJECT_KEY = 'bank-management-system'
    }

    stages {
        stage('Prepare Version') {
            steps {
                script {
                    def pom = readMavenPom file: 'pom.xml'
                    def currentVersion = pom.version

                    def versionParts = currentVersion.split('\\.')
                    def major = versionParts[0] as int
                    def minor = versionParts[1] as int
                    def patch = versionParts[2] as int
                    patch++

                    env.NEW_VERSION = "${major}.${minor}.${patch}"

                    sh "mvn versions:set -DnewVersion=${env.NEW_VERSION}"
                }
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} \
                        -Dsonar.java.coveragePlugin=jacoco \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    """
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("hmzelidrissi/bank-management-system:${env.NEW_VERSION}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    // docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials') {
                    //     docker.image("hmzelidrissi/bank-management-system:${env.NEW_VERSION}").push()
                    // }
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                    sh "docker push hmzelidrissi/bank-management-system:${env.NEW_VERSION}"
                }
            }
        }

        stage('Commit Version Update') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-credentials', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                        sh """
                            git config user.email 'jenkins@hmzelidrissi.ma'
                            git config user.name 'Jenkins'
                            git add pom.xml
                            git commit -m 'Bump version to ${env.NEW_VERSION} [ci skip]'
                            git push https://\${GIT_USERNAME}:\${GIT_PASSWORD}@github.com/HMZElidrissi/bank-management-system.git HEAD:main
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker logout'
        }
        success {
            emailext (
                subject: "Pipeline Successful: ${currentBuild.fullDisplayName}",
                body: "The pipeline completed successfully.",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
        failure {
            emailext (
                subject: "Pipeline Failed: ${currentBuild.fullDisplayName}",
                body: "The pipeline failed. Please check the Jenkins console for details.",
                recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            )
        }
    }
}