import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      "/api": {
        target:
          process.env.VITE_API_BASE_URL ||
          "https://testdeployevent.onrender.com", // địa chỉ backend Spring Boot của bạn
        changeOrigin: true,
        secure: true,
      },
    },
  },
  build: {
    outDir: "dist",
    assetsDir: "assets",
    sourcemap: false,
  },
});
