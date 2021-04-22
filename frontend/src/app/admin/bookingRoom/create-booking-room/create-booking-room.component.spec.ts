import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateBookingRoomComponent } from './create-booking-room.component';

describe('CreateBookingRoomComponent', () => {
  let component: CreateBookingRoomComponent;
  let fixture: ComponentFixture<CreateBookingRoomComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateBookingRoomComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateBookingRoomComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
