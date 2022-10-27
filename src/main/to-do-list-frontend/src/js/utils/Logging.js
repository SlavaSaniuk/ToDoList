import {Properties} from "../../Properites";

export class Logging {
    static log(aMsg, aObj) {
        if (Properties.DebugLogging) console.log(aMsg, aObj);
    }
}