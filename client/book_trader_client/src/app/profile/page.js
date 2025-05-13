'use client'
import Header from "@/components/infra/header"
import { useEffect, useState } from "react"

export default function Profile() {

  const [user, setUser] = useState({})

  useEffect(() => {
    setUser(JSON.parse(localStorage.getItem('user')))
  }, [])

  return (
    <>
      <Header showRegister={user ? false : true} />
      <div>
        <div className="w-full flex flex-col items-center justify-center">
          <div className="border border-white w-full bg-slate-200 text-black shadow-2xl p-6 flex flex-col items-center justify-center gap-4">
            <div className="flex flex-row gap-2">
              <button className="bg-cyan-400 p-2 rounded-md border border-black hover:bg-cyan-500 cursor-pointer">Cadastrar livro</button>
              <button className="bg-cyan-400 p-2 rounded-md border border-black hover:bg-cyan-500 cursor-pointer">Cadastrar livro</button>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}