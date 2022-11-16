// noinspection JSUnresolvedVariable

import React from "react";
import "../../../styles/fragments/task/filtered-content-block.css"
import {Logger} from "../../../js/logging/Logger";
import {DateTimeUtilities} from "../../utilities/DateTimeUtilities";
import {TaskView} from "./TaskView";

/**
 * @propsProperty activeFilter - current active filter type [{TasksFilterType}];
 * @propsProperty taskViewPropsList - list of task view properties [{TaskViewProps}];
 * @propsProperty todayDate - today server date [{Date}];
 */
export class FilteredContentBlock extends React.Component {

    // Logger:
    LOGGER = new Logger("FilteredContentBlock", true);

    render() {

        // Filter user task based on current active filter:
        let renderingContent; // TaskView to be rendered:
        switch (this.props.activeFilter) {
            case TasksFilterType.WEEK: {
                // Week tasks:
                renderingContent = <WeekFilterContent todayDate={this.props.todayDate} viewPropsList={this.props.taskViewPropsList} />
                break;
            }
            default: {

            }

        }



        return (
            <div className={"filtered-content-block"}>
                {renderingContent}
            </div>
        )
    }


}

/**
 * Filter specified task views by {TaskFilterType.WEEK} week filter.
 * @param props - component props.
 * @propsProperty todayDate - today {Date} date.
 * @propsProperty viewPropsList - list of view props to be filtered and rendered.
 */
const WeekFilterContent =(props) => {

    // Filter categories result list:
    const weekFilterCategories = [];

    // Get only today tasks:
    const todayCategoryName = "Today - " +DateTimeUtilities.dateToFormattedStr(props.todayDate, "dd.mm.yyyy");
    const todayTasks = TasksFilter.dateTasks(props.todayDate, props.viewPropsList);
    weekFilterCategories.push(<FilterCategoryBlock key={"today_tasks"} categoryName={todayCategoryName}
                                                   categoryViews={todayTasks} parentControlFunctions={props.parentControlFunctions} />);

    // Filter on tomorrow tasks:
    const tomorrowCategoryName = "Tomorrow - " +DateTimeUtilities.dateToFormattedStr(
        DateTimeUtilities.addDays(props.todayDate,1), "dd.mm.yyyy");
    const tomorrowTasks = TasksFilter.dateTasks(DateTimeUtilities.addDays(props.todayDate, 1), props.viewPropsList);
    weekFilterCategories.push(<FilterCategoryBlock key={"tomorrow_tasks"} categoryName={tomorrowCategoryName}
                                                   categoryViews={tomorrowTasks} parentControlFunctions={props.parentControlFunctions} />);
    // Filter on any week tasks:
    const weekCategoryName = "Week -  until " +DateTimeUtilities.dateToFormattedStr(
        DateTimeUtilities.addDays(props.todayDate,7), "dd.mm.yyyy");
    const weekTasks = TasksFilter.betweenDateTasks(
        DateTimeUtilities.addDays(props.todayDate, 2), DateTimeUtilities.addDays(props.todayDate, 7), props.viewPropsList);
    weekFilterCategories.push(<FilterCategoryBlock key={"week_tasks"} categoryName={weekCategoryName}
                                                   categoryViews={weekTasks} parentControlFunctions={props.parentControlFunctions} />);

    return weekFilterCategories;


}

/**
 *
 * @param props - component props.
 * @propsProperty categoryName - name of filter category.
 * @propsProperty categoryViews - list of category views.
 * @return {JSX.Element}
 */
const FilterCategoryBlock =(props) => {

    const views = props.categoryViews.map(viewProps => {
        return <TaskView key={viewProps.viewId} viewId={viewProps.viewId} task={viewProps.taskObj}
                  loadingStatus={viewProps.loadStatus} parentControlFunctions={props.parentControlFunctions} />
    })

    return (
        <div className={"filter-category-block"}>
            <p> {props.categoryName} </p>
            {views}
        </div>
    )
}

class TasksFilter {

    /**
     * Filter specified list of task on specified completion date.
     * @param aDate - filter date completion value.
     * @param aListViewProps - list of task view props which will be filtered.
     * @return {*} - filtered list of task view props.
     */
    static dateTasks(aDate, aListViewProps) {
        return aListViewProps.filter(viewProps => {
            return DateTimeUtilities.isDatesEquals(viewProps.taskObj.taskCompletionDate, aDate);
        })
    }

    static betweenDateTasks(aStartDate, aFinishDate, aViewPropsList) {
        return aViewPropsList.filter(viewProps => {
            // Get task date completion:
            const taskCompletion = viewProps.taskObj.taskCompletionDate;
            // Compare date:
            return DateTimeUtilities.isDateInPeriod(taskCompletion, aStartDate, aFinishDate);
        })
    }

}

export const TasksFilterType = {WEEK: 0, ALL: 1};