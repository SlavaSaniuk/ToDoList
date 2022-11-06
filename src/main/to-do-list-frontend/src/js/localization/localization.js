
export const SUPPORTED_LOCALES = {EN: 1, RU: 2}

const LOCALIZED_STRINGS = {
    EN: {
        no_founded_string: "[REQUIRED STRING IS NOT FOUND]",
        // ====== AddTaskBlock.js ======
        task_name_input_placeholder: "Do financial report"
    },

    RU: {
        no_founded_string: "[ТРЕБУЕМОЕ СТРОКОВОЕ ЗНАЧЕНИЕ НЕ НАЙДЕНО]",
        // ====== AddTaskBlock.js ======
        task_name_input_placeholder: "Сдать фининансовый отчет"
    }
}


export class Localization {

    static DEFAULT_LOCALE = SUPPORTED_LOCALES.RU;


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

    static localization = {
        en:{
            name: "Slava"
        },
        ru: {
            name: "Вячеслав"
        }
    }
}

