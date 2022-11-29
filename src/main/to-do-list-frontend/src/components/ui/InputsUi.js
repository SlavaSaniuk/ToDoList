import {useEffect, useRef} from "react";

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