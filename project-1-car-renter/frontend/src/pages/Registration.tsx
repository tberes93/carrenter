import { useForm } from 'react-hook-form';
import { api } from '../api';
import { useNavigate } from 'react-router-dom';

type Form = { email: string; password: string };

export default function Registration(){
    const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm<Form>();    const nav = useNavigate();

    return (
        <form className="max-w-sm mx-auto card" onSubmit={handleSubmit(async v => {
            const res = await api.post('/api/auth/register', v);
            localStorage.setItem('token', res.data.accessToken);
            nav('/login');
        })}>
            <div className="card-p space-y-4">
                <header>
                    <h1 className="text-2xl font-bold">Regisztráció</h1>
                    <p className="help">Hozz létre egy fiókot az egyszerű foglaláshoz.</p>
                </header>
                <div>
                    <label className="label">Email</label>
                    <input className="input"
                           placeholder="pl. te@pelda.hu" {...register('email', {required: 'Email kötelező'})} />
                    {errors.email && <p className="error">{errors.email.message}</p>}
                </div>
                <div>
                    <label className="label">Jelszó</label>
                    <input className="input" type="password" placeholder="min. 6 karakter" {...register('password', {
                        required: 'Jelszó kötelező',
                        minLength: {value: 6, message: 'Legalább 6 karakter'}
                    })} />
                    {errors.password && <p className="error">{errors.password.message}</p>}
                </div>
                <button className="btn btn-primary w-full"
                        disabled={isSubmitting}>{isSubmitting ? 'Fiók létrehozása…' : 'Regisztrálás'}</button>
            </div>
        </form>
    );
}

