import { useEffect, useState } from 'react';
import { useForm } from 'react-hook-form';
import { api } from '../api';

type Car = { id:string; licensePlate:string; type:string; numberOfPassanger:number; fuelType:string };
type Form = { carId:string; start:string; end:string };

export default function NewRental(){
    const { register, handleSubmit, reset, formState: { errors, isSubmitting } } = useForm<Form>();
    const [cars, setRes] = useState<Car[]>([]);

    useEffect(() => { api.get('/api/cars').then(c=>setRes(c.data)); }, []);

    return (
        <form className="max-w-lg mx-auto card" onSubmit={handleSubmit(async v => {
            try {
                await api.post('/api/rentals', {carId: v.carId, start: new Date(v.start), end: new Date(v.end)});
                alert('Bérlés rögzítve');
                reset();
            } catch (e: any) {
                if (e?.response?.status === 409) alert('Az adott időszakban az autó foglalt! Válassz másik időpontot.');
                else alert('Hiba történt.');
            }
        })}>
            <div className="card-p space-y-4">
                <header className="space-y-1">
                    <h1 className="text-2xl font-bold">Új foglalás</h1>
                    <p className="help">Válassz járművet és idősávot.</p>
                </header>
                <div>
                    <label className="label">Jármű</label>
                    <select className="input" {...register('carId', {required: 'Válaszd ki az autót'})}>
                        <option value="">Válassz…</option>
                        {cars.map(c => <option key={c.id} value={c.id}>{c.type} – {c.licensePlate}</option>)}
                    </select>
                    {errors.carId && <p className="error">{errors.carId.message}</p>}
                </div>
                <div className="grid sm:grid-cols-2 gap-4">
                    <div>
                        <label className="label">Kezdet</label>
                        <input className="input"
                               type="datetime-local" {...register('start', {required: 'Add meg a kezdő időpontot'})} />
                        {errors.start && <p className="error">{errors.start.message}</p>}
                    </div>
                    <div>
                        <label className="label">Vége</label>
                        <input className="input"
                               type="datetime-local" {...register('end', {required: 'Add meg a záró időpontot'})} />
                        {errors.end && <p className="error">{errors.end.message}</p>}
                    </div>
                </div>
                <button className="btn btn-primary w-full"
                        disabled={isSubmitting}>{isSubmitting ? 'Mentés…' : 'Bérlés'}</button>
            </div>
        </form>
    );
}

