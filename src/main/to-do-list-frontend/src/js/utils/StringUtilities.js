/**
 * Static functions for working with Strings.
 */
export class StringUtilities {

    static UNIQUE_STRINGS = [];

    /**
     * Format specified string.
     * Use "%o" characters to print object from specified array of objects.
     * @param aSrcStr - source string.
     * @param aObjArr - array of objects.
     * @returns {string} - formatted string.
     */
    static format =(aSrcStr, aObjArr) => {

        let objIter=0;
        let resultString="";
        let subStr="";

        for (let i=0; i<aSrcStr.length; i++) {
            let sym = aSrcStr.charAt(i);
            if (sym === '%') {
                if (aSrcStr.charAt(i + 1) === 'o') {
                    resultString += subStr;
                    subStr ="";
                    resultString += JSON.stringify([...aObjArr].at(objIter));
                    objIter++;
                    i=i+1;
                }
            } else subStr += sym;
        }

        return resultString.concat(subStr);

    }

    /**
     * Generate random characters string of specified length.
     * @param aLength - string length
     * @returns {string} - string.
     */
    static randomString =(aLength) => {
            let result           = '';
            const characters       = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
            const charactersLength = characters.length;
            for (let i = 0; i < aLength; i++ ) {
                result += characters.charAt(Math.floor(Math.random() * charactersLength));
            }
            return result;
    }

    /**
     * Function generate unique string in all client application.
     * @param aLength - string length.
     * @returns {string|*} - unique string.
     */
    static uniqueString =(aLength) => {
        // Generate random string:
        let randomStr = StringUtilities.randomString(aLength);
        // If generated string already used:
        if (StringUtilities.UNIQUE_STRINGS.includes(randomStr)) {
            // Then: Generate new string:
            return StringUtilities.uniqueString(aLength);
        }else {
            // Else: Add generated string to UNIQUE_STRINGS array and return it:
            StringUtilities.UNIQUE_STRINGS.push(randomStr);
            return randomStr;
        }
    }

    /**
     * Capitalize first letter in source string.
     * @param aStr - source string.
     * @return {string} - new string.
     */
    static capitalizeFirstLetter =(aStr) => {
        return aStr.charAt(0).toUpperCase() + aStr.slice(1);
    }
}