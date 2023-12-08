/// <reference types="vitest" />
import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'

export default defineConfig({
  // plugins: [require("@import-meta-env/unplugin").webpack({
  //   env: ".env",
  //   example: ".env.example",
  //   transformMode: "compile-time",
  //   }),react()
  // ],
  test: {
    globals: true,
    environment: 'happy-dom',
    setupFiles: ['./src/tests/setup.ts']
  },
}) 

