/**
 * Static functions for working with Strings.
 */
export class StringUtilities {

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
}