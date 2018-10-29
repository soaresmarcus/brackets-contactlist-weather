import { RouterModule, Routes } from '@angular/router';
import { ModuleWithProviders } from '@angular/core/src/metadata/ng_module';

import { CurrentComponent } from './current/current.component';
import { ForecastComponent } from './forecast/forecast.component';
import { NgModule } from '@angular/core';

export const WeatherRoutes: Routes = [
  { path: 'forecast', component: ForecastComponent }
]

export const ROUTING : ModuleWithProviders = RouterModule.forRoot(WeatherRoutes);
