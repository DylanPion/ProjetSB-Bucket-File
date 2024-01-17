import axios from "axios";

// Code qui créer un intercepteurs. Qui va intercepter la requête pour lui affecté des "conditions".

// Récupère le token
function getLocalAccessToken() {
  const accessToken = window.localStorage.getItem("user-token");
  return accessToken;
}
// Create crée une instance du clientHTTP
const instance = axios.create({
  baseURL: "/api",
  headers: {
    "Content-Type": "application/json",
  },
});

// Intercepte chaque requête sortante avant qu'elle ne soit envoyée pour lui ajouter un token
instance.interceptors.request.use(
  (config) => {
    const token = getLocalAccessToken();
    if (token) {
      config.headers["Authorization"] = "Bearer " + token;
    }
    console.log(config.headers["Authorization"]);
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default instance;
