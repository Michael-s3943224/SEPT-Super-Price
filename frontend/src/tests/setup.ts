import { afterAll, afterEach, beforeAll } from 'vitest'
import { server } from './mocks/server'

beforeAll(() => {import.meta.env.NEXT_PUBLIC_API_URL = 'http://localhost:8080';server.listen({ onUnhandledRequest: 'error' });})
afterAll(() => server.close())
afterEach(() => server.resetHandlers())