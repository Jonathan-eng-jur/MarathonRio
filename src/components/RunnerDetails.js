import React, { useState } from "react";
import WorkoutWeekCard from "./WorkoutWeekCard";

const RunnerDetails = ({
  selectedRunner,
  weeks,
  setWeeks,
  handleWorkoutRegistration,
  handleUpdateWorkout,
}) => {
  const [expandedWeeks, setExpandedWeeks] = useState([]);

  const toggleWeekExpand = (weekNumber) => {
    setExpandedWeeks((prevExpandedWeeks) =>
      prevExpandedWeeks.includes(weekNumber)
        ? prevExpandedWeeks.filter((week) => week !== weekNumber)
        : [...prevExpandedWeeks, weekNumber]
    );
  };

  return (
    <div className="runner-details">
      <h3>Detalhes do Corredor</h3>
      <p>
        <strong>Nome:</strong> {selectedRunner.name || "Não informado"}
      </p>
      <p>
        <strong>Email:</strong> {selectedRunner.email}
      </p>
      <h4>Semanas de Treino:</h4>
      <div className="training-weeks">
        {weeks.map((week, weekIndex) => (
          <div key={week.week} className="workout-week-card">
            <div className="week-header">
              <strong>Semana {week.week}</strong>
              <button
                onClick={() => toggleWeekExpand(week.week)}
                className="expand-button"
              >
                {expandedWeeks.includes(week.week) ? "Fechar" : "Abrir"}
              </button>
            </div>

            <div className="week-status">
              {week.hasWorkout ? (
                <p className="status-registered">Treino cadastrado</p>
              ) : (
                <p className="status-not-registered">Nenhum treino cadastrado</p>
              )}
            </div>

            {expandedWeeks.includes(week.week) && (
              <div className="week-workout">
                <WorkoutWeekCard
                  week={week}
                  weekIndex={weekIndex}
                  weeks={weeks}
                  setWeeks={setWeeks}
                  selectedRunner={selectedRunner}
                  handleWorkoutRegistration={handleWorkoutRegistration}
                  handleUpdateWorkout={handleUpdateWorkout}
                />
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default RunnerDetails;
