import { Injectable } from '@angular/core';
import { CurrentWeather } from './current-weather';

@Injectable({
  providedIn: 'root'
})
export class WeatherService {
  current:CurrentWeather = new CurrentWeather('Montes Claros', '26', 'chuvoso', '22','31');
  
  constructor() { }

  weatherNow(){
    return this.current;
  }
}
