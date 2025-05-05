import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ConsultaService, Consulta } from '../../services/consulta.service';
import { MedicoService } from '../../../medicos/services/medico.service';
import { PacienteService } from '../../../pacientes/services/paciente.service';

@Component({
  selector: 'app-consulta-form',
  templateUrl: './consulta-form.component.html',
  styleUrls: ['./consulta-form.component.scss']
})
export class ConsultaFormComponent implements OnInit {
  consultaForm: FormGroup;
  isEditMode = false;
  loading = false;
  submitted = false;
  error = '';
  medicos: any[] = [];
  pacientes: any[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private consultaService: ConsultaService,
    private medicoService: MedicoService,
    private pacienteService: PacienteService
  ) {
    this.consultaForm = this.formBuilder.group({
      medicoId: ['', Validators.required],
      pacienteId: ['', Validators.required],
      dataHora: ['', [Validators.required, this.futureDateValidator()]],
      observacoes: [''],
      status: ['AGENDADA']
    });
  }

  ngOnInit(): void {
    this.loadMedicos();
    this.loadPacientes();

    const id = this.route.snapshot.params['id'];
    if (id) {
      this.isEditMode = true;
      this.loadConsulta(id);
    }
  }

  get f() { return this.consultaForm.controls; }

  futureDateValidator() {
    return (control: any) => {
      const date = new Date(control.value);
      const now = new Date();
      if (date <= now) {
        return { pastDate: true };
      }
      return null;
    };
  }

  loadMedicos(): void {
    this.medicoService.findAll().subscribe({
      next: (data) => {
        this.medicos = data;
      },
      error: (error) => {
        this.error = 'Erro ao carregar médicos';
      }
    });
  }

  loadPacientes(): void {
    this.pacienteService.findAll().subscribe({
      next: (data) => {
        this.pacientes = data;
      },
      error: (error) => {
        this.error = 'Erro ao carregar pacientes';
      }
    });
  }

  loadConsulta(id: number): void {
    this.loading = true;
    this.consultaService.findById(id).subscribe({
      next: (consulta) => {
        this.consultaForm.patchValue(consulta);
        this.loading = false;
      },
      error: (error) => {
        this.error = 'Erro ao carregar consulta';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.consultaForm.invalid) {
      return;
    }

    this.loading = true;
    const consulta = this.consultaForm.value;

    if (this.isEditMode) {
      const id = this.route.snapshot.params['id'];
      this.consultaService.update(id, consulta).subscribe({
        next: () => {
          this.router.navigate(['/consultas']);
        },
        error: (error) => {
          this.error = 'Erro ao atualizar consulta';
          this.loading = false;
        }
      });
    } else {
      this.consultaService.create(consulta).subscribe({
        next: () => {
          this.router.navigate(['/consultas']);
        },
        error: (error) => {
          this.error = 'Erro ao criar consulta';
          this.loading = false;
        }
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/consultas']);
  }
} 