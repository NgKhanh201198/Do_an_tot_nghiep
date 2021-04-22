import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBookingrateComponent } from './create-bookingrate.component';

describe('CreateBookingrateComponent', () => {
  let component: CreateBookingrateComponent;
  let fixture: ComponentFixture<CreateBookingrateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateBookingrateComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateBookingrateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
