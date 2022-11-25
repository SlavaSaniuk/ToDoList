
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
        ftb_week_filter_category_name_today_pf: "Today - ",
        ftb_week_filter_category_name_tomorrow_pf: "Tomorrow - ",
        ftb_week_filter_category_name_week_pf: "Week - until ",
        // ==== tvab - Task view adding block ====
        tvab_title: "Create new task",
        tvab_name: "TASK NAME",
        tvab_desc: "TASK DESCRIPTION",
        tvab_btn_create: "Create",
        tvab_btn_cancel: "Cancel"
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
        ftb_week_filter_category_name_today_pf: "Сегодня - ",
        ftb_week_filter_category_name_tomorrow_pf: "Завтра - ",
        ftb_week_filter_category_name_week_pf: "Неделя - до ",
        // ==== tvab - Task view adding block ====
        tvab_title: "Создать новою задачу",
        tvab_name: "Название",
        tvab_desc: "Описание задачи",
        tvab_btn_create: "СОЗДАТЬ",
        tvab_btn_cancel: "ОТМЕНИТЬ"
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

