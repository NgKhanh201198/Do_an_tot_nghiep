import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class LoggerService {

    constructor() { }
    isLog: boolean = environment.isLogger;

    logger(msg: any) {
        if (this.isLog == true) {
            console.log("Message: " + JSON.stringify(msg));
        } return false;
    }

    loggerError(msg: any) {
        if (this.isLog == true) {
            console.error("Error: " + JSON.stringify(msg));
        } return false;
    }

    loggerWarn(msg: any) {
        if (this.isLog == true) {
            console.warn("Warning: " + JSON.stringify(msg));
        } return false;
    }

    loggerData(msg: any) {
        if (this.isLog == true) {
            console.log(msg);
        } return false;
    }
}
