'use client'

import api from "@/utils/api";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";

export default function Register() {

  const [user, setUser] = useState({
    email: '',
    name: '',
    password: '',
    confirmPassword: ''
  });
  const [localData, setLocalData] = useState({});
  const route = useRouter();
  const [difPass, setDifPass] = useState(true);

  useEffect(() => {
    if (localData) {
      localStorage.setItem('user', JSON.stringify(localData));
    }
  }, [localData]);

  useEffect(() => {
    if (!user.password || !user.confirmPassword) {
      setDifPass(true);
      return;
    }

    setDifPass(user.password !== user.confirmPassword);
  }, [user.confirmPassword, user.password]);

  async function createUser() {
    if (!user.password || !user.email) {
      alert('Preencha as informações para efetuar o cadastro!');
      return;
    }

    try {
      const result = await api.post('/auth/register', user);
      console.log(result.data);
      alert('Conta criada com sucesso');
      setLocalData(result.data);
      route.push('/home');
    } catch (error) {
      alert('Erro ao criar conta');
      console.log(error);
    }
  }

  return (
    <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-r from-indigo-200 via-purple-200 to-pink-200 p-4">
      <div className="bg-white rounded-2xl shadow-xl p-8 max-w-md w-full text-center text-black">
        <h1 className="font-bold text-3xl mb-4 text-indigo-900">Criar Conta</h1>
        <p className="text-gray-600 mb-6">Preencha os dados para começar a trocar livros!</p>

        <div className="flex flex-col gap-4 text-left">
          <div>
            <label className="block text-sm font-medium text-gray-700">Nome</label>
            <input
              className="border-2 border-slate-300 rounded-md p-2 w-full"
              type="email"
              placeholder="Digite seu nome"
              onChange={(e) => setUser({ ...user, name: e.target.value })}
              value={user.name || ''}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Email</label>
            <input
              className="border-2 border-slate-300 rounded-md p-2 w-full"
              type="email"
              placeholder="Digite seu email"
              onChange={(e) => setUser({ ...user, email: e.target.value })}
              value={user.email || ''}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Senha</label>
            <input
              className="border-2 border-slate-300 rounded-md p-2 w-full"
              type="password"
              placeholder="Digite uma senha"
              onChange={(e) => setUser({ ...user, password: e.target.value })}
              value={user.password || ''}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700">Confirmar senha</label>
            <input
              className="border-2 border-slate-300 rounded-md p-2 w-full"
              type="password"
              placeholder="Confirme sua senha"
              onChange={(e) => setUser({ ...user, confirmPassword: e.target.value })}
              value={user.confirmPassword || ''}
            />
            {difPass && user.confirmPassword && (
              <p className="text-red-500 text-sm mt-1">As senhas não coincidem</p>
            )}
          </div>
        </div>

        <div className="mt-6">
          <button
            disabled={difPass}
            className={`p-2 rounded-md w-full transition ${difPass
              ? 'bg-gray-300 cursor-not-allowed'
              : 'bg-indigo-600 text-white hover:bg-indigo-700'
              }`}
            onClick={createUser}
          >
            Cadastrar
          </button>
        </div>
      </div>
    </div>
  );
}
