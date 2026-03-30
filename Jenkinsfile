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
    ansiColor('xterm')
  }

  stages {
    stage('Checkout') {
      steps {
        echo '📥 Checkout middleware sources'
        checkout scm
      }
    }

    stage('Maven Test') {
      steps {
        echo '🧪 Run middleware unit tests'
        sh './mvnw clean test -DskipITs=true'
      }
    }

    stage('Package') {
      steps {
        echo '📦 Package middleware'
        sh './mvnw clean package -DskipTests=true'
      }
    }

    stage('Docker Build') {
      steps {
        echo '🐳 Build middleware Docker image'
        sh '''
          docker build -t ${DOCKER_REGISTRY}/${APP_NAME}:${BUILD_NUMBER} .
          docker tag ${DOCKER_REGISTRY}/${APP_NAME}:${BUILD_NUMBER} ${DOCKER_REGISTRY}/${APP_NAME}:latest
        '''
      }
    }

    stage('Docker Push') {
      when {
        expression { env.BRANCH_NAME == 'main' || env.BRANCH_NAME == 'master' }
      }
      steps {
        echo '📤 Push middleware Docker image to registry'
        sh '''
          echo "${DOCKER_CREDENTIALS_PSW}" | docker login -u "${DOCKER_CREDENTIALS_USR}" --password-stdin ${DOCKER_REGISTRY}
          docker push ${DOCKER_REGISTRY}/${APP_NAME}:${BUILD_NUMBER}
          docker push ${DOCKER_REGISTRY}/${APP_NAME}:latest
          docker logout
        '''
      }
    }
     stage('Kubernetes Deploy') {
            steps {
                echo '🚀 Deploying to Kubernetes'

                bat '''
                kubectl apply -f k8s/deployment.yaml
                kubectl apply -f k8s/service.yaml
                '''
               }   }
     
  
  }

  post {
    success { echo '✅ Middleware pipeline completed successfully' }
    failure { echo '❌ Middleware pipeline failed' }
  }
}
