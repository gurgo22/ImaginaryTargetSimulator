# Imaginary Target Simulator Application

The Target Simulator is a training tool designed for sport shooting enthusiasts, allowing users to simulate and analyze shooting practice sessions. This desktop application, developed with **JavaFX** and **SQLite**, features realistic target placement, score tracking, and a leaderboard to encourage skill development. This project demonstrates database integration, file handling, and GUI design principles.

---

## Features

### Place Shots on Target
- Users can simulate shots by placing them on an interactive target image.

### Shot Management
- Options to reset the target after each session or print a visual record of the target for analysis or offline review.

### User Registration and Authentication
- Users register and log in securely, with credentials stored in an encrypted format in the SQLite database.

### High Score Tracking
- Individual high scores are saved in the database, enabling users to track their personal bests over time.

### Leaderboard for Score Comparison
- The leaderboard displays usernames and scores in a sorted table format, allowing users to compare their scores with others.

### Data Persistence
- Scores and user data are saved between sessions using SQLite, ensuring that user progress is tracked even after the app closes.

### Data Encryption
- AES Encryption is built into the project. Passwords are encrypted for database operations.

---

## Technology Stack

- **JavaFX** – GUI framework for building the interactive application.
- **SQLite** – Embedded database to store user information and scores.
- **JUnit** – Testing framework used for ensuring functionality and data integrity.
