import React from "react";

const WorkoutWeekCard = ({
  week,
  weekIndex,
  weeks,
  setWeeks,
  selectedRunner,
  handleWorkoutRegistration,
  handleUpdateWorkout,
}) => {
  const handleChange = (e, day) => {
    const updatedWeeks = [...weeks];
    updatedWeeks[weekIndex].workout[day] = e.target.value;
    setWeeks(updatedWeeks);
  };

  const handleLongRunChange = (e, field) => {
    const updatedWeeks = [...weeks];
    updatedWeeks[weekIndex][field] = e.target.value;
    setWeeks(updatedWeeks);
  };

  return (
    <div className="workout-week-card-content">
      {Object.entries(week.workout).map(([day, value]) => {
        if (["monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"].includes(day)) {
          return (
            <div key={day} className="workout-field">
              <label>{day.charAt(0).toUpperCase() + day.slice(1)}:</label>
              <input
                type="text"
                value={value}
                onChange={(e) => handleChange(e, day)}
                disabled={week.hasWorkout}
                className="workout-input"
              />
            </div>
          );
        }
        return null;
      })}

      <div className="workout-field">
        <label>Distância do Long Run (km):</label>
        <input
          type="number"
          value={week.longRunDistance}
          onChange={(e) => handleLongRunChange(e, "longRunDistance")}
          className="workout-input"
        />
      </div>

      <div className="workout-field">
        <label>Duração do Long Run (minutos):</label>
        <input
          type="time"
          step="1"
          value={week.longRunDurationMinutes || ""}
          onChange={(e) => handleLongRunChange(e, "longRunDurationMinutes")}
          className="workout-input"
        />
      </div>

      <div className="workout-field">
        <label>Pace Longão:</label>
        <input
          type="text"
          value={week.longRunPace || ""}
          readOnly
          className="workout-input"
        />
      </div>

      {!week.hasWorkout ? (
        <button
          onClick={() => handleWorkoutRegistration(week)}
          className="button workout-register-button"
        >
          Registrar Treino
        </button>
      ) : (
        <button
          onClick={() => handleUpdateWorkout(week)}
          className="button workout-update-button"
        >
          Atualizar Treino
        </button>
      )}
    </div>
  );
};

export default WorkoutWeekCard;