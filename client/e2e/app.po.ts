import {browser, by, element} from 'protractor';

export class AppPage {
    navigateTo() {
        return browser.get('/');
    }

    navigateToSpecific(url: string) {
        return browser.get(url);
    }

    selectButtonText(buttonType: string) {
        let button = element(by.id(buttonType +"Button"));
        return button.getText();
    }

    clickButton(buttonType: string) {
        element(by.id(buttonType + "Button")).click();
    }

    clickById(id: string){
        element(by.id(id)).click();
    }

    getById(id: string){
        return element(by.id(id));
    }


}
