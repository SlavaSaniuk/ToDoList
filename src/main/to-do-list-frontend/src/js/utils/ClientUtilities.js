import {Localization} from "../localization/localization";
import {Properties} from "../../Properites";

/**
 * Client localization utilities functions.
 */
export class ClientLocalization {

    /**
     * Get localized string text.
     * Function used {Properties.CLIENT_LOCALE} property to check user used locale.
     * @param aId - {String} - string identification.
     * @return {*} - {String} - localized string text.
     */
    static getLocalizedText(aId) {
        return Localization.getLocalizedText(aId, Properties.CLIENT_LOCALE);
    }
}