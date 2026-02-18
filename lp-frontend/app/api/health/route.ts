import { NextResponse } from 'next/server';

/**
 * Health check endpoint for Docker health checks
 *
 * Used by Docker Compose to verify frontend is running
 */
export async function GET() {
  return NextResponse.json(
    {
      status: 'healthy',
      service: 'lp-frontend',
      timestamp: new Date().toISOString(),
    },
    { status: 200 }
  );
}
