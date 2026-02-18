export default function Home() {
  return (
    <div className="min-h-screen flex flex-col items-center justify-center p-8">
      <main className="max-w-4xl w-full space-y-8">
        <div className="text-center space-y-4">
          <h1 className="text-4xl font-bold">
            LP Server
          </h1>
          <p className="text-xl text-gray-600 dark:text-gray-400">
            League of Legends Match Data Search Service
          </p>
        </div>

        <div className="bg-white dark:bg-gray-800 rounded-lg shadow-lg p-8 space-y-4">
          <h2 className="text-2xl font-semibold mb-4">프로젝트 상태</h2>
          <div className="space-y-2">
            <StatusItem status="success" text="✅ Next.js Frontend" />
            <StatusItem status="pending" text="⏳ Kotlin Backend API" />
            <StatusItem status="pending" text="⏳ Python ML Model" />
            <StatusItem status="pending" text="⏳ Docker Compose" />
          </div>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <Card
            title="API Server"
            description="Spring Boot REST API"
            port="8080"
          />
          <Card
            title="Admin Server"
            description="관리자 백오피스"
            port="8081"
          />
          <Card
            title="Batch Server"
            description="데이터 수집 & 처리"
            port="8082"
          />
          <Card
            title="Python Model"
            description="ML 예측 모델"
            port="5000"
          />
        </div>
      </main>
    </div>
  );
}

function StatusItem({ status, text }: { status: 'success' | 'pending'; text: string }) {
  return (
    <div className="flex items-center gap-2">
      <span className={status === 'success' ? 'text-green-500' : 'text-yellow-500'}>
        {text}
      </span>
    </div>
  );
}

function Card({ title, description, port }: { title: string; description: string; port: string }) {
  return (
    <div className="bg-gray-50 dark:bg-gray-700 rounded-lg p-6 space-y-2">
      <h3 className="text-lg font-semibold">{title}</h3>
      <p className="text-sm text-gray-600 dark:text-gray-300">{description}</p>
      <p className="text-xs text-gray-500 dark:text-gray-400">Port: {port}</p>
    </div>
  );
}
