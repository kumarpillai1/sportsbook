#!/usr/bin/env groovy
node {
    checkout scm

    stage('Build') {
        // Run the maven build
        if (isUnix()) {
            pwd
            sh 'echo $JAVA_HOME'
            sh 'ls -al'
            sh '/build_tools/maven/apache-maven-3.3.3/bin/mvn clean package'
            sh '/build_tools/maven/apache-maven-3.3.3/bin/mvn test -Denvironment=MCT'
        }
    }
    stage('Results') {
        if (isUnix()) {
            if (fileExists('**/target/surefire-reports/TEST-*.xml')) {
                echo 'Test Report exists'
                junit '**/target/surefire-reports/TEST-*.xml'
                archive 'target/*.jar'
            } else {
                echo 'Build completed without any errors!'
            }
        }
    }
}
