import AnimationContainer from "../ui/AnimationContainer";
import Background from "../ui/Background.js";
import DMTY from "../ui/DMTY";
import Title from "../ui/Title";
import Form from "../ui/Form.js";
import InputText from "../ui/InputText.js";
import CreateIPBtn from "../ui/CreateIPBtn.js";
import Loading from "../ui/Loading.jsx";
import {useState} from "react";
import {useMutation} from "@tanstack/react-query";
import {getIPv6Calculation} from "../services/fetchData.js";
import toast from "react-hot-toast";
import IPv6CalculatorContent from "../features/IP/IPv6/IPv6CalculatorContent.jsx";
import ContentContainer from "../ui/ContentContainer.js";
import Content from "../ui/Content.js";

/**
 * IPv6Calculator page component for the Binary Bunker application.
 * This page allows users to input an IPv6 address and calculate associated information.
 * When the user submits the form, the getIPv6Calculation function is called to fetch relevant details about the provided IPv6 address.
 * The page provides feedback through a loading spinner while the calculation is being processed.
 * Once the data is fetched, it is displayed via the `IPv6CalculatorContent` component.
 *
 * @component
 * @returns {JSX.Element} The IPv6 calculator page with a form to input IPv6 addresses and display calculation results.
 */
function IPv6Calculator() {
    const [ip, setIP] = useState(""); // Stores the inputted IPv6 address
    const [data, setData] = useState(null); // Stores the calculated data after fetching

    // useMutation hook from react-query to perform the calculation fetch
    const { mutate, isPending: isCalculating } = useMutation({
        mutationFn: getIPv6Calculation, // Function to get data for the IPv6 address
        onSuccess: (data) => {
            setData(data);
            toast.success(`You received the information about IP: ${data.ip}`);
        },
        onError: (error) => {
            toast.error(error.message);
        },
    });

    /**
     * Handles form submission to trigger the IPv6 calculation.
     * Prevents default form behavior, checks if an IP is entered,
     * and then triggers the calculation by calling `mutate`.
     *
     * @param {React.FormEvent} e The form submit event
     */
    function handleSubmit(e) {
        e.preventDefault();

        if (ip === "" ) return;

        mutate({ ip });
        setData(null)
        setIP("");
    }

  return (
    <AnimationContainer>
      <Background>
        <DMTY speechBubbleText="Here we can learn all about IPv6. Let's test it out" />
        <Content>
            <Title firstTitle="IPv6" secondTitle="Calculator" />
            <ContentContainer variation="top">
                <Form onSubmit={handleSubmit}>
                    <InputText
                        type="text"
                        id="ip"
                        name="ip"
                        autoComplete="off"
                        placeholder="IPv6 Address"
                        value={ip}
                        onChange={(e) => setIP(e.target.value)}
                    />
                    <CreateIPBtn>Calculate</CreateIPBtn>
                </Form>
                {isCalculating ? <Loading /> : null}
                {data && <IPv6CalculatorContent data={data} />}
            </ContentContainer>
        </Content>
      </Background>
    </AnimationContainer>
  );
}

export default IPv6Calculator;
