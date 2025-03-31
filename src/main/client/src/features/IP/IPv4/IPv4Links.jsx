import { useHandleLinkClick } from "../../../hooks/useHandleLinkClick.js";
import StyledIPLinks from "../../../ui/StyledIPLinks.js";
import StyledLink from "../../../ui/StyledLink.js";
import MoreSoon from "../../../ui/MoreSoon.jsx";

/**
 * The IPv4Links component renders a list of links related to IPv4 and IPv6 calculators and an IP Learning Tool.
 * It includes links to different pages in the application with styles and click handling.
 *
 * @returns {JSX.Element} The JSX for rendering the IPv4-related links.
 */
function IPv4Links() {
  // Custom hook for handling link clicks
  const handleLinkClick = useHandleLinkClick();

  return (
    <StyledIPLinks>
      <StyledLink
        onClick={(e) => handleLinkClick(e, "/ipv4-calculator")}
        variation="TopLeftBottomLeft"
        outlinevariation="left"
      >
        <span className="label">IPv4</span>
        <span className="label">Calculator</span>
      </StyledLink>
      <StyledLink
        onClick={(e) => handleLinkClick(e, "/ipv6-calculator")}
        outlinevariation="right"
      >
        <span className="label">IPv6</span>
        <span className="label">Calculator</span>
      </StyledLink>
      <StyledLink
          onClick={(e) => handleLinkClick(e, "/ip-learntool")}
          outlinevariation="bottom"
          variation="TopRightBottomRight"
      >
          <span className="label">IP</span>
          <span className="label">Learning-Tool</span>
      </StyledLink>
      <MoreSoon
          outlinevariation="right"
          variation="TopRightBottomRight"
      >
          <span className="label">More Soon</span>
      </MoreSoon>
    </StyledIPLinks>
  );
}

export default IPv4Links;
