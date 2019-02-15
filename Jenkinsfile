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
    stage ('GC Authorize') {
    	steps {
        	sh 'gcloud auth login' 
       }
    }
    stage ('Deploy to GC') {
    	steps {
        	sh 'mvn appengine:deploy' 
       }
    }
  }
}