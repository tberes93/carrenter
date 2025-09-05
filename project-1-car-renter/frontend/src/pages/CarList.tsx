import { useEffect, useState } from 'react';
import { api } from '../api';

type Car = { id: string; licensePlate: string; type: string; numberOfPassangers?: number; fuelType?: string };

export default function CarList() {
    const [data, setData] = useState<Car[]>([]);
    useEffect(() => {
        api.get('/api/cars').then(r => setData(r.data));
    }, []);
    return (
        <section className="space-y-4">
            <header className="flex items-end justify-between gap-4">
                <div>
                    <h1 className="text-2xl font-bold">Bérelhető autók</h1>
                    <p className="text-sm text-gray-600">Válassz a jelenleg elérhető járműveink közül.</p>
                </div>
            </header>


            <div className="grid grid-auto-fit gap-4">
                {data.map(c => (
                    <article key={c.id} className="card">
                        <div className="card-p space-y-3">
                            <div className="flex items-center justify-between">
                                <h2 className="text-lg font-semibold">{c.type}</h2>
                                {c.fuelType && <span className="badge">{c.fuelType}</span>}
                            </div>
                            <dl className="grid grid-cols-2 gap-x-4 gap-y-2 text-sm">
                                <div>
                                    <dt className="text-gray-500">Rendszám</dt>
                                    <dd className="font-medium">{c.licensePlate}</dd>
                                </div>
                                <div>
                                    <dt className="text-gray-500">Férőhely</dt>
                                    <dd className="font-medium">{c.numberOfPassangers ?? '-'} fő</dd>
                                </div>
                            </dl>
                        </div>
                    </article>
                ))}
            </div>


            {data.length === 0 && (
                <div className="card"><div className="card-p text-sm text-gray-600">Jelenleg nincs megjeleníthető autó.</div></div>
            )}
        </section>
    );
}
