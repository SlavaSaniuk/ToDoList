/**
 * DateTimeUtilities class has static method to work with JS Date object.
 */
export class DateTimeUtilities {

    static dateToStr(aDate) {
        return aDate.toLocaleDateString("en-US", {day: 'numeric', month: 'numeric'})
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