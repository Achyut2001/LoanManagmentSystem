📄 Document Upload Service – README
📌 Overview

The Document Upload Service is a Spring Boot–based microservice that allows users to upload documents securely, store them in AWS S3, and publish document metadata events using Apache Avro for downstream services (like Document Verification).

🧱 Tech Stack

Backend: Java 8, Spring Boot, Spring Web, Spring Data JPA

Messaging: Kafka + Apache Avro

Database: PostgreSQL

Cloud: AWS S3, EC2, IAM

Build Tool: Maven

🏗️ High-Level Architecture

Flow:

Client uploads document via REST API

File stored in S3

Metadata saved in PostgreSQL

Event published to Kafka using Avro schema

Verification service consumes event

📂 Project Structure
document-upload-service
│── controller
│── service
│── repository
│── model
│── dto
│── kafka
│── avro
│── config
│── resources

📑 API – Document Upload

Endpoint

POST /api/documents/upload


Request (Multipart)

file

userId

documentType

Response

{
  "documentId": "DOC123",
  "status": "UPLOADED",
  "message": "Document uploaded successfully"
}

🧬 Apache Avro Schema Setup
1️⃣ Add Avro Dependency
<dependency>
  <groupId>org.apache.avro</groupId>
  <artifactId>avro</artifactId>
  <version>1.11.1</version>
</dependency>

2️⃣ Create Avro Schema

📁 src/main/resources/avro/DocumentUploaded.avsc

{
  "type": "record",
  "name": "DocumentUploaded",
  "namespace": "com.example.avro",
  "fields": [
    {"name": "documentId", "type": "string"},
    {"name": "userId", "type": "string"},
    {"name": "documentType", "type": "string"},
    {"name": "s3Url", "type": "string"},
    {"name": "status", "type": "string"}
  ]
}

3️⃣ Generate Avro Classes
mvn clean install


Generated classes will be available in:

target/generated-sources/avro

4️⃣ Kafka Producer Configuration
spring.kafka.producer.value-serializer=io.confluent.kafka.serializers.KafkaAvroSerializer
spring.kafka.properties.schema.registry.url=http://localhost:8081

☁️ AWS Setup (S3 + EC2)
🔐 Step 1: IAM User

Create IAM user

Attach policy: AmazonS3FullAccess

Generate Access Key & Secret Key

🪣 Step 2: Create S3 Bucket

Bucket name: document-upload-bucket

Region: ap-south-1

Disable public access

⚙️ Step 3: Application Configuration
aws.accessKey=YOUR_ACCESS_KEY
aws.secretKey=YOUR_SECRET_KEY
aws.region=ap-south-1
aws.s3.bucket=document-upload-bucket

🖥️ Step 4: Deploy on EC2

Launch EC2 (Amazon Linux)

Install Java 8

Copy JAR file

java -jar document-upload-service.jar


Open port 8080 in security group

🗄️ Database Setup
CREATE TABLE documents (
  id VARCHAR(50) PRIMARY KEY,
  user_id VARCHAR(50),
  document_type VARCHAR(30),
  s3_url TEXT,
  status VARCHAR(20),
  created_at TIMESTAMP
);

🔐 Security Considerations

File type validation (PDF/JPG/PNG)

Max upload size restriction

Pre-signed S3 URLs (optional)

Role-based access (JWT)

🚀 How to Run Locally
git clone <repo-url>
cd document-upload-service
mvn clean install
java -jar target/document-upload-service.jar

📌 Future Enhancements

Virus scan before upload

Async retry using Kafka DLQ

Encryption at rest in S3

Document versioning
