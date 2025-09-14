import { Routes, Route, Link, Navigate, useNavigate } from 'react-router-dom'
import { useState } from 'react'
import { Car as CarIcon, LogIn, LogOut, Menu, UserPlus, CalendarPlus, Wrench } from 'lucide-react'

import CarList from './pages/CarList'
import Login from './pages/Login'
import NewRental from './pages/NewRental'
import VehicleRegister from './pages/VehicleRegister'
import ProtectedRoute from './routes/ProtectedRoute'
import AdminRoute from './routes/AdminRoute'
import Registration from './pages/Registration'
import { useAuth } from './auth/AuthContext'

export default function App() {
    const { authed, role, logout } = useAuth()
    const nav = useNavigate()
    const [open, setOpen] = useState(false)

    const closeMenu = () => setOpen(false)

    return (
        <div className="min-h-dvh bg-surface text-ink">
            {/* SIDEBAR (desktop + mobile overlay) */}
            <aside
                className={[
                    'sidebar',
                    open ? 'translate-x-0' : '-translate-x-full md:translate-x-0',
                ].join(' ')}
            >
                <div className="sidebar-header">
                    <div className="brand">
                        <CarIcon className="size-5" />
                        <span>AutoRent</span>
                    </div>
                    <button
                        className="md:hidden btn btn-ghost -mr-2"
                        aria-label="Menü bezárása"
                        onClick={() => setOpen(false)}
                    >
                        ✕
                    </button>
                </div>

                <nav className="sidebar-nav">
                    <Link to="/" onClick={closeMenu} className="nav-item">
                        <CarIcon className="size-4" />
                        Bérelhető autók
                    </Link>

                    {authed && (
                        <Link to="/new-rental" onClick={closeMenu} className="nav-item">
                            <CalendarPlus className="size-4" />
                            Autó kölcsönzés
                        </Link>
                    )}

                    {role === 'ADMIN' && (
                        <Link to="/admin/cars" onClick={closeMenu} className="nav-item">
                            <Wrench className="size-4" />
                            Nyilvántartó
                        </Link>
                    )}
                </nav>

                <div className="sidebar-footer">
                    {!authed ? (
                        <div className="grid gap-2">
                            <Link to="/login" onClick={closeMenu} className="btn btn-ghost w-full">
                                <LogIn className="size-4" />
                                Belépés
                            </Link>
                            <Link to="/registration" onClick={closeMenu} className="btn btn-primary w-full">
                                <UserPlus className="size-4" />
                                Regisztráció
                            </Link>
                        </div>
                    ) : (
                        <button
                            className="btn btn-ghost w-full"
                            onClick={() => {
                                logout()
                                nav('/login')
                            }}
                        >
                            <LogOut className="size-4" />
                            Kilépés
                        </button>
                    )}
                </div>
            </aside>

            {/* MOBILE TOPBAR */}
            <header className="md:hidden sticky top-0 z-40 bg-surface/90 backdrop-blur border-b border-line">
                <div className="container-p h-14 flex items-center justify-between">
                    <button
                        className="btn btn-ghost"
                        aria-label="Menü"
                        onClick={() => setOpen((v) => !v)}
                    >
                        <Menu className="size-5" />
                    </button>
                    <div className="flex items-center gap-2 font-semibold">
                        <CarIcon className="size-5" />
                        AutoRent
                    </div>
                    <div className="w-9" />
                </div>
            </header>

            {/* CONTENT */}
            <main className="content">
                <div className="container-p py-8">
                    <Routes>
                        <Route path="/" element={<CarList />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/registration" element={<Registration />} />
                        <Route
                            path="/new-rental"
                            element={
                                <ProtectedRoute>
                                    <NewRental />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/admin/cars"
                            element={
                                <AdminRoute>
                                    <VehicleRegister />
                                </AdminRoute>
                            }
                        />
                        <Route path="*" element={<Navigate to="/" />} />
                    </Routes>
                </div>

                <footer className="mt-auto border-t border-line">
                    <div className="container-p py-6 text-sm text-muted">
                        © {new Date().getFullYear()} AutoRent
                    </div>
                </footer>
            </main>

            {/* BACKDROP (mobile) */}
            {open && (
                <button
                    aria-label="Menü háttér"
                    className="fixed inset-0 z-30 bg-black/30 md:hidden"
                    onClick={() => setOpen(false)}
                />
            )}
        </div>
    )
}
