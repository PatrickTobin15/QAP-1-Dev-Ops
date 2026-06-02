package com.fitnesstracker.model;

/**
 * Represents a single workout session logged by a user.
 */
public class Workout {

    private String type;         // e.g., "Running", "Cycling", "Weightlifting"
    private int durationMinutes; // Duration in minutes
    private double caloriesBurned;
    private String date;         // Format: YYYY-MM-DD

    public Workout(String type, int durationMinutes, double caloriesBurned, String date) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Workout type cannot be null or blank.");
        }
        if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration must be greater than zero.");
        }
        if (caloriesBurned < 0) {
            throw new IllegalArgumentException("Calories burned cannot be negative.");
        }
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Date cannot be null or blank.");
        }

        this.type = type;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = caloriesBurned;
        this.date = date;
    }

    // Getters

    public String getType() {
        return type;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("Workout{type='%s', duration=%d min, calories=%.1f, date='%s'}",
                type, durationMinutes, caloriesBurned, date);
    }
}
