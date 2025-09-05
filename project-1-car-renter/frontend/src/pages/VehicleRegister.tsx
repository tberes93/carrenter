import { useEffect, useState } from 'react';
import { api } from '../api';
import {Edit3, PlusCircle, Save, Trash2} from "lucide-react";

type Car = { id?:string; licensePlate:string; type:string; numberOfPassangers?:number; fuelType?:string };

export default function VehicleRegister(){
    const [items, setItems] = useState<Car[]>([]);
    const [form, setForm] = useState<Car>({ licensePlate:'', type:'', numberOfPassangers:0, fuelType:'' });

    const load = async()=> setItems((await api.get('/api/cars')).data);
    useEffect(()=>{ load(); },[]);

    const save = async()=>{
        if (form.id) await api.put(`/api/cars/${form.id}`, form);
        else await api.post('/api/cars', form);
        setForm({ licensePlate:'', type:'', numberOfPassangers:0, fuelType:'' });
        await load();
    };

    const edit = (c:Car)=> setForm(c);
    const del = async(id?:string)=>{ if(!id) return; await api.delete(`/api/cars/${id}`); await load(); };

    return (
        <div className="space-y-6">
            <header>
                <h1 className="text-2xl font-bold">Admin – Bérelhető autók</h1>
                <p className="help">Hozzáadás, módosítás, törlés.</p>
            </header>


            <div className="card">
                <div className="card-p grid gap-4 md:grid-cols-4">
                    <div>
                        <label className="label">Rendszám</label>
                        <input className="input" placeholder="ABC-123" value={form.licensePlate}
                               onChange={e => setForm({...form, licensePlate: e.target.value})}/>
                    </div>
                    <div>
                        <label className="label">Típus</label>
                        <input className="input" placeholder="Pl. Toyota Corolla" value={form.type}
                               onChange={e => setForm({...form, type: e.target.value})}/>
                    </div>
                    <div>
                        <label className="label">Férőhely</label>
                        <input className="input" type="number" placeholder="pl. 5" value={form.numberOfPassangers ?? ''}
                               onChange={e => setForm({
                                   ...form,
                                   numberOfPassangers: e.target.value === '' ? undefined : +e.target.value
                               })}/>
                    </div>
                    <div>
                        <label className="label">Üzemanyag</label>
                        <input className="input" placeholder="Benzin/Dízel/Hibrid/Elektromos"
                               value={form.fuelType ?? ''}
                               onChange={e => setForm({...form, fuelType: e.target.value})}/>
                    </div>
                    <div className="md:col-span-4">
                        <button className="btn btn-primary"
                                onClick={save}>{form.id ? (<><Save className="size-4" /> Mentés</>) : (<><PlusCircle className="size-4" />Autó hozzáadása</>)}</button>
                    </div>
                </div>
            </div>


            {/* Lista */}
            <div className="card">
                <div className="card-p">
                    <div className="hidden md:block overflow-x-auto">
                        <table className="w-full text-left text-sm">
                            <thead className="text-gray-600">
                            <tr>
                                <th className="py-2 pr-4">Rendszám</th>
                                <th className="py-2 pr-4">Típus</th>
                                <th className="py-2 pr-4">Férőhely</th>
                                <th className="py-2 pr-4">Üzemanyag</th>
                                <th className="py-2 pr-4 w-40">Műveletek</th>
                            </tr>
                            </thead>
                            <tbody className="divide-y">
                            {items.map(c => (
                                <tr key={c.id} className="align-middle">
                                    <td className="py-3 pr-4 font-medium">{c.licensePlate}</td>
                                    <td className="py-3 pr-4">{c.type}</td>
                                    <td className="py-3 pr-4">{c.numberOfPassangers ?? '-'}</td>
                                    <td className="py-3 pr-4">{c.fuelType ?? '-'}</td>
                                    <td className="py-3 pr-4">
                                        <div className="flex gap-2">
                                            <button className="btn btn-ghost" onClick={() => edit(c)}><Edit3 className="size-4" />Szerkeszt</button>
                                            <button className="btn btn-ghost" onClick={() => del(c.id)}><Trash2 className="size-4" />Törlés</button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    </div>


                    {/* Mobile kártyák */}
                    <ul className="md:hidden space-y-3">
                        {items.map(c => (
                            <li key={c.id} className="card">
                                <div className="card-p space-y-3">
                                    <div className="flex items-center justify-between">
                                        <h3 className="text-base font-semibold">{c.type}</h3>
                                        {c.fuelType && <span className="badge">{c.fuelType}</span>}
                                    </div>
                                    <dl className="grid grid-cols-2 gap-x-4 gap-y-1 text-sm">
                                        <div>
                                            <dt className="text-gray-500">Rendszám</dt>
                                            <dd className="font-medium">{c.licensePlate}</dd>
                                        </div>
                                        <div>
                                            <dt className="text-gray-500">Férőhely</dt>
                                            <dd className="font-medium">{c.numberOfPassangers ?? '-'}</dd>
                                        </div>
                                    </dl>
                                    <div className="flex gap-2">
                                        <button className="btn btn-ghost flex-1" onClick={() => edit(c)}> <Edit3 className="size-4" />Szerkeszt
                                        </button>
                                        <button className="btn btn-ghost flex-1" onClick={() => del(c.id)}><Trash2 className="size-4" />Törlés
                                        </button>
                                    </div>
                                </div>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}
