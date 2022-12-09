import {forwardRef, useEffect, useRef, useState} from "react";
import DatePicker from "react-datepicker";
import {Properties} from "../../Properites";
import {DateTimeUtilities} from "../utilities/DateTimeUtilities";

/**
 *
 * @param props - component props.
 * @propsProperty value - {String} - text area value.
 * @propsProperty onChange - {Function} - text area onChange action function.
 * @propsProperty className - {String} - class names string.
 * @propsproperty rows - {number} - text area rows attribute.
 * @propsProperty placeholder - {String} - placeholder text.
 * @return {JSX.Element} - text area element.
 */
export const ScalableTextArea =(props) => {
    // Refs:
    const textAreaRef = useRef(null);

    // Effects:
    useEffect(() => {
        // Scalable effect:
        const textArea = textAreaRef.current;

        // Set height:
        textArea.style.height = "0px";
        textArea.style.height = textArea.scrollHeight +"px";
    })

    return <textarea value={props.value} onChange={props.onChange} ref={textAreaRef}
                     className={props.className}  placeholder={props.placeholder} />
}

/**
 * Custom react date picker.
 * @param props - component props.
 * @propertyProps date - {Date} - date picker input date.
 * @propertyProps onChange - {Function} - date picker onChange function.
 * @propertyProps wrapperClassName - {String} - all datepicker wrapper css class.
 * @propertyProps inputClassName - {String} - datepicker input css class.
 */
export const DatePickerInput =(props) => {
    const CustomInput = forwardRef(({value, onClick}, ref) => (
        <button className={props.inputClassName} onClick={onClick} ref={ref} > {DateTimeUtilities.dateToFormattedStr(props.date, "tt-dd.mm", Properties.CLIENT_LOCALE)} </button>
    ));
    return (<div className={props.wrapperClassName}> <DatePicker customInput={<CustomInput />} selected={props.date} onChange={props.onChange} /> </div>)
}