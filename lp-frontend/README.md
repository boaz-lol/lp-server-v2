# LP Frontend

Next.js frontend for LoL Match Data Search Service

## Tech Stack

- **Framework**: Next.js 15 (App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **Deployment**: Docker (Standalone Output)

## Development

```bash
# Install dependencies
npm install

# Run development server
npm run dev
```

Open [http://localhost:3000](http://localhost:3000)

## Environment Variables

Create `.env.local` for development:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080
```

In Docker, this is automatically set to `http://lp-api:8080`

## Build

```bash
# Build for production
npm run build

# Run production build locally
npm start
```

## Docker

The app is configured with `output: "standalone"` for optimized Docker builds.

See `/lp-frontend/Dockerfile` for the multi-stage build configuration.

## Health Check

- Development: `http://localhost:3000/api/health`
- Docker: `http://localhost:3000/api/health`

## API Client

API client is located at `/lib/api.ts` and uses the `NEXT_PUBLIC_API_URL` environment variable.

Example usage:

```typescript
import { api } from '@/lib/api';

const response = await api.getSummoner('Hide on bush', 'KR');
```
