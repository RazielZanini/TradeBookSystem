'use client'
import { useRouter } from "next/navigation";

export default function Home() {

  const router = useRouter();

  return (
    <>
      <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-r from-indigo-200 via-purple-200 to-pink-200 p-4">
        <header className="mb-10 text-center">
          <h1 className="text-5xl font-bold text-indigo-900">TradeBookSystem</h1>
          <p className="text-lg text-indigo-700 mt-2">Seu app de troca de livros</p>
        </header>

        <div className="bg-white rounded-2xl shadow-xl p-8 max-w-md w-full text-center">
          <img src="https://cdn-icons-png.flaticon.com/512/29/29302.png"
            alt="Logo de livro"
            className="w-24 h-24 mx-auto mb-4"
          />

          <h2 className="text-2xl font-semibold mb-2 text-gray-800">Bem vindo ao TradeBookSystem!</h2>
          <p className="text-gray-600 mb-6">
            Troque livro com outrsas pessoas, descubra novas histórias e compartilhe suas reviews com todo os usuários!
          </p>
          <div className="flex justify-center gap-4">
            <button className="bg-indigo-600 text-white px-6 py-2 rounded-xl hover:bg-indigo-700 transition"
              onClick={() => router.push('/login')}>
              Entrar
            </button>
            <button className="bg-gray-200 text-indigo-700 px-6 py-2 rounded-xl hover:bg-gray-300 transition"
              onClick={() => router.push('/register')}>
              Cadastrar
            </button>
          </div>
        </div>
      </div>
    </>
  );
}