import {useState} from "react";
import {useMutation} from "@tanstack/react-query";
import {getIPv4Visualizer} from "../services/fetchData.js";
import toast from "react-hot-toast";

/**
 * Custom hook for managing the state and fetching data related to IPv4 visualizations or calculations.
 * It handles the user inputs: IPv4 address and prefix, submits the data to the API, and manages the result or errors.
 *
 * @param {Function} fetchFn The function used to fetch data (gets provided as param by using this hook)
 * @returns {Object} An object containing state variables, setter functions, and a submit-handler.
 */
export function useIPv4Fetch(fetchFn) {
    // Local state to manage the IPv4 address input
    const [ip, setIP] = useState("");
    // Local state to manage the subnet prefix input
    const [prefix, setPrefix] = useState("");
    // Local state to store the fetched data from the API
    const [data, setData] = useState(null);

    // The useMutation hook from React Query to handle API requests
    const { mutate, isPending: isCalculating } = useMutation({
        mutationFn: fetchFn,
        onSuccess: (data) => {
            setData(data);
            toast.success(`You received the information about IP: ${data.ip || data.ip_decimal}`);
        },
        onError: (error) => {
            toast.error(error.message);
        },
    });

    /**
     * Handles form submission by calling the mutation function with the input data.
     * Resets the form fields and starts a new fetch request.
     *
     * @param {Event} e The form-submit event.
     */
    function handleSubmit(e) {
        e.preventDefault();

        if (ip === "" || prefix === "") return;

        if(prefix.includes("/")) {
            mutate({ip, prefix: prefix.split("/")[1]});
        }
        else {
            mutate({ ip, prefix });
        }

        // Reset the form and data after submission
        setData(null)
        setIP("");
        setPrefix("");
    }

    return {
        handleSubmit,
        isCalculating,
        ip,
        setIP,
        prefix,
        setPrefix,
        data,
    }
}