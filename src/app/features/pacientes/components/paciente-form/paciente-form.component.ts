import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PacienteService, Paciente } from '../../services/paciente.service';

@Component({
  selector: 'app-paciente-form',
  templateUrl: './paciente-form.component.html',
  styleUrls: ['./paciente-form.component.scss']
})
export class PacienteFormComponent implements OnInit {
  pacienteForm: FormGroup;
  isEditMode = false;
  loading = false;
  submitted = false;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private pacienteService: PacienteService
  ) {
    this.pacienteForm = this.formBuilder.group({
      nome: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      cpf: ['', [Validators.required, Validators.pattern('^[0-9]{11}$')]],
      dataNascimento: ['', Validators.required],
      telefone: ['', [Validators.required, Validators.pattern('^[0-9]{10,11}$')]],
      endereco: ['', Validators.required],
      ativo: [true]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.loadPaciente(id);
    }
  }

  get f() { return this.pacienteForm.controls; }

  loadPaciente(id: number): void {
    this.loading = true;
    this.pacienteService.findById(id).subscribe({
      next: (paciente) => {
        this.pacienteForm.patchValue(paciente);
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erro ao carregar paciente';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.pacienteForm.invalid) {
      return;
    }

    this.loading = true;
    const paciente = this.pacienteForm.value;

    if (this.isEditMode) {
      const id = this.route.snapshot.params['id'];
      this.pacienteService.update(id, paciente).subscribe({
        next: () => {
          this.router.navigate(['/pacientes']);
        },
        error: (error) => {
          this.error = 'Erro ao atualizar paciente';
          this.loading = false;
        }
      });
    } else {
      this.pacienteService.create(paciente).subscribe({
        next: () => {
          this.router.navigate(['/pacientes']);
        },
        error: (error) => {
          this.error = 'Erro ao criar paciente';
          this.loading = false;
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/pacientes']);
  }
} 