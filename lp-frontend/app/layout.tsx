import type { Metadata } from "next";
import "./globals.css";

export const metadata: Metadata = {
  title: "LP Server - LoL Match Data Search",
  description: "League of Legends match data search and analysis service",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="ko">
      <body className="antialiased">
        {children}
      </body>
    </html>
  );
}
