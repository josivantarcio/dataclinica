import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

export interface Consulta {
  id?: number;
  medicoId: number;
  pacienteId: number;
  dataHora: string;
  observacoes: string;
  status: 'AGENDADA' | 'CONFIRMADA' | 'CANCELADA' | 'REALIZADA';
  medico?: {
    id: number;
    nome: string;
    especialidade: string;
  };
  paciente?: {
    id: number;
    nome: string;
    cpf: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class ConsultaService {
  private apiUrl = `${environment.apiUrl}/consultas`;

  constructor(private http: HttpClient) { }

  findAll(): Observable<Consulta[]> {
    return this.http.get<Consulta[]>(this.apiUrl);
  }

  findById(id: number): Observable<Consulta> {
    return this.http.get<Consulta>(`${this.apiUrl}/${id}`);
  }

  create(consulta: Consulta): Observable<Consulta> {
    return this.http.post<Consulta>(this.apiUrl, consulta);
  }

  update(id: number, consulta: Consulta): Observable<Consulta> {
    return this.http.put<Consulta>(`${this.apiUrl}/${id}`, consulta);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  cancelar(id: number): Observable<Consulta> {
    return this.http.put<Consulta>(`${this.apiUrl}/${id}/cancelar`, {});
  }

  confirmar(id: number): Observable<Consulta> {
    return this.http.put<Consulta>(`${this.apiUrl}/${id}/confirmar`, {});
  }

  realizar(id: number): Observable<Consulta> {
    return this.http.put<Consulta>(`${this.apiUrl}/${id}/realizar`, {});
  }
} 