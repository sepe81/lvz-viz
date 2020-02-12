import { Component, OnInit } from '@angular/core';
import { ArticleService } from '../articles/article.service';

declare var L: any;

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  private map: any;

  private markers: any;

  constructor(private articleService: ArticleService) { }

  ngOnInit(): void {
    this.map = L.map('mapSearch').setView([51.339695, 12.373075], 11);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(this.map);

    this.markers = new L.FeatureGroup();
    this.articleService.getx('0', '30', 'datePublished,desc').subscribe(data => {
      this.addtoMap(data);
    });
  }

  search() {
    console.debug('search');
    this.map._onResize();
  }

  addtoMap(data) {
    console.debug('addToMap');
    this.map.removeLayer(this.markers);
    this.markers = new L.FeatureGroup();
    const content = data.content;
    for (let i = content.length - 1; i >= 0; i--) {
      const c = content[i];
      if (c.coords) {
        const marker = new L.marker([c.coords.lat, c.coords.lon]).bindPopup('<a href=' + c.url + '>' + c.title + '</a><br>' + c.snippet);
        this.markers.addLayer(marker);
      }
    }
    this.map.addLayer(this.markers);
  };
}
