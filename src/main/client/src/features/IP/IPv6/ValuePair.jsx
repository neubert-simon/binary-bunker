/**
 * Component for displaying a labeled value pair.
 *
 * @param {{ label: string, value: string | number }} props
 * @param {string} props.label The label describing the value.
 * @param {string | number} props.value The value associated with the label.
 * @returns {JSX.Element} A styled component displaying the label and value.
 */
function ValuePair({ label, value }) {
    return (
        <div className="value-pair">
            <span className="value-label">{label}:</span>
            <span className="value-data">{value}</span>
        </div>
    );
}

export default ValuePair;