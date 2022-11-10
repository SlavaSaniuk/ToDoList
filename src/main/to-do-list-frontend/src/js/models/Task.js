
/**
 * Task model class represent a user task object.
 */
export class Task {

    constructor() {
    }

    // Class fields:
    taskId; // task identification ({Integer});
    taskName; // task name ({String});
    taskDescription; // task description ({String});
    taskCreationDate; // task date of creation ({Date});
    taskCompletionDate; // task date of completion ({Date});
    taskStatus; // task status ({TaskStatus});

}

export class TaskBuilder {
    // Class fields:
    bTaskId; // task identification ({Integer});
    bTaskName; // task name ({String});
    bTaskDescription; // task description ({String});
    bTaskCreationDate; // task date of creation ({Date});
    bTaskCompletionDate; // task date of completion ({Date});
    bTaskStatus; // task status ({TaskStatus});

    constructor() {
    }

    static builder() {
        return new TaskBuilder();
    }

    ofId(aId) {
        this.bTaskId = aId;
        return this;
    }

    withName(aName) {
        this.bTaskName = aName;
        return this;
    }

    withDescription(aDescription) {
        this.bTaskDescription = aDescription;
        return this;
    }

    withDateOfCreation(aDate) {
        this.bTaskCreationDate = aDate;
        return this;
    }

    withDateOfCompletion(aDate) {
        this.bTaskCompletionDate = aDate;
        return this;
    }

    withStatus(aStatus) {
        this.bTaskStatus = aStatus;
        return this;
    }





    build() {
        const task = new Task();
        task.taskId = this.bTaskId;
        task.taskName = this.bTaskName;
        task.taskDescription = this.bTaskDescription;
        task.taskCreationDate = this.bTaskCreationDate;
        task.taskCompletionDate = this.bTaskCompletionDate;
        task.taskStatus = this.bTaskStatus;

        return task;
    }

}

/**
 * Task statuses constants.
 * @type {{WORKING: number, COMPLETED: number}}
 */
export const TaskStatus = {WORKING: 1, COMPLETED: 2};