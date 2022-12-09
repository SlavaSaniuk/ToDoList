import {StringUtilities} from "../utils/StringUtilities";

export const SUPPORTED_LOCALES = {EN: 1, RU: 2}

const LOCALIZED_STRINGS = {
    EN: {
        no_founded_string: "[REQUIRED STRING IS NOT FOUND]",
        // ====== AddTaskBlock.js ======
        task_name_input_placeholder: "Task name: Do financial report",
        task_desc_input_placeholder: "Task description: At 5:00 pm",
        at_control_btn_add: "Add",
        at_control_btn_clear: "Clear",
        at_control_btn_cancel: "Hide",
        // ==== TV - TaskView component =====
        tv_task_date_creation: "Created at:",
        tv_task_date_completion: "Complete until:",
        tv_edit_control_btn_update: "Update",
        tv_edit_control_btn_cancel: "Cancel",
        tv_complete_until: "Complete at:",
        tv_created_at: "Created at:",
        ftb_week_filter_category_name_today_pf: "Today - ",
        ftb_week_filter_category_name_tomorrow_pf: "Tomorrow - ",
        ftb_week_filter_category_name_week_pf: "Week - until ",
        // ==== tvab - Task view adding block ====
        tvab_title: "Create new task",
        tvab_name: "TASK NAME",
        tvab_desc: "TASK DESCRIPTION",
        tvab_btn_create: "Create",
        tvab_btn_cancel: "Cancel",
        // ==== tveb - Task view adding block ====
        tveb_title: "Edit task",
        tveb_update_text: "Update",
        tveb_cancel_text: "Cancel",
        // ==== DT - Date/Time ====
        DT_monday: "monday",
        DT_tuesday: "tuesday",
        DT_wednesday: "wednesday",
        DT_thursday: "thursday",
        DT_friday: "friday",
        DT_saturday: "saturday",
        DT_sunday: "sunday",
        DT_monday_short: "mon",
        DT_tuesday_short: "tue",
        DT_wednesday_short: "wed",
        DT_thursday_short: "thu",
        DT_friday_short: "fri",
        DT_saturday_short: "sat",
        DT_sunday_short: "sun",
    },

    RU: {
        no_founded_string: "[ТРЕБУЕМОЕ СТРОКОВОЕ ЗНАЧЕНИЕ НЕ НАЙДЕНО]",
        // ====== AddTaskBlock.js ======
        task_name_input_placeholder: "Имя задачи: Сдать фининансовый отчет",
        task_desc_input_placeholder: "Описание задачи: В четверг в 17:00",
        at_control_btn_add: "Добавить",
        at_control_btn_clear: "Очистить",
        at_control_btn_cancel: "Скрыть",
        // ==== TV - TaskView component =====
        tv_task_date_creation: "Создана:",
        tv_task_date_completion: "Выполнить до:",
        tv_edit_control_btn_update: "Обновить",
        tv_edit_control_btn_cancel: "Отменить",
        tv_complete_until: "Выполнить:",
        tv_created_at: "Созданно:",
        ftb_week_filter_category_name_today_pf: "Сегодня - ",
        ftb_week_filter_category_name_tomorrow_pf: "Завтра - ",
        ftb_week_filter_category_name_week_pf: "Неделя - до ",
        // ==== tvab - Task view adding block ====
        tvab_title: "Создать новою задачу",
        tvab_name: "Название",
        tvab_desc: "Описание задачи",
        tvab_btn_create: "СОЗДАТЬ",
        tvab_btn_cancel: "ОТМЕНИТЬ",
        // ==== tveb - Task view adding block ====
        tveb_title: "Редактировать задачу",
        tveb_update_text: "ОБНОВИТЬ",
        tveb_cancel_text: "ОТМЕНИТЬ",
        // ==== DT - Date/Time ====
        DT_monday: "понедельник",
        DT_tuesday: "вторник",
        DT_wednesday: "среда",
        DT_thursday: "четверг",
        DT_friday: "пятница",
        DT_saturday: "суббота",
        DT_sunday: "воскресенье",
        DT_monday_short: "пон",
        DT_tuesday_short: "вт",
        DT_wednesday_short: "ср",
        DT_thursday_short: "чт",
        DT_friday_short: "пт",
        DT_saturday_short: "сб",
        DT_sunday_short: "вск",
    }
}


export class Localization {

    static DEFAULT_LOCALE = SUPPORTED_LOCALES.RU;

    /**
     * Get localized string text.
     * NOTE: If "aLocalizedStrings" formal parameter is "undefined" used english localized strings object.
     * @param aId - {String} - localized string identification.
     * @param aLocalizedStrings - {LOCALIZED_STRINGS.*} - localized strings object.
     * @return {any} - {String} localized text.
     */
    static getString(aId, aLocalizedStrings = LOCALIZED_STRINGS.EN) {
        return Reflect.get(aLocalizedStrings, aId);
    }

    /**
     * Get localized string text by specified supported locale.
     * Note: if locale is not supported then throw new NoSupportedLocaleException exception.
     * @param aId - {String} identification.
     * @param aSupportedLocale - {SUPPORTED_LOCALES} one of supported locale.
     * @return {*} - localized string text.
     */
    static getLocalizedText(aId, aSupportedLocale) {
        switch (aSupportedLocale) {
            case SUPPORTED_LOCALES.EN: return Localization.getString(aId, LOCALIZED_STRINGS.EN);
            case SUPPORTED_LOCALES.RU: return Localization.getString(aId, LOCALIZED_STRINGS.RU);
            default: throw new NoSupportedLocaleException(aSupportedLocale);
        }
    }


    /**
     * DEPRECATED.
     * @param aName - localized string name.
     * @return {any} - localized string message.
     */
    static getLocalizedString =(aName) => {
        let usedLocale = Localization.DEFAULT_LOCALE;

        switch (usedLocale) {
            case SUPPORTED_LOCALES.EN: {
                return Reflect.get(LOCALIZED_STRINGS.EN, aName);
            }
            case SUPPORTED_LOCALES.RU: {
                return Reflect.get(LOCALIZED_STRINGS.RU, aName);
            }
            default: {
                return Reflect.get(LOCALIZED_STRINGS.EN, aName);
            }
        }

    };
}

export class CommonLocalizationError extends Error {
    constructor(message) {
        super(message);
        this.name = "CommonLocalizationError";
    }
}

export class NoSupportedLocaleException extends CommonLocalizationError {
    constructor(aLocale) {
        super(StringUtilities.format("Locale [%o] is not supported in application.", [aLocale]));
        this.name = "NoSupportedLocaleException";
    }
}

