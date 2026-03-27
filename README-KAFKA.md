Kafka setup and quick test for Middleware

Prerequisites:
- Java 17
- Local Kafka running on localhost:9092 (you can use Docker or a local installation)

Build:

mvnw.cmd -DskipTests package

Run:

mvnw.cmd spring-boot:run

Test (publish message):

curl -X POST -H "Content-Type: application/json" -d "{\"id\":\"1\",\"payload\":\"hello from middleware\"}" http://localhost:8080/api/kafka/publish

Verify:
- The consumer logs should show the consumed message in the application logs.

Notes:
- The app creates the topic `test-topic` automatically using AdminClient if Kafka allows topic creation.
- If using Docker, ensure advertised.listeners is set so container can be reached at localhost:9092.
