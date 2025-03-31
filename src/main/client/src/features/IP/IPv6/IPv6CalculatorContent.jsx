import {useRef} from "react";
import {useGSAP} from "@gsap/react";
import gsap from 'gsap';
import MoreDetailsBtn from "../../../ui/MoreDetailsBtn.jsx";
import {usePagePagination} from "../../../hooks/usePagePagination.js";
import {formatBinary} from "../../../helpers/helper.js";
import {IoIosArrowBack, IoIosArrowForward} from "react-icons/io";
import ValuePair from "./ValuePair.jsx";
import IPv6Content from "../../../ui/IPv6Content.js";

/**
 * IPv6CalculatorContent Component to render IPv6-Adressinformation.
 * Includes pagination feature for different pages for IPv6 Information like in Binary
 *
 * @param {Object} props The props of the Component.
 * @param {Object} props.data IPv6-Dara, which are getting rendered.
 * @returns {JSX.Element} Rendered Component.
 */
function IPv6CalculatorContent({ data }) {
    const { page, handlePagePaginationClick } = usePagePagination(2); // Custom Hook Pagination with 2 pages
    const {
        page: subpage,
        handlePagePaginationClick: handleSubPagePaginationClick,
        nextPage,
        prevPage
    } = usePagePagination(3, 1, true); // Custom Hook Pagination for 3 pages with step size 1
    const ref = useRef();

    const { ip, ip_binary, private: ipPrivate, subnet } = data;
    const { broadcast: broadcastIP, broadcast_binary, maskDecimal, netID, netID_binary, prefix } = subnet;

    //Initial GSAP Animation
    useGSAP(() => {
        gsap.to(ref.current, { opacity: 1, x: 0, ease: 'power2.inOut', duration: .3 });
    });

    return <IPv6Content ref={ref}>
        <div className="ip-content">
            <div className={`page-content-vertical pageOne ${page === 1 ? "active" : ""}`}>
                <div className="page-content__values-vertical">
                    <ValuePair label="IP-Address" value={`${ip} /${prefix}`} />
                    <ValuePair label="Broadcast" value={broadcastIP} />
                    <ValuePair label="NetID" value={netID} />
                    <ValuePair label="Private" value={ipPrivate ? "True" : "False"} />
                </div>
            </div>
            <div className={`page-content-horizontal pageTwo ${page === 2 ? "active" : ""}`}>
                <div className={`page-content__values-horizontal ${subpage === 1 ? "active" : ""}`}>
                    <div className="values-horizontal__key">IP-Address Binary:</div>
                    <div className="values-horizontal__value">
                        {formatBinary(ip_binary).map((bitBlock, index) => <div className="binary-row"
                                                                               key={index}>{bitBlock}</div>)}
                    </div>
                </div>
                <div className={`page-content__values-horizontal ${subpage === 2 ? "active" : ""}`}>
                    <div className="values-horizontal__key">Broadcast IP Binary:</div>
                    <div className="values-horizontal__value">
                        {formatBinary(broadcast_binary).map((bitBlock, index) => <div className="binary-row"
                                                                                      key={index}>{bitBlock}</div>)}
                    </div>
                </div>
                <div className={`page-content__values-horizontal ${subpage === 3 ? "active" : ""}`}>
                    <div className="values-horizontal__key">NetID Binary:</div>
                    <div className="values-horizontal__value">
                        {formatBinary(netID_binary).map((bitBlock, index) => <div className="binary-row"
                                                                                  key={index}>{bitBlock}</div>)}
                    </div>
                </div>
                <div className="subPagePagination-btn">
                    <div className="subPageBtn" onClick={() => prevPage()}><IoIosArrowBack /></div>
                    <div className="subPageBtn" onClick={() => nextPage()}><IoIosArrowForward /></div>
                </div>
            </div>
        </div>
        <MoreDetailsBtn handleDetailsClick={handlePagePaginationClick} page={page}/>
    </IPv6Content>
}

export default IPv6CalculatorContent;