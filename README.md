# Queue System

Welcome to the Queue System project! This project simulates a queue management system in Java. It helps manage multiple queues, track clients, and simulate service times and waiting periods.

## Project Overview

The Queue System consists of two main classes:

- **`Queue`**: Manages a single queue with functionalities to handle clients, track processing times, and display queue status.
- **`QueueSystem`**: Oversees the entire system, including multiple queues, the waiting line, and client management.

## Class Details

### `Queue` Class

The `Queue` class provides functionalities to manage a queue:

- **Attributes**:
  - `serverName`: The name of the queue server.
  - `queueSize`: The maximum size of the queue.
  - `clientBeingServed`: The client currently being served.
  - `requestInProgress`: The request currently being processed.
  - `processingStartTime`: Start time of processing.
  - `clientsHistory`: Array of clients that have been served.
  - `clientsInQueue`: Array of clients currently in the queue.

- **Methods**:
  - Getters and setters for all attributes.
  - `toString()`: Returns a string representation of the queue showing the client being served and clients in the queue.
  - `toString(boolean showID)`: Returns a string representation with optional client IDs or remaining processing times.

### `QueueSystem` Class

The `QueueSystem` class manages the overall system:

- **Attributes**:
  - `clock`: Current time in the system.
  - `totalWaitingTime`: Accumulated waiting time for clients.
  - `clientsWorld`: Array of clients ready to enter the system.
  - `totalClientsInSystem`: Number of clients currently in the system.
  - `waitingLineSize`: Size of the waiting line.
  - `waitingLine`: Array of clients in the waiting line.
  - `tvInWaitingArea`: Indicates if there is a TV in the waiting area.
  - `coffeeInWaitingArea`: Indicates if there is coffee in the waiting area.
  - `queues`: Array of `Queue` objects representing different queues.

- **Methods**:
  - `increaseTime()`: Advances the system clock and processes clients based on the elapsed time.
  - `increaseTime(int time)`: Advances the system clock by a specified amount of time.
  - `getClientsBeingServed()`: Returns the clients currently being served.
  - `toString()`: Returns a string representation of the waiting line and all queues.
  - `toString(boolean showID)`: Returns a string representation with optional client IDs.

## Setup and Usage

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/queue-system.git
