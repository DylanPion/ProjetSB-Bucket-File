import instance from "../api/http";

// Connexion
export const Authenticate = (data) => {
  return instance.post("auth/login", data);
};

// Inscription
export const CreateAccount = (data) => {
  return instance.post("/auth/signup", data);
};

// Création d'un Bucket
export const CreateBucket = (data) => {
  return instance.post("/bucket");
};
