import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { PacienteListComponent } from './components/paciente-list/paciente-list.component';
import { PacienteFormComponent } from './components/paciente-form/paciente-form.component';

const routes: Routes = [
  {
    path: '',
    component: PacienteListComponent
  },
  {
    path: 'novo',
    component: PacienteFormComponent
  },
  {
    path: 'editar/:id',
    component: PacienteFormComponent
  }
];

@NgModule({
  declarations: [
    PacienteListComponent,
    PacienteFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ]
})
export class PacientesModule { } 