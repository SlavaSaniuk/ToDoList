package by.beltelecom.todolist.configuration.services;

import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.utilities.Randomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class TestsTaskService {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(TestsTaskService.class);
    // Spring beans:
    @Autowired
    private TasksService tasksService;
    // Class variables:
    private static final int DEFAULT_NUMBER_OF_WORDS = 4; // Number of words in task name.
    private final TestTaskBuilder builder = new TestTaskBuilder();

    public TestTaskBuilder builder() {
        return this.builder;
    }

    public Task createTask() {
        LOGGER.debug(String.format("Create task object with number of words in name: %d;", DEFAULT_NUMBER_OF_WORDS));
        return this.createTask(DEFAULT_NUMBER_OF_WORDS);
    }

    public Task createTask(int aNumberOfWords) {
        String taskName = Randomizer.randomSentence(aNumberOfWords);
        LOGGER.debug(String.format("Create task object with name: %s;", taskName));
        return TaskWrapper.Creator.createTask(taskName);
    }

    public Task saveTask(Task aTask, User aUser) {
        LOGGER.debug(String.format("Save task[%s] of user[%s] in database;", aTask, aUser));
        return this.tasksService.createTask(aTask, aUser);
    }

    public Task testTask(User aUser) {
        LOGGER.debug(String.format("Generate new test Task[] object of user[%s];", aUser));
        Task task = this.saveTask(this.createTask(), aUser);
        LOGGER.debug(String.format("Task[%s] of user[%s] - GENERATED;", task, aUser));
        return task;
    }

    /**
     * Generate list of random tasks.
     * @param aUser - user owner.
     * @param aNumberOfTasks - size of result list.
     * @return - list of generated user tasks.
     */
    public List<Task> testTasks(User aUser, int aNumberOfTasks) {
        LOGGER.debug(String.format("Generate %d task objects of user[%s];", aNumberOfTasks, aUser));
        List<Task> generatedTasks = new ArrayList<>();
        for (int i=0; i<aNumberOfTasks; i++) generatedTasks.add(this.testTask(aUser));
        return generatedTasks;
    }

    public class TestTaskBuilder {

        private int wordsCountInName = 1; // words count in task name;

        private LocalDate dateOfCompletion; // task date of completion;

        public TestTaskBuilder withWordsCountInName(int aWordsCountInName) {
            this.wordsCountInName = aWordsCountInName;
            return this;
        }

        public TestTaskBuilder withDateOfCompletion(LocalDate aDateOfCompletion) {
            this.dateOfCompletion = aDateOfCompletion;
            return this;
        }

        public Task build(User aUser) {
            Task task = new TaskWrapper.Builder()
                    .withName(Randomizer.randomSentence(this.wordsCountInName))
                    .withDateOfCompletion(this.dateOfCompletion)
                    .build();
            return saveTask(task, aUser);
        }

        public List<Task> buildList(User aUser, int aListSize) {
            List<Task> tasksList = new ArrayList<>();

            for (int i=0; i<aListSize; i++) tasksList.add(this.build(aUser));

            return tasksList;
        }


    }
}
