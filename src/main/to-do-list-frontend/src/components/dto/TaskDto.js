/**
 * TaskDto DTO object encapsulate task properties.
 */
export class TaskDto {

    // Class fields:
    taskId; // Task ID;
    taskName; // Task name;
    taskDesc; // Task description;
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
     * Construct new TaskDto object with current properties.
     * @returns {TaskDto} - new TaskDto dto object.
     */
    static build() {
        const taskDto = new TaskDto();
        taskDto.taskId = this.taskId;
        taskDto.taskName = this.taskName;
        taskDto.taskDesc = this.taskDesc;

        // Reset:
        this.taskId = 0;
        this.taskName = null;
        this.taskDesc = null;

        return taskDto;
    }
}

export const TaskStatus = {WORKING: 1, COMPLETED: 2};