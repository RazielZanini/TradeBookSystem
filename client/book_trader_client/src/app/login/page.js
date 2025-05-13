'use client'
import Header from "@/components/infra/header";
import api from "@/utils/api"
import { useEffect, useState } from "react"

export default function login() {

  const [loginData, setLoginData] = useState({
    email: '',
    password: ''
  })

  const sendLogin = async () => {

    try {
      const response = await api.post('/auth/login', loginData)
      localStorage.setItem('token', response.data.token)
    } catch (error) {
      console.log("Erro ao realizar login", error);
    }

  };

  return (
    <>
      <Header />
      <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-r from-indigo-200 via-purple-200 to-pink-200 p-4">
        <div className="bg-white rounded-2xl shadow-xl p-8 max-w-md w-full text-center text-black">
          <h1 className="font-bold text-3xl mb-6 text-indigo-900">Entrar</h1>

          <div className="flex flex-col gap-4 text-left">
            <div>
              <label className="block text-sm font-medium text-gray-700">Email</label>
              <input type="email"
                className="border-2 border-slate-300 rounded-md p-2 w-full"
                placeholder="Informe seu email"
                onChange={(e) => setLoginData({ ...loginData, email: e.target.value })}
                value={loginData.email}
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Senha</label>
              <input
                className="border-2 border-slate-300 rounded-md p-2 w-full"
                type="password"
                placeholder="Informe sua senha"
                onChange={(e) => setLoginData({ ...loginData, password: e.target.value })}
                value={loginData.password} />
            </div>

            <div className="mt-6">
              <button
                disabled={!loginData.email || !loginData.password}
                className={`p-2 rounded-md w-full transition ${!loginData.email || !loginData.password ?
                  `bg-gray-300 cursor-not-allowed` : `bg-indigo-600 text-white hover:bg-indigo-700`}`}
                onClick={() => sendLogin()}
              >Entrar</button>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}