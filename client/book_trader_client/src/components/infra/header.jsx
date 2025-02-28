import Link from "next/link";

export default function Header({ showRegister = true }) {

  return (
    <div className="bg-white text-2xl text-black p-2 flex flex-row gap-2 justify-between">
      <span className="font-bold hover:bg-slate-200 p-1 rounded-lg">
        <Link href="/profile">Perfil</Link>
      </span>

      {showRegister && (
        <span className="text-base bg-emerald-400 hover:bg-emerald-500 p-1 rounded-lg ml-auto">
          <Link href="/">Cadastrar</Link>
        </span>
      )}
    </div>
  );
}
