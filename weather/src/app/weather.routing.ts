import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core/src/metadata/ng_module';

import { AppComponent } from './app.component';
import { NgModule } from '@angular/core';

export const WeatherRoutes: Routes = [
  { path: '', component: AppComponent }
]

export const ROUTING : ModuleWithProviders = RouterModule.forRoot(WeatherRoutes);
