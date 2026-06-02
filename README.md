# Fitness Tracker — SDAT & DevOps QAP 1

A Java-based Fitness Tracker application that allows users to log workouts, track progress, and set fitness goals. Built with clean OOP principles, tested with JUnit 5, and configured with GitHub Actions for CI.

---

## Project Overview

This application demonstrates:
- **Workout logging** — track type, duration, calories, and date
- **Progress tracking** — total calories burned, total minutes, filter by type or date
- **Goal setting** — set targets, update progress, and check achievement status

---

## Clean Code Practices

### 1. Single Responsibility Principle
Each class has one clear job:
- `Workout.java` — only holds workout data and validates its own fields
- `Goal.java` — only manages goal state and progress calculation
- `FitnessTrackerService.java` — only coordinates business logic

```java
// Workout.java — validates its own input on construction
public Workout(String type, int durationMinutes, double caloriesBurned, String date) {
    if (type == null || type.isBlank()) {
        throw new IllegalArgumentException("Workout type cannot be null or blank.");
    }
    if (durationMinutes <= 0) {
        throw new IllegalArgumentException("Duration must be greater than zero.");
    }
    // ...
}
```

### 2. Meaningful Naming
All variables, methods, and classes use descriptive names with no abbreviations or ambiguous names:

```java
// Clear, self-documenting method names
public double getTotalCaloriesBurned() { ... }
public List<Workout> getWorkoutsByType(String type) { ... }
public List<Goal> getPendingGoals() { ... }
```

### 3. Guard Clauses for Input Validation
Instead of deeply nested if blocks, guard clauses will throw immediately on a bad input:

```java
public void logWorkout(Workout workout) {
    if (workout == null) {
        throw new IllegalArgumentException("Workout cannot be null.");
    }
    workouts.add(workout);
}
```

---

## Test Cases

The test file `FitnessTrackerTest.java` contains **13 unit tests** using JUnit 5:

| # | Test Name | Type |
|---|-----------|------|
| 1 | Log a workout and verify it is stored | Positive |
| 2 | Log multiple workouts and verify count | Positive |
| 3 | Logging a null workout throws exception | Negative |
| 4 | Creating a workout with blank type throws exception | Negative |
| 5 | Creating a workout with zero duration throws exception | Negative |
| 6 | Total calories burned is correctly summed | Positive |
| 7 | Total workout minutes are correctly summed | Positive |
| 8 | Filter workouts by type returns only matching workouts | Positive |
| 9 | Filter workouts by date returns only workouts on that date | Positive |
| 10 | No workouts logged returns zero calories | Edge Case |
| 11 | Goal is marked achieved when progress meets target | Positive |
| 12 | Goal is not achieved when progress is below target | Negative |
| 13 | Pending and achieved goals are correctly separated | Positive |

**Assertions used:** `assertEquals`, `assertTrue`, `assertFalse`, `assertThrows`, `stream().allMatch()`

---

## Dependencies

| Dependency | Version | Source |
|---|---|---|
| JUnit Jupiter (JUnit 5) | 5.10.0 | [Maven Central](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter) |
| Maven Surefire Plugin | 3.1.2 | [Maven Central](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-surefire-plugin) |

All dependencies are declared in `pom.xml` and automatically downloaded by Maven.

---

## GitHub Actions CI

The workflow file `.github/workflows/ci.yml` automatically runs all tests on:
- Every **push** to `main` or `dev`
- Every **pull request** targeting `main`

Steps in the pipeline:
1. Checkout the repo
2. Set up JDK 17
3. Cache Maven packages
4. Compile the project
5. Run all unit tests
6. Upload test results as artifacts

---

## How to Run Locally

```bash
# Run all tests
mvn test

# Compile and run the app
mvn compile exec:java -Dexec.mainClass="com.fitnesstracker.Main"
```

---

## Git Workflow

This project follows **trunk-based development**:
- `main` — stable, production-ready branch
- `dev` — active development branch
- Feature branches created from `dev`, merged back via Pull Request
- PRs trigger the GitHub Actions CI pipeline automatically
