import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ConsultaService, Consulta } from '../../services/consulta.service';

@Component({
  selector: 'app-consulta-list',
  templateUrl: './consulta-list.component.html',
  styleUrls: ['./consulta-list.component.scss']
})
export class ConsultaListComponent implements OnInit {
  consultas: Consulta[] = [];
  loading = false;
  error = '';

  constructor(
    private consultaService: ConsultaService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadConsultas();
  }

  loadConsultas(): void {
    this.loading = true;
    this.consultaService.findAll().subscribe({
      next: (data) => {
        this.consultas = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erro ao carregar consultas';
        this.loading = false;
      }
    });
  }

  onEdit(id: number): void {
    this.router.navigate(['/consultas/editar', id]);
  }

  onDelete(id: number): void {
    if (confirm('Tem certeza que deseja excluir esta consulta?')) {
      this.consultaService.delete(id).subscribe({
        next: () => {
          this.consultas = this.consultas.filter(c => c.id !== id);
        },
        error: (error) => {
          this.error = 'Erro ao excluir consulta';
        }
      });
    }
  }

  onNew(): void {
    this.router.navigate(['/consultas/nova']);
  }

  onCancelar(id: number): void {
    if (confirm('Tem certeza que deseja cancelar esta consulta?')) {
      this.consultaService.cancelar(id).subscribe({
        next: (consulta) => {
          const index = this.consultas.findIndex(c => c.id === id);
          if (index !== -1) {
            this.consultas[index] = consulta;
          }
        },
        error: (error) => {
          this.error = 'Erro ao cancelar consulta';
        }
      });
    }
  }

  onConfirmar(id: number): void {
    this.consultaService.confirmar(id).subscribe({
      next: (consulta) => {
        const index = this.consultas.findIndex(c => c.id === id);
        if (index !== -1) {
          this.consultas[index] = consulta;
        }
      },
      error: (error) => {
        this.error = 'Erro ao confirmar consulta';
      }
    });
  }

  onRealizar(id: number): void {
    this.consultaService.realizar(id).subscribe({
      next: (consulta) => {
        const index = this.consultas.findIndex(c => c.id === id);
        if (index !== -1) {
          this.consultas[index] = consulta;
        }
      },
      error: (error) => {
        this.error = 'Erro ao realizar consulta';
      }
    });
  }

  formatarDataHora(dataHora: string): string {
    return new Date(dataHora).toLocaleString('pt-BR');
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'AGENDADA':
        return 'badge-warning';
      case 'CONFIRMADA':
        return 'badge-info';
      case 'CANCELADA':
        return 'badge-danger';
      case 'REALIZADA':
        return 'badge-success';
      default:
        return 'badge-secondary';
    }
  }
} 