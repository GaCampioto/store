<h1 align="center">Store Kafka</h1>
<h5 align="center">Simple project to try kafka</h5>

This project has some modules to `represent` microservices and the communication between then using kafka.

### Modules
- commons-database: Commons code to access database;
- commons-kafka: Commons code to create `consumers` and `producers`;
- email: Listen topic `store.new-order` and `store.send-email`;
- log: Log all messages sended to `store.*`;
- order: Listen topic `store.new-order` and save it in the database;
- report: Listen topic `store.create-report-for-user` and create a report for each user;
- user: 
  - Listen topic `store.new-order` and create a user, if it does not exists;
  - Listen topic `store.send-message-to-all-users` to send a command to `store.create-report-for-user`;
- producer: Create the first input which will be send to `store.new-order`, it would be something like an http endpoint that create an order.

### How to run it?
- First you need to start a `kafka` server in your local machine, which has to be located in `localhost:9092` as you can see in `KafkaReceiver.java`;
- Run the `Main` class in each module to create consumers and producers needed;
- Run the `MainProducer.java` to create first messages in `store.new-order`.

### Disclaimer
- Ignore the strategy used to simulate a local database and deal with `Exceptions`, the objective is try kafka and not protect yourself with sql injection and other defensive strategies.
- This project were created following the online trainning 
  - https://www.alura.com.br/curso-online-kafka-introducao-a-streams-em-microservicos 
  - https://www.alura.com.br/curso-online-kafka-cluster-de-microservicos
  - https://www.alura.com.br/curso-online-kafka-batches-correlation-ids-e-dead-letters
  - https://www.alura.com.br/curso-online-kafka-idempotencia-e-garantias
