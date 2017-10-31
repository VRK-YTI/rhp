import { Component, OnInit } from '@angular/core';
import { OrganizationService } from '../services/organization.service';
import { OrganizationListItem } from '../apina';
import { Router } from '@angular/router';
import { AuthorizationManager } from '../services/authorization-manager';

@Component({
  selector: 'app-organizations',
  template: `
    <div class="row add-new-organization">
      <div class="col-md-12">

        <h2 translate>Organizations</h2>
        <button class="button btn-default" (click)="addOrganization()" *ngIf="authorizationManager.canCreateOrganization()">
          <span translate>Add new organization</span>
        </button>
        <button class="button btn-default" *ngIf="authorizationManager.canBrowseUsers()">
          <span translate>Browse users</span>
        </button>
      </div>
      <ul id="organizations-list">
        <li *ngFor="let organization of organizations" [routerLink]="['/organization', organization.id]">
          {{organization.name | translateValue}}
        </li>
      </ul>
    </div>
  `,
  styleUrls: ['./organizations.component.scss']
})

export class OrganizationsComponent implements OnInit {

  organizations: OrganizationListItem[];

  constructor(private organizationService: OrganizationService,
              public authorizationManager: AuthorizationManager,
              private router: Router) {
  }

  ngOnInit() {
    this.organizationService.getOrganizationList().subscribe(organizationListItems => {
      this.organizations = organizationListItems;
    });
  }

  addOrganization() {
    this.router.navigate(['/newOrganization']);
  }
}
