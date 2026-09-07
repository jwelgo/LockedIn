package com.backend.backend.Services;

import com.backend.backend.DTOs.WorkoutMovementRequest;
import com.backend.backend.DTOs.WorkoutRequest;
import com.backend.backend.DTOs.WorkoutResponse;
import com.backend.backend.Entities.Workout;
import com.backend.backend.Entities.WorkoutMovement;
import com.backend.backend.Exceptions.ResourceNotFoundException;
import com.backend.backend.Repositories.MovementRepository;
import com.backend.backend.Repositories.WorkoutRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class WorkoutService {
    private final WorkoutRepository workoutRepo;
    private final MovementRepository movementRepo;

    public WorkoutService(WorkoutRepository workoutRepo, MovementRepository movementRepo) {
        this.workoutRepo = workoutRepo;
        this.movementRepo = movementRepo;
    }

    @Transactional
    public WorkoutResponse create(WorkoutRequest req) {
        Workout w = new Workout();
        w.setDate(req.date());
        w.setType(req.type());
        w.setNotes(req.notes());

        for (WorkoutMovementRequest wmReq : req.movements()) {
            WorkoutMovement wm = new WorkoutMovement();
            wm.setWorkout(w);
            wm.setMovement(movementRepo.getReferenceById(wmReq.movementId()));
            wm.setSortOrder(wmReq.sortOrder());
            wm.setSets(wmReq.sets());
            wm.setReps(wmReq.reps());
            wm.setWeight(wmReq.weight());
            w.getWorkoutMovements().add(wm);
        }

        return toResponse(workoutRepo.save(w));
    }

    public WorkoutResponse getById(Long id) {
        return workoutRepo.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Workout " + id + " not found"));
    }

    public Page<WorkoutResponse> list(Pageable pageable) {
        return workoutRepo.findAll(pageable).map(this::toResponse);
    }

    @Transactional
    public WorkoutResponse update(Long id, WorkoutRequest req) {
        Workout w = workoutRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Workout " + id + " not found"));
        w.setDate(req.date());
        w.setType(req.type());
        w.setNotes(req.notes());
        return toResponse(w);
    }

    @Transactional
    public void delete(Long id) {
        if (!workoutRepo.existsById(id)) throw new ResourceNotFoundException("Workout " + id + " not found");
        workoutRepo.deleteById(id);
    }

    @Transactional
    public WorkoutResponse addMovement(Long workoutId, WorkoutMovementRequest wmReq) {
        Workout w = workoutRepo.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout " + workoutId + " not found"));

        WorkoutMovement wm = new WorkoutMovement();
        wm.setWorkout(w);
        wm.setMovement(movementRepo.getReferenceById(wmReq.movementId()));
        wm.setSortOrder(wmReq.sortOrder());
        wm.setSets(wmReq.sets());
        wm.setReps(wmReq.reps());
        wm.setWeight(wmReq.weight());

        w.getWorkoutMovements().add(wm);
        return toResponse(w); // dirty checking + cascade handles the insert
    }

    @Transactional
    public void removeMovement(Long workoutId, Long workoutMovementId) {
        Workout w = workoutRepo.findById(workoutId)
                .orElseThrow(() -> new ResourceNotFoundException("Workout " + workoutId + " not found"));

        boolean removed = w.getWorkoutMovements()
                .removeIf(wm -> wm.getId().equals(workoutMovementId));

        if (!removed) throw new ResourceNotFoundException("Movement entry " + workoutMovementId + " not found on this workout");
        // orphanRemoval = true (set on Workout.workoutMovements) deletes the row on flush
    }

    private WorkoutResponse toResponse(Workout w) {
        return new WorkoutResponse(w.getId(), w.getDate(), w.getWorkoutMovements(), w.getType(), w.getNotes());
    }
}

