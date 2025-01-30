package com.marathon.riodejaneiro.service;

import com.marathon.riodejaneiro.model.Runner;
import com.marathon.riodejaneiro.model.TrainingWeek;
import com.marathon.riodejaneiro.model.Workout;
import com.marathon.riodejaneiro.repository.RunnerRepository;
import com.marathon.riodejaneiro.repository.TrainingWeekRepository;
import com.marathon.riodejaneiro.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private RunnerRepository runnerRepository;

    @Autowired
    private TrainingWeekRepository trainingWeekRepository;

    public Workout save(String email, Workout workout) {
        Runner runner = runnerRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Runner not found with email: " + email));
        workout.setRunner(runner);

        if (workout.getTrainingWeek() != null) {
            TrainingWeek trainingWeek = workout.getTrainingWeek();
            trainingWeek.setWorkout(workout); // Configura o lado bidirecional
        }

        if (workout.getLongRunDistance() > 0 && workout.getLongRunDurationMinutes() != null) {
            LocalTime pace = calculatePace(workout.getLongRunDistance(), workout.getLongRunDurationMinutes());
            workout.setLongRunPace(pace);
        }

        return workoutRepository.save(workout);
    }

    public LocalTime calculatePace(double distance, LocalTime duration) {
        if (distance <= 0) {
            throw new IllegalArgumentException("A distância deve ser maior que zero");
        }

        long totalSeconds = duration.getHour() * 3600 + duration.getMinute() * 60 + duration.getSecond();

        double secondsPerKm = (double) totalSeconds / distance;

        long hours = (long) (secondsPerKm / 3600);
        long minutes = (long) ((secondsPerKm % 3600) / 60);
        long seconds = (long) (secondsPerKm % 60);

        return LocalTime.of((int) hours, (int) minutes, (int) seconds);
    }

    public List<Workout> getAllWorkoutsForRunner(String email) {
        return workoutRepository.findByRunnerEmail(email);
    }

    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    public Workout updateWorkout(String email, int week, double distance, LocalTime duration, TrainingWeek trainingWeek) {
        Workout workout = workoutRepository.findByRunnerEmailAndWeek(email, week);

        workout.setLongRunDistance(distance);
        workout.setLongRunDurationMinutes(duration);
        workout.setTrainingWeek(trainingWeek);

        if (workout.getLongRunDistance() > 0 && workout.getLongRunDurationMinutes() != null) {
            LocalTime pace = calculatePace(workout.getLongRunDistance(), workout.getLongRunDurationMinutes());
            workout.setLongRunPace(pace);
        }
        return workoutRepository.save(workout);
    }
}