import instance from "../api/http";

// Upload Fichier
export const AddFile = (data) => {
  return instance.post(`files/save/`, data);
};
