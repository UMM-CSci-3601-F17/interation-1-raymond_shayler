import {TestBed, ComponentFixture} from "@angular/core/testing";
import {HomeComponent} from "./home.component";
import {DebugElement} from "@angular/core";
import {By} from "@angular/platform-browser";
import {SharedModule} from "../shared.module";
import {MATERIAL_COMPATIBILITY_MODE} from "@angular/material";
import {RouterTestingModule} from "@angular/router/testing";
//import {routing} from "./app.routes";

describe('Home', () => {

    let component: HomeComponent;
    let fixture: ComponentFixture<HomeComponent>;
    let de: DebugElement;
    let el: HTMLElement;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [SharedModule, RouterTestingModule],
            declarations:[HomeComponent],

            // declare the test component
            providers: [{provide: MATERIAL_COMPATIBILITY_MODE, useValue: true}],
        })

        fixture = TestBed.createComponent(HomeComponent);

        component = fixture.componentInstance; // BannerComponent test instance

        // query for the title <h1> by CSS element selector
        de = fixture.debugElement.query(By.css('#logo'));
        el = de.nativeElement;
    });

    /*
    Test various buttons on the homePage
     */
    it("displays a greeting", () => {
        fixture.detectChanges();
        expect(el.textContent).toContain("Sage");
    });
});
