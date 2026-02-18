/**
 * API Client
 *
 * Uses environment variable for API URL
 * In Docker: NEXT_PUBLIC_API_URL=http://lp-api:8080
 * In development: NEXT_PUBLIC_API_URL=http://localhost:8080
 */

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

export interface ApiResponse<T> {
  success: boolean;
  data?: T;
  error?: {
    code: string;
    message: string;
    details?: string;
  };
  timestamp: string;
}

/**
 * Base fetch wrapper with error handling
 */
async function fetchApi<T>(
  endpoint: string,
  options?: RequestInit
): Promise<ApiResponse<T>> {
  try {
    const response = await fetch(`${API_BASE_URL}${endpoint}`, {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...options?.headers,
      },
    });

    if (!response.ok) {
      const error = await response.json();
      return {
        success: false,
        error: error.error || {
          code: response.status.toString(),
          message: response.statusText,
        },
        timestamp: new Date().toISOString(),
      };
    }

    const data = await response.json();
    return data;
  } catch (error) {
    return {
      success: false,
      error: {
        code: 'NETWORK_ERROR',
        message: error instanceof Error ? error.message : 'Network error occurred',
      },
      timestamp: new Date().toISOString(),
    };
  }
}

/**
 * Summoner data type
 */
export interface SummonerData {
  id: number;
  puuid: string;
  summonerName: string;
  profileIconId: number;
  summonerLevel: number;
  region: string;
}

/**
 * Match data type
 */
export interface MatchData {
  matchId: string;
  queueType: string;
  gameCreation: string;
  gameDuration: number;
}

/**
 * API Methods
 */
export const api = {
  /**
   * Get summoner by name
   */
  async getSummoner(summonerName: string, region: string = 'KR') {
    return fetchApi<SummonerData>(`/api/summoners/${summonerName}?region=${region}`);
  },

  /**
   * Get match history
   */
  async getMatches(puuid: string, start: number = 0, count: number = 20) {
    return fetchApi<MatchData[]>(`/api/matches/${puuid}?start=${start}&count=${count}`);
  },

  /**
   * Health check
   */
  async healthCheck() {
    return fetchApi<{ status: string }>('/actuator/health');
  },
};
