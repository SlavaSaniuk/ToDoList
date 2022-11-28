/**
 * Global application properties.
 * @field {TaskViewLogging}: boolean - TaskView logging flag;
 * @field {TASKS_BLOCK_LOGGING}: boolean - TasksBlock logging flag;
 * @type {{TaskViewLogging: boolean, DebugLogging: boolean}}
 */
export const Properties = {
    DebugLogging: true,
    GLOBAL_LEVEL_LOGS: {TRACE: true, DEBUG: true, INFO: true, WARN: true, ERROR: true},
    TASKS_BLOCK_LOGGING: true, // TaskBlock logging flag;
    TaskViewLogging: false // TaskView logging flag;
}