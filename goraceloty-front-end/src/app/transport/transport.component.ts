import { Component, OnInit } from '@angular/core';
import { TransportService } from '../services/transport.service';
import { Transport } from '../entity/Transport';

@Component({
  selector: 'app-transport-details',
  templateUrl: './transport.component.html'
})
export class TransportDetailsComponent implements OnInit {
  transports: Transport[] = [];
  selectedTransport?: Transport;

  constructor(private transportService: TransportService) {}

  ngOnInit(): void {
    this.loadTransports();
  }

  loadTransports(): void {
    this.transportService.getTransports().subscribe({
      next: (data) => {
        this.transports = data;
        console.log('Failed to load transports');
      },
      error: (error) => {
        console.error('Failed to load transports', error);
      }
    });
  }

  selectTransport(transportId: number): void {
    this.selectedTransport = this.transports.find(t => t.transportID === transportId);
  }
}
