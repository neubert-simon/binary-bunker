import styled from "styled-components";
import {useRef, useState} from "react";
import {useGSAP} from "@gsap/react";
import gsap from 'gsap';
import MoreDetailsBtn from "../../../ui/MoreDetailsBtn.jsx";
import {usePagePagination} from "../../../hooks/usePagePagination.js";

// Styled component for the IP content section
const StyledIPContent = styled.div`
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
        top: 1rem;
        left: -1rem;
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
    
    .page-content {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        font-size: 2.5rem;
        text-align: left;
        display: flex;
        justify-content: center;
        align-items: center;
        gap: 3rem;
        color: var(--color-white);
        transition: .5s ease-in-out;
    }
    
    .page-content__values {
        position: relative;
        display: flex;
        justify-content: start;
        align-items: start;
        flex-direction: column;
        gap: .2rem;
        line-height: 1.5;
    }

    .page-content__mobile {
        display: none;
    }

    .content-tuple {
        display: flex;
        gap: 3rem;
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

        .page-content {
            font-size: 1.8rem;
            flex-direction: column;
            align-items: center;
            gap: 0;
        }

        .content-tuple {
            gap: 1rem;
            width: 18rem;
            padding: .3rem 0;
            justify-content: start;
        }
        
        .pageTwo .page-content__values {
            display: none;
        }
        
        .page-content__mobile {
            position: relative;
            display: flex;
            justify-content: start;
            align-items: start;
            flex-direction: column;
            gap: .2rem;
            line-height: 1.5;
        }
        
        .mobile-values {
            display: flex;
            justify-content: flex-start;
            flex-direction: column;
        }
        
        .formated {
            font-size: 1.3rem;
        }
    }

    @media (min-width: 577px) and (max-width: 992px) {
        transform: translateX(-2rem);
        padding: 1rem;
        width: 80%;
        border-radius: var(--border-radius-tiny);

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }
        
        .page-content {
            font-size: 1.1rem;
            gap: 3rem;
        }

        .pageTwo .page-content__values {
            display: none;
        }

        .page-content__mobile {
            position: relative;
            display: flex;
            justify-content: start;
            align-items: start;
            flex-direction: column;
            gap: .2rem;
            line-height: 1.5;
        }

        .mobile-values {
            display: flex;
            justify-content: flex-start;
            flex-direction: column;
        }

        .formated {
            font-size: .9rem;
        }
    }

    @media (min-width: 993px) and (max-width: 1200px) {
        transform: translateX(-2rem);
        padding: 1rem;
        width: 80%;
        flex: 1;
        border-radius: var(--border-radius-tiny);

        &::before {
            top: .5rem;
            left: -.5rem;
            border-radius: var(--border-radius-tiny);
        }

        .page-content {
            font-size: 1.8rem;
            gap: 3rem;
        }

        .pageTwo .page-content__values {
            display: none;
        }

        .page-content__mobile {
            position: relative;
            display: flex;
            justify-content: start;
            align-items: start;
            flex-direction: column;
            gap: .2rem;
            line-height: 1.5;
        }

        .mobile-values {
            display: flex;
            justify-content: flex-start;
            flex-direction: column;
        }

        .formated {
            font-size: 1.3rem;
        }
    }

    @media (min-width: 1200px) and (max-width: 1599px) {
        .page-content {
            font-size: 2rem;
            gap: 3rem;
        }
    }
`;

/**
 * The main content component for the IPv4 Calculator page.
 * This component displays information related to the IP address, subnet, and its binary representation
 * with page navigation support.
 *
 * @param {Object} data The data used to populate the content (e.g., IP address, subnet information).
 * @returns {JSX.Element} The rendered content for the IPv4 Calculator.
 */
function IPv4CalculatorContent({ data }) {
    const { page, handlePagePaginationClick } = usePagePagination(2); // Custom Hook Pagination with 2 pages
    const ref = useRef();

    const { Class: ipClass, ip, ip_binary, private: ipPrivate, subnet } = data;
    const { broadcast: broadcastIP, broadcast_binary, mask, mask_binary, netID, netID_binary, prefix } = subnet;

    useGSAP(() => {
     gsap.to(ref.current, { opacity: 1, x: 0, ease: 'power2.inOut', duration: .3 });
    });

    return <StyledIPContent ref={ref}>
        <div className="ip-content">
            {/* Page 1 content */}
            <div className={`page-content pageOne ${page === 1 ? "active" : ""}`}>
                <div className="page-content__values">
                    <div className="content-tuple">
                        <span>IP-Address:</span>
                        <span>{ip} /{prefix}</span>
                    </div>
                    <div className="content-tuple">
                        <span>Broadcast:</span>
                        <span>{broadcastIP}</span>
                    </div>
                    <div className="content-tuple">
                        <span>Mask:</span>
                        <span>{mask}</span>
                    </div>
                </div>
                <div className="page-content__values">
                    <div className="content-tuple">
                        <span>NetID:</span>
                        <span>{netID}</span>
                    </div>
                    <div className="content-tuple">
                        <span>Private:</span>
                        <span>{ipPrivate ? "True" : "False"}</span>
                    </div>
                    <div className="content-tuple">
                        <span>Class of IP:</span>
                        <span>{ipClass}</span>
                    </div>
                </div>
            </div>
            {/* Page 2 content */}
            <div className={`page-content pageTwo ${page === 2 ? "active" : ""}`}>
                <div className="page-content__values">
                    <span>IP-Address Binary:</span>
                    <span>Broadcast IP Binary:</span>
                    <span>Mask Binary:</span>
                    <span>NetID Binary:</span>
                </div>
                <div className="page-content__values">
                    <span className="formated">{ip_binary}</span>
                    <span className="formated">{broadcast_binary}</span>
                    <span className="formated">{mask_binary}</span>
                    <span className="formated">{netID_binary}</span>
                </div>

                <div className="page-content__mobile">
                    <div className="mobile-values">
                        <span>IP-Address Binary:</span>
                        <span className="formated">{ip_binary}</span>
                    </div>
                    <div className="mobile-values">
                        <span>Broadcast IP Binary:</span>
                        <span className="formated">{broadcast_binary}</span>
                    </div>
                    <div className="mobile-values">
                        <span>Mask Binary:</span>
                        <span className="formated">{mask_binary}</span>
                    </div>
                    <div className="mobile-values">
                        <span>NetID Binary:</span>
                        <span className="formated">{netID_binary}</span>
                    </div>
                </div>
            </div>
        </div>
        <MoreDetailsBtn handleDetailsClick={handlePagePaginationClick} page={page}/>
    </StyledIPContent>
}

export default IPv4CalculatorContent;