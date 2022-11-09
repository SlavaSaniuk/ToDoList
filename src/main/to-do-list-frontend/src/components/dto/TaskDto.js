import {DateTimeUtilities} from "../utilities/DateTimeUtilities";

/**
 * TaskDto DTO object encapsulate user task properties.
 * Used to exchange task data between client and server.
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
 * Builder for {@link TaskDto} DTO object.
 */
export class TaskBuilder {

    // Class variables;
    static taskId; // Task ID;
    static taskName; // Task name;
    static taskDesc; // Task description;
    static taskDateCreation; // Task date creation;
    static taskDateCreationStr; // Date creation (String);
    static taskDateCompletion; // Task date completion;
    static taskDateCompletionStr; // // Date completion (String);

    /**
     * Set TaskDto.taskId property.
     * @param aId - task ID;
     * @returns {TaskBuilder} - this build.
     */
    static ofId(aId) {
        TaskBuilder.taskId = aId;
        return this;
    }

    /**
     * Set TaskDto.taskName property.
     * @param aName - task name.
     * @returns {TaskBuilder} - this builder.
     */
    static withName(aName) {
        this.taskName = aName;
        return this;
    }

    /**
     * Set TaskDto.taskDesc property.
     * @param aDesc - task description.
     * @returns {TaskBuilder} - this builder.
     */
    static withDescription(aDesc) {
        this.taskDesc = aDesc;
        return this;
    }

    /**
     * Set TaskDto.taskDateCreation and TaskDto.taskDateCreationStr.
     * @param aDate - JS Date.
     * @returns {TaskBuilder} - this builder.
     */
    static withDateCreation(aDate) {
        this.taskDateCreation = aDate;
        this.taskDateCreationStr = DateTimeUtilities.jsDateToStr(aDate);
        return this;
    }

    /**
     * Set TaskDto.taskDateCompletion and TaskDto.taskDateCompletionStr.
     * @param aDate - JS Date.
     * @returns {TaskBuilder} - this builder.
     */
    static withDateCompletion(aDate) {
        this.taskDateCompletion = aDate;
        this.taskDateCompletionStr = DateTimeUtilities.jsDateToStr(aDate);
        return this;
    }

    /**
     * Construct new TaskDto object with current properties.
     * @returns {TaskDto} - new TaskDto dto object.
     */
    static build() {
        const taskDto = new TaskDto();
        taskDto.taskId = this.taskId;
        taskDto.taskName = this.taskName;
        taskDto.taskDesc = this.taskDesc;
        taskDto.taskDateCreation = this.taskDateCreation;
        taskDto.dateOfCreation = this.taskDateCreationStr;
        taskDto.taskDateCompletion = this.taskDateCompletion;
        taskDto.dateOfCompletion = this.taskDateCompletionStr;

        // Reset:
        this.taskId = 0;
        this.taskName = null;
        this.taskDesc = null;
        this.taskDateCreation = null;
        this.taskDateCreationStr = null;
        this.taskDateCompletion = null;
        this.taskDateCompletionStr = null;

        return taskDto;
    }
}

export const TaskStatus = {WORKING: 1, COMPLETED: 2};