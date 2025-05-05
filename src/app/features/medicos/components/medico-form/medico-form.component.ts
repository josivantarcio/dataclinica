import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MedicoService, Medico } from '../../services/medico.service';

@Component({
  selector: 'app-medico-form',
  templateUrl: './medico-form.component.html',
  styleUrls: ['./medico-form.component.scss']
})
export class MedicoFormComponent implements OnInit {
  medicoForm: FormGroup;
  isEditMode = false;
  loading = false;
  submitted = false;
  error = '';

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private medicoService: MedicoService
  ) {
    this.medicoForm = this.formBuilder.group({
      nome: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      crm: ['', [Validators.required, Validators.pattern('^[0-9]{6}$')]],
      especialidade: ['', Validators.required],
      telefone: ['', [Validators.required, Validators.pattern('^[0-9]{10,11}$')]],
      ativo: [true]
    });
  }

  ngOnInit(): void {
    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.loadMedico(id);
    }
  }

  get f() { return this.medicoForm.controls; }

  loadMedico(id: number): void {
    this.loading = true;
    this.medicoService.findById(id).subscribe({
      next: (medico) => {
        this.medicoForm.patchValue(medico);
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erro ao carregar médico';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.medicoForm.invalid) {
      return;
    }

    this.loading = true;
    const medico = this.medicoForm.value;

    if (this.isEditMode) {
      const id = this.route.snapshot.params['id'];
      this.medicoService.update(id, medico).subscribe({
        next: () => {
          this.router.navigate(['/medicos']);
        },
        error: (error) => {
          this.error = 'Erro ao atualizar médico';
          this.loading = false;
        }
      });
    } else {
      this.medicoService.create(medico).subscribe({
        next: () => {
          this.router.navigate(['/medicos']);
        },
        error: (error) => {
          this.error = 'Erro ao criar médico';
          this.loading = false;
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/medicos']);
  }
} 