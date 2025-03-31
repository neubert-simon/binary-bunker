import styled from "styled-components";
import Stat from "./Stat";

// Styled container for stats
const StyledStats = styled.div`
  position: relative;  
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
  gap: 1.5rem;
`;

function Stats() {
  return (
    <StyledStats>
      <Stat
        variation="TopLeftBottomLeft"
        outlinevariation="left"
        label="Tage in folge"
        statvalue={4}
      />
      <Stat
        outlinevariation="top"
        label="Beantwortete Fragen"
        statvalue={237}
      />
      <Stat
        outlinevariation="right"
        label="Richtige Antworten Hintereinander"
        statvalue={32}
      />
      <Stat
        outlinevariation="bottom"
        variation="TopRightBottomRight"
        label="IP Adressen Generiert"
        statvalue={1112}
      />
    </StyledStats>
  );
}

export default Stats;
