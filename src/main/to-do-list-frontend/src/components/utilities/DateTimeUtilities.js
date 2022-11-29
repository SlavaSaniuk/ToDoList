/**
 * DateTimeUtilities class has static method to work with JS Date object.
 */
import {Localization, SUPPORTED_LOCALES} from "../../js/localization/localization";
import {StringUtilities} from "../../js/utils/StringUtilities";

export class DateTimeUtilities {

    static dateToStr(aDate) {
        return aDate.toLocaleDateString("en-US", {day: 'numeric', month: 'numeric'})
    }

    /**
     * Print specified JS Date to String with format and locale.
     * If "aLocale" parameter is absent or null then function used en locale.
     * @param aDate - {Date} - source JS Date.
     * @param aFormat - {String} - date format (See JsDateFormatter below).
     * @param aLocale - {SUPPORTED_LOCALE} - locale property.
     * @return {string} - formatted date.
     */
    static dateToFormattedStr(aDate, aFormat, aLocale= SUPPORTED_LOCALES.EN) {
        if (aLocale === null) aLocale = SUPPORTED_LOCALES.EN;
        return JsDateFormatter.dateToStr(aDate, aFormat, aLocale);
    }

    static dateMonthAndDayToStr(aDate) {

        let day = aDate.getDate();
        if (day<10) day = '0'+day;

        let month = aDate.getMonth() +1;
        if (month<10) month = '0' +month;

        return day +'.' +month;
    }

    /**
     * Add days to specified date.
     * @param aDate - date value.
     * @param aDays - days number.
     * @returns {Date} - new date.
     */
    static addDays(aDate, aDays) {
        Date.prototype.addDays = function(aDays) {
            let date = new Date(this.valueOf());
            date.setDate(date.getDate() + aDays);
            return date;
        }

        return aDate.addDays(aDays);
    }

    /**
     * Get day number in week of specified date.
     * @param aDate - source date.
     * @return {null|number} - {DAYS_OF_WEEK} day number in week (1-7, 1 - monday);
     */
    static dayOfWeek(aDate) {

        switch (aDate.getDay()) {
            case 0: return this.DAYS_OF_WEEK.SUNDAY;
            case 1: return this.DAYS_OF_WEEK.MONDAY;
            case 2: return this.DAYS_OF_WEEK.TUESDAY;
            case 3: return this.DAYS_OF_WEEK.WEDNESDAY;
            case 4: return this.DAYS_OF_WEEK.THURSDAY;
            case 5: return this.DAYS_OF_WEEK.FRIDAY;
            case 6: return this.DAYS_OF_WEEK.SATURDAY;
            default: return null;
        }
    }

    /**
     * Days numbers of week.
     * @type {{WEDNESDAY: number, MONDAY: number, THURSDAY: number, SUNDAY: number, TUESDAY: number, FRIDAY: number, SATURDAY: number}}
     */
    static DAYS_OF_WEEK = {MONDAY: 1, TUESDAY: 2, WEDNESDAY: 3, THURSDAY: 4, FRIDAY: 5, SATURDAY: 6, SUNDAY: 7};

    /**
     * Convert specified Date to JS Date str.
     * @param aDate - JS Date.
     * @returns {string} - Date string.
     */
    static jsDateToStr(aDate) {
        // Month:
        let month = aDate.getMonth().valueOf()+1;
        if (month<10) month = '0' +month;

        // Day:
        let day = aDate.getDate();
        if (day<10) day = '0' +day;

        return aDate.getFullYear() +", " +month +", " +day;
    }

    /**
     * Compare two date. If first date > second date, return 1. Else if first date < second date, return -1.
     * If two dates equals, return 0.
     * Note: Function only compare dates year, month and day properties.
     * @param aDateFirst - first date.
     * @param aDateSecond - second date.
     * @return {number} - number [-1, 0, 1];
     */
    static compareDates(aDateFirst, aDateSecond) {

        // Compare years:
        let fYear = aDateFirst.getFullYear();
        if (fYear > aDateSecond.getFullYear()) return 1;
        else if (fYear < aDateSecond.getFullYear()) return -1;

        // If years equals, compare months:
        let fMonth = aDateFirst.getMonth();
        if (fMonth > aDateSecond.getMonth())  return 1;
        else if (fMonth < aDateSecond.getMonth()) return -1;

        // If years and months equals, compare days:
        let fDay = aDateFirst.getDate();
        if (fDay > aDateSecond.getDate()) return 1;
        else if (fDay < aDateSecond.getDate()) return -1;
        else return 0;
    }

    /**
     * Check if specified two dates is equals.
     * Note: Function only compare dates year, month and day properties.
     * @param aDateFirst - first date.
     * @param aDateSecond - second date.
     * @return {boolean} - true, if dates is equals.
     */
    static isDatesEquals(aDateFirst, aDateSecond) {
        return DateTimeUtilities.compareDates(aDateFirst, aDateSecond) === 0;
    }

    /**
     * Check condition: If specified date in period between start and end dates.
     * aStartDate >= aDate <= aEndDate.
     * @param aDate - source date.
     * @param aStartDate - start date in period.
     * @param aEndDate - end date in period;
     * @return {boolean} - true, if source date in period.
     */
    static isDateInPeriod(aDate, aStartDate, aEndDate) {
        // Check condition: date >= start date;
        let belowStartDate = DateTimeUtilities.compareDates(aDate, aStartDate) === -1;
        if (belowStartDate) return false;

        // Check condition: date <= end date;
        let aboveEndDate = DateTimeUtilities.compareDates(aDate, aEndDate) === 1;
        return !aboveEndDate;
    }
}

/**
 *
 * Format character: t - represent day of week;
 *     If yoy use char sequence of 't' character you may get any day of week representation:
 *     * 't' - day of week in number (1 - monday, 2 - tuesday);
 *     * 'tt' - day of week in string short format (mon (monday), - tue (TUESDAY));
 *     * 'ttt' - day of week in string usual format (monday, tuesday);
 *     If first 't' in sequence is in uppercase (T), then first letter in string format will be uppercase too.
 */
class JsDateFormatter {

    /**
     * Convert source date to string in specified format.
     * @param aDate - source date.
     * @param aFormat - date format.
     * @param aLocale - user locale.
     * @return {string} - string.
     */
    static dateToStr(aDate, aFormat, aLocale) {
        // Get first character:
        let sameCharsStr = aFormat[0];
        let sameCharsStrList = [];
        let result = "";

        // Iterate of date format:
        for (let i=1; i<aFormat.length+1; i++) {

            if (sameCharsStr.toLowerCase()[0] === aFormat.toLowerCase()[i]) sameCharsStr += aFormat[i];
            else {
                sameCharsStrList.push(sameCharsStr);
                sameCharsStr=aFormat[i];
            }

        }

        result += JsDateFormatter.#handleSameCharsStrList(sameCharsStrList, aDate, aLocale);

        return result;
    }


    static #handleSameCharsStrList(aList, aDate, aLocale) {
        let result = "";
        for (let i=0; i<aList.length; i++)
        result += JsDateFormatter.#handleSameCharsStr(aList[i], aDate, aLocale);
        return result;

    }


    static #handleSameCharsStr(aSameCharsStr, aDate, aLocale) {

        let datePropStr;

        // Check character:
        switch (aSameCharsStr[0]) {
            case 'd': {
                datePropStr = aDate.getDate().toString();
                break;
            }
            case 'm': {
                datePropStr = aDate.getMonth().valueOf()+1;
                break;
            }
            case 'y': {
                datePropStr = aDate.getFullYear().valueOf();
                break;
            }
            case 't': {
                datePropStr = this.#formattedDayOfWeek(DateTimeUtilities.dayOfWeek(aDate), aSameCharsStr, aLocale);
                break;
            }
            case 'T': {
                datePropStr = StringUtilities.capitalizeFirstLetter(this.#formattedDayOfWeek(DateTimeUtilities.dayOfWeek(aDate), aSameCharsStr, aLocale));
                break;
            }
            default: {
              return aSameCharsStr[0];
            }
        }

        return datePropStr;
    }

    /**
     * Handle char sequence of 't' characters (day of week).
     * Function handle length of source sequence and choose which format will be used.
     * @param aDayOfWeek - {number} - day of week.
     * @param aCharSeq - {string} - source char sequence.
     * @param aLocale - {SUPPORTED_LOCALES} - supported locales.
     * @return {*|string} - formatted day of week.
     */
    static #formattedDayOfWeek(aDayOfWeek, aCharSeq, aLocale) {
        // Check sequence length:
        // if length = 1, return day of week in number:
        if (aCharSeq.length === 1) return aDayOfWeek;
        // if length >= 2, return string format:
        if (aCharSeq.length === 2) return JsDateFormatter.#dayOfWeekStr(aDayOfWeek, aLocale, true);
        if (aCharSeq.length >= 3) return  JsDateFormatter.#dayOfWeekStr(aDayOfWeek, aLocale, false);

    }

    /**
     * Get day of week localized string by its number in short or usual format.
     * @param dayOfWeek - source day of week.
     * @param aLocale - used locale.
     * @param short - {boolean} - is in short format.
     * @return {string|*} - localized day of week string.
     */
    static #dayOfWeekStr(dayOfWeek, aLocale, short=false) {
        let suff = short ? "_short" : "";
        switch (dayOfWeek) {
            case 1: return Localization.getLocalizedText("DT_monday"+suff, aLocale);
            case 2: return Localization.getLocalizedText("DT_tuesday"+suff, aLocale);
            case 3: return Localization.getLocalizedText("DT_wednesday"+suff, aLocale);
            case 4: return Localization.getLocalizedText("DT_thursday"+suff, aLocale);
            case 5: return Localization.getLocalizedText("DT_friday"+suff, aLocale);
            case 6: return Localization.getLocalizedText("DT_saturday"+suff, aLocale);
            case 7: return Localization.getLocalizedText("DT_sunday"+suff, aLocale);
            default: return "UNDEFINED";
        }
    }

}