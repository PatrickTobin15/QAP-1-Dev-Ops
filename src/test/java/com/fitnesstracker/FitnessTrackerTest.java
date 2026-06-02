package com.fitnesstracker;

import com.fitnesstracker.model.Goal;
import com.fitnesstracker.model.Workout;
import com.fitnesstracker.service.FitnessTrackerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Fitness Tracker application. Covers workout logging, progress tracking, goal management, and input validation (positive and negative scenarios). */
class FitnessTrackerTest {

    private FitnessTrackerService tracker;

    @BeforeEach
    void setUp() {
        tracker = new FitnessTrackerService();
    }

    // Workout Logging Tests

    @Test
    @DisplayName("Log a workout and verify it is stored")
    void testLogWorkout_Success() {
        Workout workout = new Workout("Running", 30, 300.0, "2025-06-01");
        tracker.logWorkout(workout);

        assertEquals(1, tracker.getWorkoutCount());
        assertEquals("Running", tracker.getAllWorkouts().get(0).getType());
    }

    @Test
    @DisplayName("Logs multiple workouts and will verify the count")
    void testLogMultipleWorkouts() {
        tracker.logWorkout(new Workout("Running", 30, 300.0, "2025-06-01"));
        tracker.logWorkout(new Workout("Cycling", 45, 400.0, "2025-06-02"));
        tracker.logWorkout(new Workout("Weightlifting", 60, 250.0, "2025-06-03"));

        assertEquals(3, tracker.getWorkoutCount());
    }

    @Test
    @DisplayName("Logging a null workout throws IllegalArgumentException")
    void testLogWorkout_NullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> tracker.logWorkout(null));
    }

    @Test
    @DisplayName("Creating a workout with blank type throws IllegalArgumentException")
    void testWorkout_BlankTypeThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Workout("", 30, 300.0, "2025-06-01"));
    }

    @Test
    @DisplayName("Creating a workout with zero duration throws IllegalArgumentException")
    void testWorkout_ZeroDurationThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Workout("Running", 0, 300.0, "2025-06-01"));
    }

    // Progress Tracking Tests

    @Test
    @DisplayName("Total calories burned is correctly summed")
    void testGetTotalCaloriesBurned() {
        tracker.logWorkout(new Workout("Running", 30, 300.0, "2025-06-01"));
        tracker.logWorkout(new Workout("Cycling", 45, 400.0, "2025-06-02"));

        assertEquals(700.0, tracker.getTotalCaloriesBurned(), 0.001);
    }

    @Test
    @DisplayName("Total workout minutes are correctly summed")
    void testGetTotalWorkoutMinutes() {
        tracker.logWorkout(new Workout("Running", 30, 300.0, "2025-06-01"));
        tracker.logWorkout(new Workout("Cycling", 45, 400.0, "2025-06-02"));

        assertEquals(75, tracker.getTotalWorkoutMinutes());
    }

    @Test
    @DisplayName("Filter workouts by type returns only matching workouts")
    void testGetWorkoutsByType() {
        tracker.logWorkout(new Workout("Running", 30, 300.0, "2025-06-01"));
        tracker.logWorkout(new Workout("Cycling", 45, 400.0, "2025-06-02"));
        tracker.logWorkout(new Workout("Running", 20, 200.0, "2025-06-03"));

        List<Workout> runningWorkouts = tracker.getWorkoutsByType("Running");

        assertEquals(2, runningWorkouts.size());
        assertTrue(runningWorkouts.stream().allMatch(w -> w.getType().equalsIgnoreCase("Running")));
    }

    @Test
    @DisplayName("The filter does workouts by the date and returns only the workouts within that date")
    void testGetWorkoutsByDate() {
        tracker.logWorkout(new Workout("Running", 30, 300.0, "2025-06-01"));
        tracker.logWorkout(new Workout("Cycling", 45, 400.0, "2025-06-01"));
        tracker.logWorkout(new Workout("Weightlifting", 60, 250.0, "2025-06-03"));

        List<Workout> june1Workouts = tracker.getWorkoutsByDate("2025-06-01");

        assertEquals(2, june1Workouts.size());
    }

    @Test
    @DisplayName("No workouts logged returns zero calories")
    void testTotalCalories_NoWorkouts() {
        assertEquals(0.0, tracker.getTotalCaloriesBurned(), 0.001);
    }

    // Goal Setting Tests

    @Test
    @DisplayName("The Goal is marked down as achieved when progress meets the target")
    void testGoal_MarkedAchievedWhenTargetMet() {
        Goal goal = new Goal("Burn 500 calories", 500.0);
        goal.updateProgress(500.0);

        assertTrue(goal.isAchieved());
    }

    @Test
    @DisplayName("Goal is not achieved when the progress is below the target")
    void testGoal_NotAchievedBelowTarget() {
        Goal goal = new Goal("Burn 500 calories", 500.0);
        goal.updateProgress(300.0);

        assertFalse(goal.isAchieved());
        assertEquals(60.0, goal.getProgressPercentage(), 0.001);
    }

    @Test
    @DisplayName("Pending and achieved goals are correctly separated")
    void testGetAchievedAndPendingGoals() {
        Goal achieved = new Goal("Run 30 minutes", 30.0);
        achieved.updateProgress(30.0);

        Goal pending = new Goal("Burn 1000 calories", 1000.0);
        pending.updateProgress(200.0);

        tracker.addGoal(achieved);
        tracker.addGoal(pending);

        assertEquals(1, tracker.getAchievedGoals().size());
        assertEquals(1, tracker.getPendingGoals().size());
    }
}
