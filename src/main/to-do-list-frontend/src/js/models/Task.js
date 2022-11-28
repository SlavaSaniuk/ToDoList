import {DateTimeUtilities} from "../../components/utilities/DateTimeUtilities";

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

/**
 * Builder of {Task} task model object.
 */
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

    /**
     * Construct Task model object from {TaskDto} dto.
     * @param aDto - taskDto object.
     * @returns - task model object.
     */
    static ofDto(aDto) {
        // Create new task:
        return TaskBuilder.builder().ofId(aDto.taskId)
            .withName(aDto.taskName)
            .withDescription(aDto.taskDesc)
            .withDateOfCreation(new Date(aDto.dateOfCreation))
            .withDateOfCompletion(new Date(aDto.dateOfCompletion))
            .withStatus(aDto.taskStatus)
            .build();
    }

}

/**
 * TaskDto data transfer object.
 */
export class TaskDto {

    // Class fields:
    taskId; // Task ID;
    taskName; // Task name;
    taskDesc; // Task description;
    taskDateCreation; // Task date creation;
    dateOfCreation; // Date creation (String);
    taskDateCompletion; // Task date completion;
    dateOfCompletion; // Date completion (String);
    taskStatus; // Task status;

}

/**
 * Builder for TaskDto DTO.
 */
export class TaskDtoBuilder {

    // DTO builder class fields:
    dbTaskId; // Task ID;
    dbTaskName; // Task name;
    dbTaskDesc; // Task description;
    dbTaskDateCreation; // Task date creation;
    dbDateOfCreation; // Date creation (String);
    dbTaskDateCompletion; // Task date completion;
    dbDateOfCompletion; // Date completion (String);
    dbTaskStatus; // Task status;

    static builder() {
        return new TaskDtoBuilder();
    }

    ofId(aId) {
        this.dbTaskId = aId;
        return this;
    }

    withName(aName) {
        this.dbTaskName = aName;
        return this;
    }

    withDesc(aDescription) {
        this.dbTaskDesc = aDescription;
        return this;
    }

    withCreationDate(aDate) {
        this.dbTaskDateCreation = aDate;
        this.dbDateOfCreation = DateTimeUtilities.jsDateToStr(aDate);
        return this;
    }

    withCompletionDate(aDate) {
        this.dbTaskDateCompletion = aDate;
        this.dbDateOfCompletion = DateTimeUtilities.jsDateToStr(aDate);
        return this;
    }

    withStatus(aStatus) {
        this.dbTaskStatus = aStatus;
        return this;
    }

    build() {
        // Construct new task:
        const taskDto = new TaskDto();
        taskDto.taskId = this.dbTaskId; // Set ID;
        taskDto.taskName = this.dbTaskName; // Set name;
        taskDto.taskDesc = this.dbTaskDesc; // Set desk:
        taskDto.taskDateCreation = this.dbTaskDateCreation; // Set created:
        taskDto.dateOfCreation = this.dbDateOfCreation;
        taskDto.taskDateCompletion = this.dbTaskDateCompletion; // Set completion:
        taskDto.dateOfCompletion = this.dbDateOfCompletion;
        taskDto.taskStatus = this.dbTaskStatus;

        return taskDto;
    }

    static ofTask(aTask) {
        return TaskDtoBuilder.builder().ofId(aTask.taskId)
            .withName(aTask.taskName)
            .withDesc(aTask.taskDescription)
            .withCreationDate(aTask.taskCreationDate)
            .withCompletionDate(aTask.taskCompletionDate)
            .withStatus(aTask.taskStatus)
            .build();
    }

}

/**
 * Task statuses constants.
 * @type {{WORKING: number, COMPLETED: number}}
 */
export const TaskStatus = {WORKING: 1, COMPLETED: 2};