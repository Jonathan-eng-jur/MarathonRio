// components/RegisterForm.js
import React, { useState } from "react";
import { validateName, validateEmail } from "../utils/validations";

const RegisterForm = ({ onSubmit, onCancel }) => {
  const [newRunner, setNewRunner] = useState({ name: "", email: "" });
  const [error, setError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!validateName(newRunner.name)) return setError("Nome inválido.");
    if (!validateEmail(newRunner.email)) return setError("E-mail inválido.");
    onSubmit(newRunner);
  };

  return (
    <div className="register-form">
      <h2>Cadastrar Usuário</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Nome:</label>
          <input
            type="text"
            value={newRunner.name}
            onChange={(e) => setNewRunner({ ...newRunner, name: e.target.value })}
            className="input-field"
          />
        </div>
        <div>
          <label>Email:</label>
          <input
            type="email"
            value={newRunner.email}
            onChange={(e) => setNewRunner({ ...newRunner, email: e.target.value })}
            className="input-field"
          />
        </div>
        {error && <p className="error">{error}</p>}
        <button type="submit" className="button">
          Cadastrar
        </button>
        <button type="button" onClick={onCancel} className="button-cancel">
          Cancelar
        </button>
      </form>
    </div>
  );
};

export default RegisterForm;