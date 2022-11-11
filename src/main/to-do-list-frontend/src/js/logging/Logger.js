import {StringUtilities} from "../utils/StringUtilities";

/**
 * Logger class used to logging about any situations in application.
 * Create instance of this class in every loggable object.
 */
export class Logger {

    // Class fields:
    loggerClassName; // Class name;
    logFlag; // Logging flag;

    /**
     * Construct new logger for specified [aClassName] loggable object.
     * @param aClassName - loggable object class name.
     * @param aLogFlag - logging flag (Global, in Properties.js).
     */
    constructor(aClassName, aLogFlag) {

        // Map arguments:
        this.loggerClassName = aClassName;
        this.logFlag = aLogFlag;
    }

    /**
     * Log to console.
     * Function log message in format "[LOGGER_CLASS_NAME]: MSG[args...]" to console;
     * Function used {StringUtilities#format} method to print formatted output.
     * If this LogFlag is false, do not log.
     * @param aMsg - formatted log message.
     * @param aArgArr - array of arguments to print.
     */
    log =(aMsg, aArgArr) => {
        if (this.logFlag) console.log(this.loggerClassName +": " +StringUtilities.format(aMsg, aArgArr));
    };
}