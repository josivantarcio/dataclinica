import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'dashboard',
    pathMatch: 'full'
  },
  {
    path: 'auth',
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'dashboard',
    loadChildren: () => import('./features/dashboard/dashboard.module').then(m => m.DashboardModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'medicos',
    loadChildren: () => import('./features/medicos/medicos.module').then(m => m.MedicosModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'pacientes',
    loadChildren: () => import('./features/pacientes/pacientes.module').then(m => m.PacientesModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'consultas',
    loadChildren: () => import('./features/consultas/consultas.module').then(m => m.ConsultasModule),
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { } 