import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PacienteService, Paciente } from '../../services/paciente.service';

@Component({
  selector: 'app-paciente-list',
  templateUrl: './paciente-list.component.html',
  styleUrls: ['./paciente-list.component.scss']
})
export class PacienteListComponent implements OnInit {
  pacientes: Paciente[] = [];
  loading = false;
  error = '';

  constructor(
    private pacienteService: PacienteService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadPacientes();
  }

  loadPacientes(): void {
    this.loading = true;
    this.pacienteService.findAll().subscribe({
      next: (data) => {
        this.pacientes = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erro ao carregar pacientes';
        this.loading = false;
      }
    });
  }

  onEdit(id: number): void {
    this.router.navigate(['/pacientes/editar', id]);
  }

  onDelete(id: number): void {
    if (confirm('Tem certeza que deseja excluir este paciente?')) {
      this.pacienteService.delete(id).subscribe({
        next: () => {
          this.pacientes = this.pacientes.filter(p => p.id !== id);
        },
        error: (error) => {
          this.error = 'Erro ao excluir paciente';
        }
      });
    }
  }

  onNew(): void {
    this.router.navigate(['/pacientes/novo']);
  }

  formatarCPF(cpf: string): string {
    return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
  }

  formatarData(data: string): string {
    return new Date(data).toLocaleDateString('pt-BR');
  }
} 