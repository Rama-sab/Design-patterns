

##  Project Description

This project implements a modular event-driven notification system designed to handle real-time notifications similar to systems like Google Calendar. The system allows multiple entities (users, services, components) to publish events, subscribe to specific event types, and receive notifications when those events occur.

The system serves as backend logic for an extensible event infrastructure that can be used by different teams or modules in a  distributed environment.

##  System Design

### Core Architecture

The system follows an **Observer Pattern** combined with **Event-Driven Architecture** principles:

- **Event Publisher**: Components that generate and publish events
- **Event Subscriber**: Components that listen for specific event types
- **Event Manager**: Central coordinator that manages subscriptions and dispatches events
- **Event Store**: In-memory storage for event history and retrieval

### Key Components

1. **Event System**
   - Abstract event base class
   - Specific event types (TaskEvent, TimeBasedEvent, etc.)
   - Event metadata (timestamp, priority, source)

2. **Subscription Management**
   - Subscriber registration and management
   - Event type filtering
   - Personalized subscription preferences

3. **Notification Dispatcher**
   - Asynchronous event delivery
   - Multi-threaded event processing
   - Error handling and retry mechanisms

4. **Event History & Reporting**
   - Event storage and retrieval
   - Stream-based filtering and grouping
   - Historical analysis capabilities

## Supported Use Cases

###  Task Alerts
- Subscribe to new task notifications
- Automatic notification delivery to all relevant subscribers
- Event logging and audit trail

###  Time-Based Event Triggers
- Scheduled events at configurable intervals
- Multi-threaded parallel event processing
- Heartbeat and reminder functionality

###  Personalized Subscriptions
- Priority-based filtering (high, medium, low)
- Time-based filtering (work hours, specific periods)
- Custom subscriber preferences

###  Event History and Reporting
- Query events by time range
- Group events by type, priority, or timestamp
- Stream-based data processing for analytics

##  Technologies Used

- **Java 17+** - Core programming language
- **Maven** - Build and dependency management
- **JUnit 5** - Unit testing framework
- **Java Collections & Generics** - Data structures
- **Java 8+ Features**:
  - Streams and Lambda expressions
  - Optional for null safety
  - LocalDateTime for time handling
- **Multithreading/Concurrency** - Parallel event processing
- **Design Patterns** -  singleton, MVC and observer  pattern

## 🚀 How to Run the Application

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Git

### Setup and Execution

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Design-patterns
   ```

2. **Compile the project**
   ```bash
   mvn clean compile
   ```

3. **Run the application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.mycompany.app.controller.Main"
   ```

### Alternative Run Methods

- **Using Maven wrapper** (if included):
  ```bash
  ./mvnw clean compile
  ./mvnw exec:java -Dexec.mainClass="com.mycompany.app.controller.Main"
  ```

- **Direct Java execution** (after compilation):
  ```bash
  java -cp target/classes com.mycompany.app.controller.Main
  ```

## How to Run Tests

### Run all tests
```bash
mvn test
```

### Run specific test class
```bash
mvn test -Dtest=EventManagerTest
```

### Run tests with coverage report
```bash
mvn test jacoco:report
```

### Generate test reports
```bash
mvn surefire-report:report
```



## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

##  Documentation

For detailed API documentation and design decisions, see:
- [API Documentation](docs/api.md)
- [Design Decisions](docs/design.md)
- [Architecture Overview](docs/architecture.md)
