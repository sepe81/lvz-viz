import { Component, OnInit } from '@angular/core';

declare var $: any;

declare var L: any;

@Component({
  selector: 'app-heatmap',
  templateUrl: './heatmap.component.html',
  styleUrls: ['./heatmap.component.css']
})
export class HeatmapComponent implements OnInit {

  private map: any;

  private heat: any;

  constructor() { }

  ngOnInit(): void {
    this.map = L.map('mapStatistics').setView([51.339695, 12.373075], 11);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(this.map);

    this.heat = L.heatLayer([], {
      radius: 25,
      minOpacity: 0.5
    });
    //this.map.addLayer(this.heat);

    this.createSlider();
  }

  createSlider() {
    $('#slider').dateRangeSlider({
      arrows: false,
      bounds: {
        min: new Date(2015, 0, 1),
        max: new Date(2020, 0, 31)
      },
      step: {
        days: 1
      },
      defaultValues: {
        min: new Date(2015, 0, 1),
        max: new Date(2020, 0, 31)
      }
    }).bind('valuesChanged', function(e, data) {
//      getResources(data.values.min.toISOString(), data.values.max.toISOString());
      this.map._onResize();
    });
  }
}
