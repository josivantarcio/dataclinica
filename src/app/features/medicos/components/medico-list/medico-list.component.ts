import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MedicoService, Medico } from '../../services/medico.service';

@Component({
  selector: 'app-medico-list',
  templateUrl: './medico-list.component.html',
  styleUrls: ['./medico-list.component.scss']
})
export class MedicoListComponent implements OnInit {
  medicos: Medico[] = [];
  loading = false;
  error = '';

  constructor(
    private medicoService: MedicoService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadMedicos();
  }

  loadMedicos(): void {
    this.loading = true;
    this.medicoService.findAll().subscribe({
      next: (data) => {
        this.medicos = data;
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erro ao carregar médicos';
        this.loading = false;
      }
    });
  }

  onEdit(id: number): void {
    this.router.navigate(['/medicos/editar', id]);
  }

  onDelete(id: number): void {
    if (confirm('Tem certeza que deseja excluir este médico?')) {
      this.medicoService.delete(id).subscribe({
        next: () => {
          this.medicos = this.medicos.filter(m => m.id !== id);
        },
        error: (error) => {
          this.error = 'Erro ao excluir médico';
        }
      });
    }
  }

  onNew(): void {
    this.router.navigate(['/medicos/novo']);
  }
} 