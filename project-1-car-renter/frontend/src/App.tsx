import {Routes, Route, Link, Navigate, useNavigate} from 'react-router-dom';
import CarList from './pages/CarList.tsx';
import Login from './pages/Login';
import NewRental from './pages/NewRental.tsx';
import VehicleRegister from './pages/VehicleRegister.tsx';
import ProtectedRoute from './routes/ProtectedRoute';
import AdminRoute from './routes/AdminRoute';
import Registration from "./pages/Registration.tsx";
import {useAuth} from "./auth/AuthContext.tsx";
import {useState} from "react";
import {Car, LogIn, LogOut, Menu, UserPlus} from "lucide-react";

export default function App() {
    const {authed, role, logout} = useAuth();
    const nav = useNavigate();
    const [open, setOpen] = useState(false);

    return (
        <div className="min-h-full flex flex-col">
            {/* NAVBAR */}
            <header
                className="sticky top-0 z-40 border-b border-gray-200 bg-white/80 backdrop-blur supports-[backdrop-filter]:bg-white/60 dark:border-gray-800 dark:bg-gray-900/70">
                <nav className="container-p h-16 flex items-center justify-between">
                    <div className="flex items-center gap-6">
                        <Car className="size-5"/>
                        <Link to="/" className="text-lg font-semibold tracking-tight">AutoRent</Link>
                        <div className="hidden md:flex items-center gap-4">
                            <Link to="/" className="nav-link">Bérelhető autók</Link>
                            {authed && <Link to="/new-rental" className="nav-link">Autó kölcsönzés</Link>}
                            {role === 'ADMIN' && <Link to="/admin/cars" className="nav-link">Nyilvántartó</Link>}
                        </div>
                    </div>
                    <div className="hidden md:flex items-center gap-3">
                        {authed ? (
                            <button className="btn btn-ghost" onClick={() => {
                                logout();
                                nav('/login');
                            }}><LogOut className="size-4" />Kilépés</button>
                        ) : (
                            <>
                                <Link to="/login" className="btn btn-ghost"><LogIn className="size-4" /> Belépés</Link>
                                <Link to="/registration" className="btn btn-primary"><UserPlus className="size-4" />Regisztráció</Link>
                            </>
                        )}
                    </div>
                    <button className="md:hidden btn btn-ghost" aria-label="Menü" onClick={() => setOpen(v => !v)}>
                        <Menu className="size-5"/>
                    </button>
                </nav>
                {/* Mobile menu */}
                {open && (
                    <div className="md:hidden border-t border-gray-200 dark:border-gray-800">
                        <div className="container-p py-3 flex flex-col gap-2">
                            <Link to="/" className="nav-link" onClick={() => setOpen(false)}>Bérelhető autók</Link>
                            {authed && <Link to="/new-rental" className="nav-link" onClick={() => setOpen(false)}>Autó
                                kölcsönzés</Link>}
                            {role === 'ADMIN' && <Link to="/admin/cars" className="nav-link"
                                                       onClick={() => setOpen(false)}>Nyilvántartó</Link>}
                            <div className="pt-2 border-t border-gray-200 dark:border-gray-800"/>
                            {authed ? (
                                <button className="btn btn-ghost w-full" onClick={() => {
                                    logout();
                                    nav('/login');
                                }}>Kilépés</button>
                            ) : (
                                <div className="flex gap-2">
                                    <Link to="/login" className="btn btn-ghost flex-1"
                                          onClick={() => setOpen(false)}>Belépés</Link>
                                    <Link to="/registration" className="btn btn-primary flex-1"
                                          onClick={() => setOpen(false)}>Regisztráció</Link>
                                </div>
                            )}
                        </div>
                    </div>
                )}
            </header>


            {/* CONTENT */}
            <main className="container-p flex-1 py-8">
                <Routes>
                    <Route path="/" element={<CarList/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/registration" element={<Registration/>}/>
                    <Route path="/new-rental" element={<ProtectedRoute><NewRental/></ProtectedRoute>}/>
                    <Route path="/admin/cars" element={<AdminRoute><VehicleRegister/></AdminRoute>}/>
                    <Route path="*" element={<Navigate to="/"/>}/>
                </Routes>
            </main>


            <footer className="border-t border-gray-200 dark:border-gray-800">
                <div className="container-p py-6 text-sm text-gray-500">© {new Date().getFullYear()} AutoRent</div>
            </footer>
        </div>
    );
}


