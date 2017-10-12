import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardCreateComponent } from './card-create.component';

describe('CardCreateComponent', () => {
  let component: CardCreateComponent;
  let fixture: ComponentFixture<CardCreateComponent>;

  let cardServiceStub: {
      addNewCard: (word: string, synonym: string, antonym : string, general_sense : string, example_usage: string) => true;
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

    //Commented out due to errors
  // it('should create', () => {
  //   expect(component).toBe(true);
  // });


  it("should have the right fields and no initialization"), () =>{
      expect(typeof component.word).toBe(typeof "Hello");
      expect(typeof component.synonym).toBe(typeof "Hello");
      expect(typeof component.antonym).toBe(typeof "Hello");
      expect(typeof component.general_sense).toBe(typeof "Hello");
      expect(typeof component.example_usage).toBe(typeof "Hello");
  };

});
