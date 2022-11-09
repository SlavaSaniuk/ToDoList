
/**
 * Task model class represent a user task object.
 */
export class Task {

    // Class fields:
    taskId; // task identification ({Integer});
    taskName; // task name ({String});
    taskDescription; // task description ({String});
    taskCreationDate; // task date of creation ({Date});
    taskCompletionDate; // task date of completion ({Date});
    taskStatus; // task status ({TaskStatus});

}

/**
 * Task statuses constants.
 * @type {{WORKING: number, COMPLETED: number}}
 */
export const TaskStatus = {WORKING: 1, COMPLETED: 2};