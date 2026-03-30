pipeline {
  agent any

  environment {
    APP_NAME = 'telecom-middleware'
    DOCKER_HUB = "rakeshdocker7"
    KUBECONFIG = "C:\\ProgramData\\Jenkins\\.kube\\config"
  }

  options {
    buildDiscarder(logRotator(numToKeepStr: '10'))
    timestamps()
  }

  stages {

    stage('Checkout') {
      steps {
        echo '📥 Checkout middleware sources'
        checkout scm
      }
    }

    stage('Build (Maven)') {
      steps {
        echo '📦 Building middleware'
        bat 'mvnw clean package -DskipTests'
      }
    }

    stage('Docker Build') {
      steps {
        echo '🐳 Build Docker image'
        bat 'docker build -t %DOCKER_HUB%/%APP_NAME%:latest .'
      }
    }

    stage('Docker Push') {
      steps {
        echo '📤 Push Docker image'

        withCredentials([usernamePassword(
          credentialsId: 'docker-hub-creds',
          usernameVariable: 'DOCKER_USER',
          passwordVariable: 'DOCKER_PASS'
        )]) {

          bat '''
          echo %DOCKER_PASS% | docker login -u %DOCKER_USER% --password-stdin
          docker push %DOCKER_HUB%/%APP_NAME%:latest
          '''
        }
      }
    }

    stage('Kubernetes Deploy') {
      steps {
        echo '🚀 Deploying to Kubernetes'

        bat '''
        kubectl apply -f k8s/deployment.yaml
        kubectl apply -f k8s/service.yaml
        '''
      }
    }
  }

  post {
    success {
      echo '✅ Middleware pipeline completed successfully 🚀'
    }
    failure {
      echo '❌ Middleware pipeline failed'
    }
  }
}
