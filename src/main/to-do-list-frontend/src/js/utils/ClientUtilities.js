import {Localization} from "../localization/localization";
import {Properties} from "../../Properites";

/**
 * Client utilities class has any static methods to access any client utilities classes instances.
 */
export class ClientUtilities {

    // Static private variables:
    static #clientLocalization = new ClientLocalization(); // Client localization;

    /**
     * Get client localization utility instance.
     * @return {ClientLocalization} - instance.
     */
    static localization() {
        return this.#clientLocalization;
    }

}

/**
 * Client localization utilities functions.
 */
class ClientLocalization {

    /**
     * Get localized string text.
     * Function used {Properties.CLIENT_LOCALE} property to check user used locale.
     * @param aId - {String} - string identification.
     * @return {*} - {String} - localized string text.
     */
    getLocalizedText(aId) {
        return Localization.getLocalizedText(aId, Properties.CLIENT_LOCALE);
    }
}