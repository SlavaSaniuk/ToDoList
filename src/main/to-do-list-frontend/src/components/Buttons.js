/**
 * CrossButton is SVG "cross" image. Used as cancel, remove button component.
 * @param props - react properties.
 * @property classes - component class names.
 * @property clickFunc - onClick action function.
 * @returns {JSX.Element} - <SVG> "cross" element.
 * @constructor - initialize props.
 */
export const CrossButton =(props) => {
    return (
        <svg fill="#000000" xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 26 26" width="24px" height="24px" className={props.classes} onClick={props.clickFunc}>
            <path d="M 21.734375 19.640625 L 19.636719 21.734375 C 19.253906 22.121094 18.628906 22.121094 18.242188 21.734375 L 13 16.496094 L 7.761719 21.734375 C 7.375 22.121094 6.746094 22.121094 6.363281 21.734375 L 4.265625 19.640625 C 3.878906 19.253906 3.878906 18.628906 4.265625 18.242188 L 9.503906 13 L 4.265625 7.761719 C 3.882813 7.371094 3.882813 6.742188 4.265625 6.363281 L 6.363281 4.265625 C 6.746094 3.878906 7.375 3.878906 7.761719 4.265625 L 13 9.507813 L 18.242188 4.265625 C 18.628906 3.878906 19.257813 3.878906 19.636719 4.265625 L 21.734375 6.359375 C 22.121094 6.746094 22.121094 7.375 21.738281 7.761719 L 16.496094 13 L 21.734375 18.242188 C 22.121094 18.628906 22.121094 19.253906 21.734375 19.640625 Z"/>
        </svg>
    )
}

/**
 * CheckmarkButton is SVG "checkmark" image. Used as "Done", "Select" button component.
 * @param props - react properties.
 * @property classes - component class names.
 * @property clickFunc - onClick action function.
 * @returns {JSX.Element} - <SVG> "cross" element.
 * @constructor - initialize props.
 */
export const CheckmarkButton =(props) => {
    return (
        <svg fill="#000000" xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 72 72" width="24px" height="24px" className={props.classes} onClick={props.clickFunc}>
            <path d="M57.658,12.643c1.854,1.201,2.384,3.678,1.183,5.532l-25.915,40c-0.682,1.051-1.815,1.723-3.064,1.814	C29.764,59.997,29.665,60,29.568,60c-1.146,0-2.241-0.491-3.003-1.358L13.514,43.807c-1.459-1.659-1.298-4.186,0.36-5.646	c1.662-1.46,4.188-1.296,5.646,0.361l9.563,10.87l23.043-35.567C53.329,11.971,55.806,11.442,57.658,12.643z"/>
        </svg>
    )
}

export const PlusButton =(props) => {
    return (
        <svg fill="#000000" xmlns="http://www.w3.org/2000/svg" width="24" height="24">
            <path d="M16 0H8v8H0v8h8v8h8v-8h8V8h-8V0z"/>
        </svg>

    )

}

export const CancelButton =(props) => {
    return (
        <svg fill="#000000" xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 26 26" width="24px" height="24px" className={props.classes} onClick={props.clickFunc}>
            <path d="M 21.734375 19.640625 L 19.636719 21.734375 C 19.253906 22.121094 18.628906 22.121094 18.242188 21.734375 L 13 16.496094 L 7.761719 21.734375 C 7.375 22.121094 6.746094 22.121094 6.363281 21.734375 L 4.265625 19.640625 C 3.878906 19.253906 3.878906 18.628906 4.265625 18.242188 L 9.503906 13 L 4.265625 7.761719 C 3.882813 7.371094 3.882813 6.742188 4.265625 6.363281 L 6.363281 4.265625 C 6.746094 3.878906 7.375 3.878906 7.761719 4.265625 L 13 9.507813 L 18.242188 4.265625 C 18.628906 3.878906 19.257813 3.878906 19.636719 4.265625 L 21.734375 6.359375 C 22.121094 6.746094 22.121094 7.375 21.738281 7.761719 L 16.496094 13 L 21.734375 18.242188 C 22.121094 18.628906 22.121094 19.253906 21.734375 19.640625 Z"/>
        </svg>
    )
}

export const EditButton =(props) => {
    return (
        <svg fill="#000000" xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 72 72" width="24px" height="24px" className={props.classes} onClick={props.clickFunc}>
            <path d="M38.406 22.234l11.36 11.36L28.784 54.576l-12.876 4.307c-1.725.577-3.367-1.065-2.791-2.79l4.307-12.876L38.406 22.234zM41.234 19.406l5.234-5.234c1.562-1.562 4.095-1.562 5.657 0l5.703 5.703c1.562 1.562 1.562 4.095 0 5.657l-5.234 5.234L41.234 19.406z"/>
        </svg>
    )
}

export const DoneButton =(props) => {
    return (
        <svg fill="#000000" xmlns="http://www.w3.org/2000/svg"  viewBox="0 0 72 72" width="24px" height="24px" className={props.classes} onClick={props.clickFunc}>
            <path d="M57.658,12.643c1.854,1.201,2.384,3.678,1.183,5.532l-25.915,40c-0.682,1.051-1.815,1.723-3.064,1.814	C29.764,59.997,29.665,60,29.568,60c-1.146,0-2.241-0.491-3.003-1.358L13.514,43.807c-1.459-1.659-1.298-4.186,0.36-5.646	c1.662-1.46,4.188-1.296,5.646,0.361l9.563,10.87l23.043-35.567C53.329,11.971,55.806,11.442,57.658,12.643z"/>
        </svg>
    )
}

/**
 * Simple text button.
 * @param props - element properties.
 * @property classes - button classes
 * @property clickFunc - function onClick action.
 * @property btnText - button text.
 * @returns {JSX.Element}
 * @constructor
 */
export const TextButton =(props) => {
    return (
        <input type={"button"} className={props.classes} onClick={props.clickFunc} value={props.btnText} />
    )
}