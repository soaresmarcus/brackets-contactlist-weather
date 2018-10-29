import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core/src/metadata/ng_module';

import { CurrentComponent } from './current/current.component';
import { NgModule } from '@angular/core';

export const WeatherRoutes: Routes = [
  { path: '', component: CurrentComponent }
]

export const ROUTING : ModuleWithProviders = RouterModule.forRoot(WeatherRoutes);
