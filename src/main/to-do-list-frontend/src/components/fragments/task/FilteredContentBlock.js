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

    constructor(props) {
        super(props);

        // Bind functions:
        this.weekFilter.bind(this);
    }

    weekFilter =(aTodayDate, aViewPropsList) => {
        const weekFilterCategories = [];
        // Filter on today tasks:
        const todayTasks = TasksFilter.dateTasks(aTodayDate, aViewPropsList);
        weekFilterCategories.push(<FilterCategoryBlock key={"today_tasks"} categoryName={"Today"} categoryViews={todayTasks} />);
        // Filter on tomorrow tasks:
        const tomorrowTasks = TasksFilter.dateTasks(DateTimeUtilities.addDays(aTodayDate, 1), aViewPropsList);
        weekFilterCategories.push(<FilterCategoryBlock key={"tomorrow_tasks"} categoryName={"Tomorrow"} categoryViews={tomorrowTasks} />);
        // Filter on any week tasks:
        const  anyWeekTasks = TasksFilter.betweenDateTasks(
            DateTimeUtilities.addDays(aTodayDate, 2), DateTimeUtilities.addDays(aTodayDate, 7), aViewPropsList);
        weekFilterCategories.push(<FilterCategoryBlock key={"week_tasks"} categoryName={"Week"} categoryViews={anyWeekTasks} />);

        return weekFilterCategories;
    }

    render() {

        // Filter user task based on current active filter:
        let renderingContent; // TaskView to be rendered:
        switch (this.props.activeFilter) {
            case TasksFilterType.WEEK: {
                // Week tasks:
                renderingContent = this.weekFilter(this.props.todayDate, this.props.taskViewPropsList);
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
 *
 * @param props - component props.
 * @propsProperty categoryName - name of filter category.
 * @propsProperty categoryViews - list of category views.
 * @return {JSX.Element}
 */
const FilterCategoryBlock =(props) => {

    const views = props.categoryViews.map(viewProps => {
        return <TaskView key={viewProps.viewId} viewId={viewProps.viewId} task={viewProps.taskObj}
                  loadingStatus={viewProps.loadStatus} />
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