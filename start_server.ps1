# Start the server in a separate process
$env:SPRING_DATASOURCE_URL='jdbc:mysql://student-system-db.cfi8mm4iog6d.us-west-2.rds.amazonaws.com:3306/student_system_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true'
$env:SPRING_DATASOURCE_USERNAME='admin'
$env:SPRING_DATASOURCE_PASSWORD='Checkpoint_BNMIT*26'
$env:SPRING_DATASOURCE_DRIVER_CLASS_NAME='com.mysql.cj.jdbc.Driver'
$env:SPRING_JPA_HIBERNATE_DDL_AUTO='update'
$env:SPRING_JPA_DATABASE_PLATFORM='org.hibernate.dialect.MySQLDialect'
$env:JWT_SECRET_KEY='4v8y/B?E(H+MbQeThWmZq3t6w9z$C&F)J@NcRfUjXn2r5u7x!A%D*G-KaPdSg'
$env:SERVER_PORT='5000'

$jarPath = "s:\CHECKPOINT\new approach\server\server\target\server-0.0.1-SNAPSHOT.jar"
$javaPath = "java"

# Start the process without waiting
Start-Process -FilePath $javaPath -ArgumentList "-jar", $jarPath -WindowStyle Hidden -PassThru
Write-Host "Server started in background. Waiting 10 seconds for initialization..."
Start-Sleep -Seconds 10
Write-Host "Server should be ready. Testing endpoint..."
Invoke-WebRequest -Uri "http://localhost:5000/api/v1/courses" -UseBasicParsing 2>&1
