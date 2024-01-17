import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api": {
        target: "http://localhost:8080/", // met en commun le port de l'url du front avec le back pour éviter le CROSS
        changeOrigin: true,
      },
    },
  },
});
