import React from "react";
import '../../styles/common.css'
import '../../styles/fragments/TasksBlock.css'
import Task from "./Task";
import {AddTaskBlock} from "./AddTaskBlock";
import {ReqUtilities} from "../utilities/ReqUtilities";

const TasksFooter =() => {
    return(<div className={"tasks-footer"}>
        <p className={"tasks-footer-text"}> LOAD MORE ... </p>
    </div>);
}

/**
 * This element render list of users tasks.
 * @property tasksList - list of users tasks.
 * @property funcOnSelectTask - parent function on select task action.
 * @property funcOnUnselectTask - parent function on unselect task action.
 * @property taskControlFuncs - task control function in object.
 */
class TasksList extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {

        const tasks = this.props.tasksList.map((task) =>
            <Task taskName={task.taskName} taskId={task.taskId} key={task.taskId}
                  funcOnSelectTasks={this.props.funcOnSelectTasks} funcOnUnselectTask={this.props.funcOnUnselectTask}
                  taskControlFuncs={this.props.taskControlFuncs}
            />
        );
        return (
            <div>
                {tasks}
            </div>
        )
    }
}

/**
 * Task content block is root element that has and render the TasksList and AddTaskBlock elements.
 * @property showAddTaskBlock - boolean flag.
 * @property showAddTaskBlockFunc - function to show/hide AddTaskBlock.
 * @property tasksList - list of users tasks.
 * @property funcOnSelectTask - call this function, when task is selected.
 * @property funcOnUnselectTask - parent function on unselect task action.
 * @property taskControlFuncs - task control function in object.
 */
class TasksContentBlock extends React.Component {
    /**
     * Render TaskContentBlock element.
     * @returns {JSX.Element} - html.
     */
    render() {
        return(
            <div>
                <AddTaskBlock isShow={this.props.showAddTaskBlock} showAddTaskBlockFunc={this.props.showAddTaskBlockFunc}
                              funcOnAddNewTask={this.props.funcOnAddNewTask} />
                <TasksList tasksList={this.props.tasksList}
                           funcOnSelectTasks={this.props.funcOnSelectTasks} funcOnUnselectTask={this.props.funcOnUnselectTask}
                           taskControlFuncs={this.props.taskControlFuncs}
                />
            </div>
        );
    }


}

const TasksFilterTab =(props) => {
    return(
        <p className={"tasks-filter-tab tasks-filter-tab-text col-2"}>{props.tabText}</p>
    );
}

const TasksFilterPanel =() => {
    return(<div className={"tasks-filter-panel tasks-menu-panel col-4 row"}>
        <TasksFilterTab tabText={"9"} />
        <TasksFilterTab tabText={"10"} />
        <TasksFilterTab tabText={"+7"} />
        <TasksFilterTab tabText={"ALL"} />
    </div>)
}

const TasksInfoFilterInfo =() => {
    return(<div>
        <p className={"tasks-info-text"}>TODAY [Current Date] </p>
    </div>);
}

const TasksInfoPanel =() => {
    return(<div className={"tasks-info-panel tasks-menu-panel col-4"}>
        <TasksInfoFilterInfo />
    </div>);
}

/**
 * Edit button statuses:
 * ACTIVE - 0;
 * DISABLED - 1;
 */
const TasksEditBtnStatus = {ACTIVE: 0, DISABLED: 1};

/**
 * Edit button types:
 * ADD - ADD;
 * REMOVE - REMOVE;
 * EDIT - EDIT;
 */
const TasksEditBtnTypes = {ADD: "ADD", DONE: 4, EDIT: "EDIT",REMOVE: "REMOVE"};

/**
 * Class represent a task edit button element.
 * @property btnType - TasksEditBtnTypes type btp value.
 * @property btnStatus - Status of edit button (see TasksEditBtnStatuses).
 * @property clickFunc - Function on click.
 *
 */
class TasksEditBtn extends React.Component {

    /**
     * Construct new TasksEditBtn element.
     * @param props - properties.
     */
    constructor(props) {
        super(props);
    }

    /**
     * Render EditTaskBtn element.
     * @returns {JSX.Element} - html.
     */
    render() {

        let btnClass = "tasks-edit-btn-";

        // Check type:
        switch (this.props.btnType) {
            case TasksEditBtnTypes.ADD:
                btnClass+="add";
                break;
            case TasksEditBtnTypes.EDIT:
                btnClass+="edit";
                break;
            case TasksEditBtnTypes.REMOVE:
                btnClass+="remove";
                break;
            case TasksEditBtnTypes.DONE:
                btnClass+="done";
                break;
            default:
                break;
        }

        // Check status:
        if (this.props.btnStatus === TasksEditBtnStatus.DISABLED) btnClass += "-disabled";


        return(
            <input type={"button"} disabled={this.props.btnStatus}
                   className={"tasks-edit-btn " +btnClass} onClick={this.props.clickFunc}/>
        );
    }
}

/**
 * This panel render tasks edit buttons {TasksEditBtn}.
 * @param props - React properties.
 * @property - editBtnAddStatus - status of "add" button.
 * @property - editBtnRemoveStatus- status of "remove" button.
 * @property funcOnRemoveTasks - parent function on remove tasks function.
 * @returns {JSX.Element} - html.
 */
const TasksEditPanel =(props) => {
    return(<div className={"tasks-edit-panel tasks-menu-panel col-4"}>
        <TasksEditBtn btnType={TasksEditBtnTypes.ADD} btnStatus={props.editBtnAddStatus}
                      clickFunc={props.showAddTaskBlockFunc} />
        <TasksEditBtn btnType={TasksEditBtnTypes.DONE} btnStatus={TasksEditBtnStatus.DISABLED} />
        <TasksEditBtn btnType={TasksEditBtnTypes.REMOVE} btnStatus={props.editBtnRemoveStatus}
            clickFunc={props.funcOnRemoveTasks} />
    </div>)
}

/**
 * This element is menu of {TasksEditBtn} control buttons.
 * @param props - react properties.
 * @property - editBtnAddStatus - status of "add" button.
 * @property - editBtnRemoveStatus- status of "remove" button.
 * @property - funcOnRemoveTasks - parent function on remove tasks action.
 * @returns {JSX.Element} - html;
 * @constructor
 */
const TasksTopMenu =(props) => {
    return(
        <div className={"tasks-top-menu row"} >
            <TasksEditPanel showAddTaskBlockFunc={props.showAddTaskBlockFunc}
                            funcOnRemoveTasks={props.funcOnRemoveTasks}
                            editBtnAddStatus={props.editBtnAddStatus} editBtnRemoveStatus={props.editBtnRemoveStatus} />
            <TasksInfoPanel />
            <TasksFilterPanel />
        </div>
    );
}

const TasksBlockLoader =() => {
    return (
        <div className={"tasks-block-loading-block row"}>
            <div className={"tasks-block-loader"} />
        </div>
    );
}

/**
 * Tasks block loading statuses.
 * @field LOADING - status, when tasks block try to load user tasks.
 */
const TasksBlockLoadStatus = {LOADING: 1, LOADED: 2};
/**
 * TasksBlock is root component that's render user's tasks at user page.
 * @property userId - user ID.
 * @function loadUserTasks.
 * @function showAddTaskBlock(boolean);
 * @function onAddNewTask;
 * @function onRemoveTasks - function calls when user click remove edit button.
 * @function onRemoveTask - function call when user click task control button.
 * @function postNewTask;
 * @function onSelectTasks - function calls when user select task.
 * @function onUnselectTask - function calls when user unselect task.
 */
class TasksBlock extends React.Component {
    constructor(props) {
        super(props);

        // Bind functions:
        this.loadUserTasks.bind(this);
        this.showAddTaskBlock.bind(this);
        this.onAddNewTask.bind(this);
        this.onRemoveTasks.bind(this);
        this.onRemoveTask.bind(this);
        this.postNewTask.bind(this);
        this.onSelectTasks.bind(this);
        this.onUnselectTask.bind(this);
        this.removeUserTask.bind(this);
        this.updateUserTask.bind(this);

        // Element state:
        this.state = {
            loadStatus: TasksBlockLoadStatus.LOADING, // Default load status (because need to load user tasks list);
            // ==== LIST OF USERS TASKS ====
            isShowAddTaskBlock: false, // Flag to show AddTaskBlock element;
            tasksList: [], // Array of users tasks;
            // ==== TASKS SELECTION ====
            isTasksSelected: false, // Selected tasks flag;
            selectedTasksList: [], // List of selected tasks;
            // ==== EDIT BUTTON STATUSES ====
            statusAddBtn: TasksEditBtnStatus.ACTIVE, // "Add" button status;
            statusRemoveBtn: TasksEditBtnStatus.DISABLED, // "Remove" button status;
        };
    }

    componentDidMount() {
        this.loadUserTasks().then();
    }

    /**
     * Load users tasks from server.
     * Function create fetch http request to get users tasks array.
     * @returns {Promise<any>}  - result json.
     */
    loadUserTasks = async () => {
        let resultTasksList = [];
        const promise = await ReqUtilities.getRequest("/rest/tasks/" + this.props.userId);
        promise.json().then((taskListDto) => {
            if (!taskListDto.exception) {
                taskListDto.tasksList.forEach((taskDto) => {
                    resultTasksList.push(taskDto);
                })

            }

            // Set state:
            this.setState(prevState => ({
                tasksList: prevState.tasksList.concat(resultTasksList),
                loadStatus: TasksBlockLoadStatus.LOADED
            }));
        })


    }

    /**
     * Function rerender TasksContentBlock to show AddTaskBlock element.
     * @param isShow - Boolean - true to show tasks block.
     */
    showAddTaskBlock =(isShow) => {
        this.setState({isShowAddTaskBlock: isShow})
    }

    /**
     * Add new users task to tasks list in element state.
     * @param aTask - task to add.
     */
    onAddNewTask =(aTask) => {

        // Post task to server:
        this.postNewTask(aTask).then((createdTask) => {
            // Add task to list in state and hide AddTaskBlock:
            this.setState(prevState => ({
            tasksList:  [createdTask, ...prevState.tasksList],
            isShowAddTaskBlock: false
        }));
        });
    }

    /**
     * Remove selected tasks.
     * Function calling removeTask function and update state tasks list.
     */
    onRemoveTasks =() => {
        this.state.selectedTasksList.forEach(aTask => {
            this.onRemoveTask(aTask, false);
        })

        this.setState({
            isTasksSelected: false,
            selectedTasksList: []
            }
        );
    }

    /**
     * Remove specified task.
     * @param aTask - task to remove.
     */
    onRemoveTask =(aTask) => {
        this.removeUserTask(aTask)
    }

    /**
     * Function calling when user select any task {Task.TaskSelector.checkbox selected}.
     * Function set isTasksSelected state to true and add task to state selected task list.
     * @param aTask - selected task.
     */
    onSelectTasks =(aTask) => {
        this.setState(prevState => ({
            isTasksSelected: true,
            selectedTasksList: prevState.selectedTasksList.concat(aTask),
        }));
    }

    /**
     * Function remove unselected task from state selectedTasksList array and change isTasksSelected flag if needed.
     * @param aTask
     */
    onUnselectTask =(aTask) => {

        let elementToRemoveIndex = -1;

        // Iterate selected tasks list:
        this.state.selectedTasksList.forEach((value, index) => {
            if (value.taskId === aTask.taskId) elementToRemoveIndex = index;
        });

        // Remove element from selected tasks list:
        if (elementToRemoveIndex > -1)
            this.state.selectedTasksList.splice(elementToRemoveIndex, 1);

        // Change flag if needed:
        if (this.state.selectedTasksList.length === 0) this.setState({isTasksSelected: false});
    }

    /**
     * Post new task object to server.
     * @param aTask - task to post.
     * @returns {Promise<any>} - created task.
     */
    postNewTask = async(aTask) => {
        // Post request:
        let response = await fetch( sessionStorage.getItem('SERVER_URL') +"/rest/task/create-task", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': sessionStorage.getItem('JWT')},
            body: JSON.stringify(aTask)
            });

        // Check task object:
        let taskDto = await response.json();
        if (taskDto.exception) {
            console.log("Exception when post new task occurs. ExceptionDto: ", taskDto);
            return;
        }

        // Return task object:
        return taskDto;
    }

    /**
     * Remove user task.
     * Function send http get request on url /rest/task/delete-task?id=taskId. Then remove task from state tasks list.
     * @param aTask - Task object with ID.
     * @returns {Promise<void>} - promise.
     */
    removeUserTask =async (aTask) => {
        const promise = await ReqUtilities.getRequest("/rest/task/delete-task?id=" + aTask.taskId);
        promise.json().then((exceptionDto => {
            if (exceptionDto.exception === false) {
                this.setState(prevState => ({
                    tasksList:  prevState.tasksList.filter((task) => {
                        return task.taskId !== aTask.taskId;
                    }),
                }));
            }
        }));

    }

    updateUserTask =(aModifiedTask) => {
        console.log("Update user task with: ", aModifiedTask);
    }

    /**
     * Render TasksBlock element.
     * @returns {JSX.Element} - TasksBlock.
     */
    render() {

        // Set tasks edit button statuses:
        // Array. Index: 0 - "ADD", 1 - "DONE", 2 - "REMOVE";
        let editBtnStatuses = this.state.isTasksSelected ?
            [TasksEditBtnStatus.DISABLED, TasksEditBtnStatus.ACTIVE, TasksEditBtnStatus.ACTIVE] :
            [TasksEditBtnStatus.ACTIVE, TasksEditBtnStatus.DISABLED, TasksEditBtnStatus.DISABLED];

        // Object has references on task control functions:
        const taskControlFuncs = {
            updateFunc: this.updateUserTask,
            removeFunc: this.removeUserTask
        }

        // Render content block based on load status:
        let contentBlock;
        if(this.state.loadStatus === TasksBlockLoadStatus.LOADING) {
            contentBlock = (
                <TasksBlockLoader />
            );
        }else {
            contentBlock = (
                <TasksContentBlock showAddTaskBlock={this.state.isShowAddTaskBlock}
                                   showAddTaskBlockFunc={this.showAddTaskBlock}
                                   tasksList={this.state.tasksList} // List of users tasks;
                                   funcOnAddNewTask={this.onAddNewTask}
                                   funcOnSelectTasks={this.onSelectTasks} funcOnUnselectTask={this.onUnselectTask}
                                   taskControlFuncs={taskControlFuncs}
                />
            );
        }

        return (
            <div className={"tasks-block m-auto"} >
                <TasksTopMenu showAddTaskBlockFunc={this.showAddTaskBlock}
                              funcOnRemoveTasks={this.onRemoveTasks}
                              editBtnAddStatus={editBtnStatuses[0]} editBtnRemoveStatus={editBtnStatuses[2]} />
                {contentBlock}
                <TasksFooter />
            </div>
        );
    }
}

export {TasksBlock}