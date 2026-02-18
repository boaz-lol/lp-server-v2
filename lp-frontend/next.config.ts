import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  // Standalone output for Docker optimization
  output: "standalone",

  // Environment variables
  env: {
    NEXT_PUBLIC_API_URL: process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080",
  },

  // Image optimization
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: "ddragon.leagueoflegends.com",
        pathname: "/**",
      },
    ],
  },

  // Enable React strict mode
  reactStrictMode: true,
};

export default nextConfig;
