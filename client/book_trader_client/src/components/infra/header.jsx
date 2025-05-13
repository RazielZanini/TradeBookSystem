"use client";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import Link from "next/link";

export default function Header({ showRegister = true }) {
  const [authenticated, setAuthenticated] = useState(false);
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("token");
    setAuthenticated(!!token);
  }, []);

  const handleLogout = () => {
    localStorage.removeItem("token");
    router.push("/login"); // Redireciona para a página de login
  };

  return (
    <div className="bg-gradient-to-r from-indigo-200 via-purple-200 to-pink-200 text-2xl text-black p-4 flex flex-row gap-4 justify-between rounded-lg shadow-md">
      <span className="font-bold hover:bg-slate-200 p-2 rounded-md transition duration-300 ease-in-out">
        <Link href="/profile">Perfil</Link>
      </span>

      {authenticated ? (
        <button
          onClick={handleLogout}
          className="bg-red-400 hover:bg-red-500 p-2 rounded-md ml-auto text-white transition duration-300 ease-in-out"
        >
          Sair
        </button>
      ) : (
        <>
          {showRegister && (
            <span className="bg-emerald-400 hover:bg-emerald-500 p-2 rounded-md text-white cursor-pointer transition duration-300 ease-in-out">
              <Link href="/register">Cadastrar</Link>
            </span>
          )}
          <span className="bg-blue-400 hover:bg-blue-500 p-2 rounded-md text-white cursor-pointer transition duration-300 ease-in-out">
            <Link href="/login">Login</Link>
          </span>
        </>
      )}
    </div>
  );
}
