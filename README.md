# Queue System

Welcome to the Queue System project! This project simulates a comprehensive queue management system in Java. It helps manage multiple queues, track clients, and simulate service times, waiting periods, and prioritize VIP clients.

## Project Overview

The Queue System is composed of the following core components:

- **`Queue`**: Manages individual queues, handles clients, tracks processing times, and displays queue status.
- **`QueueSystem`**: Oversees the entire system, managing multiple queues, waiting lines, client management, and processing time.
- **`Client`**: Represents a client with attributes such as arrival time, patience, and requests.
- **`VIPClient`**: A specialized `Client` with priority levels and membership information, enabling prioritized service.

## Recent Updates

1. **Switched to Dynamic Data Structures**: We’ve transitioned from fixed-size arrays to dynamic lists (`List<Client>`, `List<Request>`) across various classes for better scalability and flexibility.
2. **VIP Client Functionality**: The `VIPClient` class has been added, implementing a priority system and membership tracking for clients with special service levels.
   - **Priority Adjustments**: VIP clients can now have their priority adjusted dynamically based on tenure or other factors.
3. **Enhanced Queue Management**: The queue system has been overhauled to better handle client processing and prioritization. This includes:
   - Dynamic addition/removal of clients from queues.
   - Automatic handling of queue prioritization for VIP clients.
4. **Improved `toString()` Methods**: String representations of both the queue and queue system are now more efficient, utilizing `StringBuilder` for better performance and readability.
5. **Modularized Service Level Calculation**: The service level estimation has been streamlined to reduce redundancy and improve maintainability.

## Class Details

### `Queue` Class

The `Queue` class handles individual queue management:

- **Attributes**:
  - `serverName`: The name of the server managing the queue.
  - `queueSize`: The maximum number of clients in the queue.
  - `clientBeingServed`: The client currently being served.
  - `requestInProgress`: The current request being processed.
  - `processingStartTime`: The time at which processing started.
  - `clientsHistory`: A list of clients who have been served.
  - `clientsInQueue`: A list of clients currently in the queue.

- **Methods**:
  - Getters and setters for all attributes.
  - Queue management (e.g., adding/removing clients from the queue).
  - `toString()`: Returns a string representation showing the server, the client being served, and clients in the queue.
  - `toString(boolean showID)`: Optionally shows client IDs or remaining processing times.

### `QueueSystem` Class

The `QueueSystem` class manages the overall queue system, with multiple queues and a waiting line:

- **Attributes**:
  - `clock`: The current time in the system.
  - `totalWaitingTime`: The accumulated waiting time of all clients.
  - `clientsWorld`: A list of clients ready to enter the system.
  - `totalClientsInSystem`: The total number of clients in the system.
  - `waitingLine`: A list of clients currently in the waiting line.
  - `tvInWaitingArea`: Indicates if there is a TV in the waiting area to increase client patience.
  - `coffeeInWaitingArea`: Indicates if there is coffee in the waiting area to increase client patience.
  - `queues`: A list of `Queue` objects representing the different queues.

- **Methods**:
  - `increaseTime()`: Advances the system clock by one unit and processes clients accordingly.
  - `increaseTime(int time)`: Advances the system clock by a specified amount of time.
  - `getClientsBeingServed()`: Returns the clients currently being served across all queues.
  - `toString()`: Returns a string representation of the waiting line and all queues.
  - `toString(boolean showID)`: Optionally shows client IDs in the string representation.

### `Client` Class

The `Client` class represents a client in the system:

- **Attributes**:
  - Personal information such as `firstName`, `lastName`, `yearOfBirth`, and `gender`.
  - Client-specific data including `arrivalTime`, `waitingTime`, `serviceTime`, `departureTime`, `patience`.
  - Requests the client makes during their time in the system.

- **Methods**:
  - Getters and setters for all attributes.
  - `estimateServiceLevel()`: Estimates the client’s service satisfaction based on waiting time and queue time.
  - `toString()`: Returns a string representation of the client’s details, including service level and server info.

### `VIPClient` Class

The `VIPClient` class extends the `Client` class, adding additional functionality for prioritized service:

- **Attributes**:
  - `memberSince`: The year the client became a VIP.
  - `priority`: The priority level of the client, which can be dynamically adjusted.
  
- **Methods**:
  - `adjustPriorityBasedOnTenure()`: Adjusts the priority of the VIP client based on the number of years they’ve been a member.
  - `toString()`: Returns a detailed string representation of the VIP client, including their VIP status and priority.

## Setup and Usage

1. **Clone the Repository**

   ```bash
   git clone https://github.com/ayahesham1/queue-system.git
