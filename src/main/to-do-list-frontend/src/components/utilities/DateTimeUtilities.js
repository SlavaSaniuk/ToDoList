export class DateTimeUtilities {

    /*
    static dateToStr(aDate) {
        return aDate.getDate().toString() +"." +(aDate.getMonth().valueOf()+1) +"." +aDate.getFullYear();
    }
    */

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

    static addDays(aDate, aDays) {
        Date.prototype.addDays = function(aDays) {
            let date = new Date(this.valueOf());
            date.setDate(date.getDate() + aDays);
            return date;
        }

        return aDate.addDays(aDays);
    }
}