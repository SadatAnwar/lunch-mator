import {Component} from '@angular/core';
import {By} from "@angular/platform-browser";
import {async, TestBed} from "@angular/core/testing";
import {WelcomeComponent} from 'app/common/components/welcome/welcome.component';

@Component({selector: 'test', template: '<welcome></welcome>'})
class TestComponent {
}

describe('OrderIdFormatPipe', () => {
    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [WelcomeComponent, TestComponent]
        })
    });

    it('should contain Start button', async(() => {
        TestBed.compileComponents().then(() => {
            let fixture = TestBed.createComponent(TestComponent);
            fixture.detectChanges();
            fixture.whenStable().then(() => {
                let buttonText = fixture.debugElement.query(By.css('#start-button')).nativeElement.innerText;
                expect(buttonText).toEqual('Start');
            });
        });
    }));

    it('should contain Join button', async(() => {
        TestBed.compileComponents().then(() => {
            let fixture = TestBed.createComponent(TestComponent);
            fixture.detectChanges();
            fixture.whenStable().then(() => {
                let buttonText = fixture.debugElement.query(By.css('#join-button')).nativeElement.innerText;
                expect(buttonText).toEqual('Join');
            });
        });
    }));
});
