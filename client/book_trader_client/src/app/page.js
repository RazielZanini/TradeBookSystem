'use client'

import api from "@/utils/api";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function Register() {

  const [user, setUser] = useState({});
  const [localData, setLocalData] = useState({})
  const route = useRouter();

  useEffect(() => {

    if (localData) {
      localStorage.setItem('user', JSON.stringify(localData))
    }

  }, [localData])

  async function createUser() {

    if (!user.password || !user.name) {
      alert('Preencha as informações para efetuar o cadastro!')
      return
    }

    try {
      const result = await api.post('/users', user)
      console.log(result.data)
      alert('Conta criada com sucesso')
      setLocalData(result.data)
      route.push('/home')
    } catch (error) {
      alert('Erro ao criar conta')
      console.log(error)
    }
  }

  return (
    <div className="w-full min-h-screen flex flex-col items-center justify-center">
      <div className="border border-white w-6/12 max-w-lg bg-slate-200 text-black rounded-md shadow-2xl p-6 flex flex-col items-center justify-center gap-4">
        <h1 className="font-medium text-2xl">Cadastre-se</h1>
        <div className="flex flex-col gap-2">
          <label htmlFor="input">Usuário</label>
          <input className="border-2 border-slate-400 rounded-md p-2"
            type="text"
            placeholder="Digite seu usuário"
            onChange={(e) => setUser({ ...user, name: e.target.value })}
            value={user.name || ''}
          />
          <label htmlFor="input">Senha</label>
          <input className="border-2 border-slate-400 rounded-md p-2"
            type="password"
            placeholder="Digite sua senha"
            onChange={(e) => setUser({ ...user, password: e.target.value })}
            value={user.password || ''}
          />
        </div>
        <div>
          <button className="bg-emerald-400 p-2 rounded-md hover:bg-emerald-500 cursor-pointer w-60"
            onClick={() => createUser()}
          >Cadastrar
          </button>
        </div>
      </div>
    </div>
  );
}