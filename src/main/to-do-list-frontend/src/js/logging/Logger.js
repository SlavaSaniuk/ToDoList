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

export class LevelLogger {
    constructor(aClassName, aLevelLogs) {
        this.className = aClassName;
        this.levelLogs = aLevelLogs;

        // Bind functions:
        this.trace.bind(this);
        this.debug.bind(this);
        this.info.bind(this);
        this.warn.bind(this);
        this.error.bind(this);
    }

    trace =(aMsg, aArgArr) => {
        if (this.levelLogs.TRACE)
            console.log(this.className +" TRACE: " +StringUtilities.format(aMsg, aArgArr));
    }

    debug =(aMsg, aArgArr) => {
        if (this.levelLogs.DEBUG)
            console.log(this.className +" DEBUG: " +StringUtilities.format(aMsg, aArgArr));
    }

    info =(aMsg, aArgArr) => {
        if (this.levelLogs.INFO)
            console.log(this.className +" INFO: " +StringUtilities.format(aMsg, aArgArr));
    }

    warn =(aMsg, aArgArr) => {
        if (this.levelLogs.WARN)
            console.log(this.className +" WARN: " +StringUtilities.format(aMsg, aArgArr));
    }

    error =(aMsg, aArgArr) => {
        if (this.levelLogs.ERROR)
            console.log(this.className +" ERROR: " +StringUtilities.format(aMsg, aArgArr));
    }


}

