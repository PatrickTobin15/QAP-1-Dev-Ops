package com.fitnesstracker.model;

/**
 * Represents a fitness goal set by a user. */
public class Goal {

    private String description;
    private double targetValue;   // e.g., 500 calories, 60 minutes
    private double currentValue;
    private boolean achieved;

    public Goal(String description, double targetValue) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Goal description cannot be null or blank.");
        }
        if (targetValue <= 0) {
            throw new IllegalArgumentException("Target value must be greater than zero.");
        }

        this.description = description;
        this.targetValue = targetValue;
        this.currentValue = 0;
        this.achieved = false;
    }

    /**
     * Updates progress toward the goal by adding the given amount. Marks the goal as achieved if currentValue meets or exceeds targetValue.
     */
    public void updateProgress(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Progress amount cannot be negative.");
        }
        this.currentValue += amount;
        if (this.currentValue >= this.targetValue) {
            this.achieved = true;
        }
    }

    /**
     * Returns the percentage of progress toward the goal (capped at 100%).
     */
    public double getProgressPercentage() {
        double percentage = (currentValue / targetValue) * 100;
        return Math.min(percentage, 100.0);
    }

    // --- Getters ---

    public String getDescription() {
        return description;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public boolean isAchieved() {
        return achieved;
    }

    @Override
    public String toString() {
        return String.format("Goal{description='%s', progress=%.1f/%.1f, achieved=%b}",
                description, currentValue, targetValue, achieved);
    }
}
