/**
 * TaskDto DTO object encapsulate task properties.
 */
export class TaskDto {

    // Class fields:
    taskId; // Task ID;
    taskName; // Task name;
}

/**
 * Builder for {@link TaskDto} DTO object.
 */
export class TaskBuilder {

    // Class variables;
    static taskId; // Task ID;

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
     * Construct new TaskDto object with current properties.
     * @returns {TaskDto} - new TaskDto dto object.
     */
    static build() {
        const taskDto = new TaskDto();
        taskDto.taskId = this.taskId;

        // Reset:
        this.taskId = 0;

        return taskDto;
    }
}