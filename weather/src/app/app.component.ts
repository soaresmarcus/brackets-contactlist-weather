import { Component } from '@angular/core';
import { Http, Response } from '@angular/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  cityName = '';
  temp = '';
  description = '';
  icon = '';
  tempMin = '';
  tempMax = '';

  constructor(private http: Http) { }

  searchCity()  {
    return this.http.get('https://api.openweathermap.org/data/2.5/weather?appid=6dc97f07fd347b3456fb3e13de85c8fa&units=metric&q=' + this.cityName)
    .subscribe(
      (res: Response) => {
        const weatherCity = res.json();
        console.log(weatherCity);

        this.cityName = weatherCity.name;
        this.temp = weatherCity.main.temp;
        this.description = weatherCity.weather[0].description;
        this.icon = weatherCity.weather[0].icon;
        this.tempMin = weatherCity.main.temp_min;
        this.tempMax = weatherCity.main.temp_max;
      });

  }

  title = 'weather';
}
