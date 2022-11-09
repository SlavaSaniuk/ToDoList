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
}