package com.fitnesstracker;

import com.fitnesstracker.model.Goal;
import com.fitnesstracker.model.Workout;
import com.fitnesstracker.service.FitnessTrackerService;

/**
 * Main entry point for the Fitness Tracker application. Demonstrates logging workouts, tracking progress, and setting goals.
 */
public class Main {

    public static void main(String[] args) {
        FitnessTrackerService tracker = new FitnessTrackerService();

        // Log some workouts
        tracker.logWorkout(new Workout("Running", 30, 300.0, "2025-06-01"));
        tracker.logWorkout(new Workout("Cycling", 45, 400.0, "2025-06-02"));
        tracker.logWorkout(new Workout("Running", 20, 200.0, "2025-06-03"));
        tracker.logWorkout(new Workout("Weightlifting", 60, 250.0, "2025-06-03"));

        System.out.println("=== Fitness Tracker ===");
        System.out.println("Total workouts logged: " + tracker.getWorkoutCount());
        System.out.println("Total calories burned: " + tracker.getTotalCaloriesBurned());
        System.out.println("Total minutes worked out: " + tracker.getTotalWorkoutMinutes());

        System.out.println("\nRunning sessions:");
        tracker.getWorkoutsByType("Running").forEach(System.out::println);

        // --- Set a goal ---
        Goal calorieGoal = new Goal("Burn 1000 calories", 1000.0);
        calorieGoal.updateProgress(tracker.getTotalCaloriesBurned());
        tracker.addGoal(calorieGoal);

        Goal durationGoal = new Goal("Work out for 120 minutes", 120.0);
        durationGoal.updateProgress(tracker.getTotalWorkoutMinutes());
        tracker.addGoal(durationGoal);

        System.out.println("\n=== Goals ===");
        tracker.getAllGoals().forEach(g ->
                System.out.printf("%s — Progress: %.1f%% | Achieved: %b%n",
                        g.getDescription(), g.getProgressPercentage(), g.isAchieved())
        );
    }
}
