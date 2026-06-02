package com.fitnesstracker.service;

import com.fitnesstracker.model.Goal;
import com.fitnesstracker.model.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class that manages workout logs, progress tracking, and fitness goals. This is the core business logic layer of the Fitness Tracker application. */
public class FitnessTrackerService {

    private final List<Workout> workouts;
    private final List<Goal> goals;

    public FitnessTrackerService() {
        this.workouts = new ArrayList<>();
        this.goals = new ArrayList<>();
    }

    // Workout Methods

    /**
     * Logs a new workout session. */
    public void logWorkout(Workout workout) {
        if (workout == null) {
            throw new IllegalArgumentException("Workout cannot be null.");
        }
        workouts.add(workout);
    }

    /**
     * Returns all logged workouts. */
    public List<Workout> getAllWorkouts() {
        return new ArrayList<>(workouts);
    }

    /**
     * Returns all of the workouts of a specific type */
    public List<Workout> getWorkoutsByType(String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type cannot be null or blank.");
        }
        return workouts.stream()
                .filter(w -> w.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    /**
     * Returns the total amount of calories burned across all of the logged workouts. */
    public double getTotalCaloriesBurned() {
        return workouts.stream()
                .mapToDouble(Workout::getCaloriesBurned)
                .sum();
    }

    /**
     * Returns the total workout duration in minutes across all sessions. */
    public int getTotalWorkoutMinutes() {
        return workouts.stream()
                .mapToInt(Workout::getDurationMinutes)
                .sum();
    }

    /**
     * Returns workouts logged on a specific date. */
    public List<Workout> getWorkoutsByDate(String date) {
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Date cannot be null or blank.");
        }
        return workouts.stream()
                .filter(w -> w.getDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Returns the number of workouts logged. */
    public int getWorkoutCount() {
        return workouts.size();
    }

    // Goal Methods

    /**
     * Adds a new fitness goal. */
    public void addGoal(Goal goal) {
        if (goal == null) {
            throw new IllegalArgumentException("Goal cannot be null.");
        }
        goals.add(goal);
    }

    /**
     * Returns all goals. */
    public List<Goal> getAllGoals() {
        return new ArrayList<>(goals);
    }

    /**
     * Returns only achieved goals. */
    public List<Goal> getAchievedGoals() {
        return goals.stream()
                .filter(Goal::isAchieved)
                .collect(Collectors.toList());
    }

    /**
     * Returns only goals that have not yet been achieved. */
    public List<Goal> getPendingGoals() {
        return goals.stream()
                .filter(g -> !g.isAchieved())
                .collect(Collectors.toList());
    }
}
