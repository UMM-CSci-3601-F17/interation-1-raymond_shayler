import {AppPage} from './app.po';
import {browser} from "protractor";

describe('angular-spark-lab', () => {
    let page: AppPage;

    beforeEach(() => {
        page = new AppPage();
    });

    it('should load', () => {
        page.navigateTo();
    });

    it('should have a play button', () => {
        page.navigateTo();
        expect(page.selectButtonText("play")).toContain("Play");
        page.clickButton("play");
        expect(browser.getCurrentUrl()).toContain("card");

    });


    it('should have a card button', () => {
        page.navigateTo();
        expect(page.selectButtonText("card")).toContain("Card");
        page.clickButton("card");
        expect(browser.getCurrentUrl()).toContain("card-create");
    });


    it('should have a decks button', () => {
        page.navigateTo();
        expect(page.selectButtonText("deck")).toContain("Deck");
        page.clickButton("deck");

        //this feature not implemented yet
        //expect(browser.getCurrentUrl()).toContain("cardcreate");
    });

    it('should have a settings button', () => {
        page.navigateTo();
        expect(page.selectButtonText("setting")).toContain("Settings");
        page.clickButton("setting");

        //this feature not implemented yet
        //expect(browser.getCurrentUrl()).toContain("cardcreate");
    });

    it("should have a function drawer", ()=> {
        page.navigateToSpecific("card-create");
        expect(browser.getCurrentUrl()).toContain("card-create");
        page.clickById("launchNav");
        browser.sleep(500);
        page.clickById("navHome");
        expect(browser.getCurrentUrl()).toContain("/");
        expect(browser.getCurrentUrl()).not.toContain("card-create");
    })


    });


