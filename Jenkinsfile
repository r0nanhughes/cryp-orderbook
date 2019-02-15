pipeline {
  agent any
  tools {
        maven 'maven'
  }
  stages {
    stage('checkout project') {
      steps {
        checkout scm
      }
    }
 	stage ('Test') {
    	steps {
        	sh 'mvn clean test' 
       }
    }
    stage ('Deploy to GC') {
    	steps {
        	sh 'mvn appengine:deploy' 
       }
    }
  }
}