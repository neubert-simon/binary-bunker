import StyledIPLinks from "../../../ui/StyledIPLinks.js";
import StyledLink from "../../../ui/StyledLink.js";
import {useHandleLinkClick} from "../../../hooks/useHandleLinkClick.js";
import MoreSoon from "../../../ui/MoreSoon.jsx";

/**
 * Component for displaying subnetting related navigation links.
 *
 * @returns {JSX.Element} A styled set of links related to IP subnetting.
 */
function IPSubnettingLinks() {
    const handleLinkClick = useHandleLinkClick(); // Hook for handling link clicks, ensuring proper navigation

    return <StyledIPLinks>
        <StyledLink
            onClick={(e) => handleLinkClick(e, "/ipv4-visualizer")}
            outlinevariation="top"
            variation="TopLeftBottomLeft"
        >
            <span className="label">IPv4</span>
            <span className="label">Visualizer</span>
        </StyledLink>
        <MoreSoon
            outlinevariation="bottom"
            variation="TopRightBottomRight"
        >
            <span className="label">More Soon</span>
        </MoreSoon>
    </StyledIPLinks>
}

export default IPSubnettingLinks;