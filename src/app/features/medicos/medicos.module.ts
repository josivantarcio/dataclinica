import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { MedicoListComponent } from './components/medico-list/medico-list.component';
import { MedicoFormComponent } from './components/medico-form/medico-form.component';

const routes: Routes = [
  {
    path: '',
    component: MedicoListComponent
  },
  {
    path: 'novo',
    component: MedicoFormComponent
  },
  {
    path: 'editar/:id',
    component: MedicoFormComponent
  }
];

@NgModule({
  declarations: [
    MedicoListComponent,
    MedicoFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ]
})
export class MedicosModule { } 