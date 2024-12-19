#!/usr/bin/env groovy

pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')
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

        stage('Build and Test') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Verify WAR file') {
            steps {
                sh 'ls -l target/'
                sh 'test -f target/bank-management-system.war || (echo "bank-management-system.war not found" && exit 1)'
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
    }
}