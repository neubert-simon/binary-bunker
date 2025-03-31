import styled from "styled-components";
import {useRef, useState} from "react";
import MoreDetailsBtn from "../../../ui/MoreDetailsBtn.jsx";
import {usePagePagination} from "../../../hooks/usePagePagination.js";
import {useGSAP} from "@gsap/react";
import gsap from "gsap";

/**
 * Styled component for the IPv4 visualizer content.
 * This component includes animations and responsive design for the visualized IP data.
 */
const StyledIPVisualizerContent = styled.div`
    position: relative;
    transform: translateX(-5rem);
    opacity: 0;
    padding: 2rem;
    width: 55%;
    flex: 1;
    background-color: var(--color-black);
    border-radius: var(--border-radius-md);

    &::before {
        content: "";
        position: absolute;
        top: -1rem;
        right: -1rem;
        width: 100%;
        height: 100%;
        background-color: transparent;
        pointer-events: none;
        outline: 0.15rem solid var(--color-white);
        border-radius: var(--border-radius-sm);
    }

    .ip-content {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
    }
    
    .ipvisualizer-container {
        position: absolute;
        width: 100%;
        height: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        gap: 6rem;
        transition: .5s ease-out;
    }
    
    .ipvisualizer-content__container {
        position: relative;
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 2rem;
    }

    .pageOne {
        top: -100%;
        opacity: 0;
    }

    .pageOne.active {
        top: 0;
        opacity: 1;
    }

    .pageTwo {
        top: 100%;
        opacity: 0;
    }

    .pageTwo.active {
        top: 0;
        opacity: 1;
    }
    
    .ip {
        position: relative;
        color: var(--color-white);
        font-size: 6rem;
        letter-spacing: .5rem;
        font-family: impact;
    }
    
    .ip-content__ipAmount {
        position: relative;
        width: 100%;
        color: var(--color-white);
        font-family: impact;
        font-size: 2rem;
        display: flex;
        justify-content: center;
        align-items: center;
        word-break: break-word;
    }
    
    .ip-content__ipAmount span {
        transform: translateY(-1rem);
        font-size: 1.3rem;
    }
    
    .ip-binary {
        position: relative;
        color: var(--color-white);
        font-size: 3.3rem;
        letter-spacing: .2rem;
        font-family: impact;
    }
    
    .networkID::before {
        content: "";
        position: absolute;
        top: 90%;
        left: 0;
        width: 100%;
        height: .3rem;
        background-color: var(--color-red);
        pointer-events: none;
    }

    .networkID::after {
        content: "Network ID";
        position: absolute;
        top: 100%;
        left: 0;
        width: 100%;
        font-size: 2rem;
        letter-spacing: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        color: var(--color-red);
        pointer-events: none;
    }
    
    .networkPart::before {
        content: "";
        position: absolute;
        top: 90%;
        left: 0;
        width: 100%;
        height: .3rem;
        background-color: var(--color-red);
        pointer-events: none;
    }
    
    .networkPart::after {
        content: "Network Part";
        position: absolute;
        top: 100%;
        left: 0;
        width: 100%;
        font-size: 2rem;
        letter-spacing: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        color: var(--color-red);
        pointer-events: none;
    }

    .hostPart::before {
        content: "";
        position: absolute;
        top: 90%;
        left: 0;
        width: 100%;
        height: .3rem;
        background-color: var(--color-green);
        pointer-events: none;
    }

    .hostPart::after {
        content: "Host Part";
        position: absolute;
        top: 100%;
        left: 0;
        width: 100%;
        font-size: 2rem;
        letter-spacing: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        color: var(--color-green);
        pointer-events: none;
    }

    @media (max-width: 576px) {
        transform: translateX(-2rem);
        padding: 1rem;
        width: 100%;
        flex: 1;
        border-radius: var(--border-radius-tiny);

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        .ip {
            font-size: 1.8rem;
        }

        .ip-binary {
            font-size: 1.6rem;
        }

        .ip-content__ipAmount {
            font-size: 1.3rem;
            width: 80%;
        }

        .ip-content__ipAmount span {
            font-size: 1rem;
        }

        .ipvisualizer-container {
            gap: 4rem;
        }

        .networkID::before {
            height: .15rem;
        }

        .networkID::after {
            font-size: 1.1rem;
        }

        .networkPart::before {
            height: .15rem;
        }

        .networkPart::after {
            font-size: 1.1rem;
        }

        .hostPart::before {
            height: .15rem;
        }

        .hostPart::after {
            font-size: 1.1rem;
        }
        
        .pageTwo .ipvisualizer-content__container {
            flex-direction: column;
            gap: 3rem;
        }
    }

    @media (min-width: 577px) and (max-width: 768px) {
        transform: translateX(-2rem);
        padding: 1rem;
        width: 100%;
        flex: 1;
        border-radius: var(--border-radius-tiny);

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        .ip {
            font-size: 2.3rem;
        }

        .ip-binary {
            font-size: 2rem;
        }

        .ip-content__ipAmount {
            font-size: 1.3rem;
            width: 80%;
        }

        .ip-content__ipAmount span {
            font-size: 1rem;
        }

        .ipvisualizer-container {
            gap: 4rem;
        }

        .networkID::before {
            height: .15rem;
        }

        .networkID::after {
            font-size: 1.1rem;
        }

        .networkPart::before {
            height: .15rem;
        }

        .networkPart::after {
            font-size: 1.1rem;
        }

        .hostPart::before {
            height: .15rem;
        }

        .hostPart::after {
            font-size: 1.1rem;
        }

        .pageTwo .ipvisualizer-content__container {
            flex-direction: column;
            gap: 3rem;
        }
    }

    @media (min-width: 769px) and (max-width: 992px) {

    }

    @media (min-width: 993px) and (max-width: 1200px) {

    }

    @media (min-width: 1200px) and (max-width: 1599px) {

    }
`;

/**
 * The IPv4 Visualizer Content component that displays the calculated IP and network information.
 * It includes binary representations of the network and host parts, and also allows for pagination
 * between different data views with a custom hook: usePagePagination
 *
 * @param {Object} data The data object containing IP and network information.
 * @returns {JSX.Element} The JSX for rendering the visualizer content.
 */
function IPv4VisualizerContent({ data }) {
    const { page, handlePagePaginationClick } = usePagePagination(2); // Custom Hook Pagination with 2 pages
    const ref = useRef();

    // Destructure relevant data from the passed data prop
    const {
        prefix,
        ip_decimal: ipDecimal,
        host_part_binary: hostPartBinary,
        net_part_binary: netPartBinary,
        ip_binary: ipBinary,
        net_part_decimal: netPartDecimal
    } = data;

    // Calculate the length of the host part
    const hostPartLength = hostPartBinary.split("").filter(char => char !== ".").length;
    // Calculate the number of hosts in the IP range
    const hostPartIPAmount = Math.pow(2, hostPartLength);

    useGSAP(() => {
        gsap.to(ref.current, { opacity: 1, x: 0, ease: 'power2.inOut', duration: .3 });
    });

    return <StyledIPVisualizerContent ref={ref}>
        <div className="ip-content">
            {/* Page 1: Displays Network ID and IP Range */}
            <div className={`ipvisualizer-container pageOne ${page === 1 ? "active" : ""}`}>
                <div className="ipvisualizer-content__container">
                    <div className="networkID ip">{netPartDecimal}</div>
                    <div className="ip">/{prefix}</div>
                </div>
                <div className="ip-content__ipAmount">
                    You can have 2 <span>{hostPartLength}</span> = {hostPartIPAmount} Hosts in this IP-Range
                </div>
            </div>
            {/* Page 2: Displays Binary Representation of Network and Host Parts */}
            <div className={`ipvisualizer-container pageTwo ${page === 2 ? "active" : ""}`}>
                <div className="ipvisualizer-content__container">
                    <div className="networkPart ip-binary">{netPartBinary}</div>
                    <div className="hostPart ip-binary">{hostPartBinary}</div>
                </div>
            </div>
        </div>
        <MoreDetailsBtn handleDetailsClick={handlePagePaginationClick} page={page} />
    </StyledIPVisualizerContent>
}

export default IPv4VisualizerContent;